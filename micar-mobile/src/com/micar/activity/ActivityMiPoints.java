package com.micar.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.TextView;

import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.fragment.FragmentAlertDialog;
import com.micar.model.MiPointsInfo;
import com.micar.model.Model;
import com.micar.parser.MiPointsParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;

public class ActivityMiPoints extends FragmentActivity implements IAsyncCaller {

	private TextView mTxtViewTotal, mTxtViewEarned, mTxtViewBurned;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mi_points);
		getViewsId();
		fetchMiPoints();
	}

	private void fetchMiPoints() {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accessToken", Utility.getSharedPrefStringData(this,
				Constants.KEY_ACCESS_TOKEN));
		paramMap.put("memberId",
				Utility.getSharedPrefStringData(this, Constants.KEY_MEMBER_ID));

		MiPointsParser miPointsParser = new MiPointsParser();
		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				this, getResources().getString(R.string.please_wait), true,
				ApiDetails.HOME_URL + ApiDetails.FETCH_MEMBER_MI_POINTS,
				paramMap, ApiDetails.REQUEST_TYPE.POST, this, miPointsParser);

		Utility.execute(serverIntractorAsync);

	}

	private void getViewsId() {

		mTxtViewTotal = (TextView) findViewById(R.id.txtView_mi_points_total);
		mTxtViewEarned = (TextView) findViewById(R.id.txtView_mipoints_earned);
		mTxtViewBurned = (TextView) findViewById(R.id.txtView_mi_points_burned);

	}

	@Override
	public void onComplete(Model model) {
		if (model instanceof MiPointsInfo) {
			MiPointsInfo miPointsInfo = (MiPointsInfo) model;

			if (miPointsInfo != null) {

				setMiPoints(miPointsInfo);
			}

		}

	}

	private void setMiPoints(MiPointsInfo miPointsInfo) {

		if (miPointsInfo.getStatus() == 1) {

			mTxtViewTotal.setText(miPointsInfo.getTotalPoints());
			mTxtViewEarned.setText(miPointsInfo.getEarnedPoints());
			mTxtViewBurned.setText(miPointsInfo.getBurnedPoints());

		} else {
			String message = miPointsInfo.getMessage();

			if (message != null && !message.equalsIgnoreCase("")) {
				FragmentAlertDialog alert = new FragmentAlertDialog(
						FragmentAlertDialog.DIALOG_TYPE.OK_ONLY,
						this.getResources().getString(R.string.error_title),
						message);
				alert.show(getSupportFragmentManager(), "Alert_Dialog");
			}
		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {

	}

}
