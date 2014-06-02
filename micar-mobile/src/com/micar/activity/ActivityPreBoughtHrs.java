package com.micar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.fragment.FragmentAlertDialog;
import com.micar.model.Model;
import com.micar.model.PreBoughtHoursInfo;
import com.micar.parser.PreBoughtHrsParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;

public class ActivityPreBoughtHrs extends FragmentActivity implements
		OnClickListener, IAsyncCaller {

	private TextView mTxtViewPreBoughtHrs, mTxtViewLeftPreBoughHrs,
			mTxtViewYearMonth;

	private Spinner mSpinnerMonth, mSpinerYear;

	private String[] monthArray = { "Jan", "Fab", "Mar", "Apr", "May", "Jun",
			"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	private String[] yearArray = { "2011", "2013", "2014", "2015" };

	private List<PreBoughtHoursInfo> boughtHrsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pre_bought_hrs);
		getViewsID();
		fetchPreBoughtHrs();
		// addItemInMonth();
		// addItemInYear();
	}

	private void fetchPreBoughtHrs() {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accessToken", Utility.getSharedPrefStringData(this,
				Constants.KEY_ACCESS_TOKEN));
		paramMap.put("memberId",
				Utility.getSharedPrefStringData(this, Constants.KEY_MEMBER_ID));

		PreBoughtHrsParser preBoughtHrsParser = new PreBoughtHrsParser();
		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				this, getResources().getString(R.string.please_wait), true,
				ApiDetails.HOME_URL + ApiDetails.PRE_BOUGHT_HOURS, paramMap,
				ApiDetails.REQUEST_TYPE.POST, this, preBoughtHrsParser);

		Utility.execute(serverIntractorAsync);

	}

	private void getViewsID() {

		mTxtViewPreBoughtHrs = (TextView) findViewById(R.id.txtView_pre_bought_hrs);
		mTxtViewLeftPreBoughHrs = (TextView) findViewById(R.id.txtView_pre_bought_hrs_left);
//		mTxtViewYearMonth = (TextView) findViewById(R.id.textView_year_month);

		// mSpinnerMonth = (Spinner) findViewById(R.id.spinner_month);
		mSpinerYear = (Spinner) findViewById(R.id.spinner_year);

	}

	private void addItemInYear(String[] hrs) {

		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				R.layout.spinner_txtview, hrs);
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinerYear.setAdapter(adapter_state);

		mSpinerYear
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						Log.e("position", pos + "");

						Log.e("left hrs", boughtHrsList.get(pos).getHours());
						String date = boughtHrsList.get(pos).getMonth() + " "
								+ boughtHrsList.get(pos).getYear();
//						mTxtViewYearMonth.setText(date);
						mTxtViewLeftPreBoughHrs.setText(boughtHrsList.get(pos)
								.getHours());

					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});

	}

	@Override
	public void onComplete(Model object) {

		if (object instanceof PreBoughtHoursInfo) {
			PreBoughtHoursInfo preBoughtHoursInfo = (PreBoughtHoursInfo) object;

			if(preBoughtHoursInfo.getStatus()==1)
			{
			if (preBoughtHoursInfo.getHoursList() != null
					&& preBoughtHoursInfo.getHoursList().size() > 0) {
				boughtHrsList = preBoughtHoursInfo.getHoursList();

				String[] hrs = new String[boughtHrsList.size()];

				for (int i = 0; i < boughtHrsList.size(); i++) {
					String date = boughtHrsList.get(i).getMonth() + " "
							+ boughtHrsList.get(i).getYear();
					hrs[i] = date;

				}

				addItemInYear(hrs);
			}
			}
			else
			{
				FragmentAlertDialog alert = new FragmentAlertDialog(
						FragmentAlertDialog.DIALOG_TYPE.OK_ONLY, this
								.getResources().getString(R.string.error_title),
						getResources().getString(R.string.invalid_email));
				alert.show(getSupportFragmentManager(), "Alert_Dialog");
			}

		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {

	}

	@Override
	public void onClick(View v) {

	}

}
