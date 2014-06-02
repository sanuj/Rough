package com.micar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.internal.fo;
import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.fragment.FragmentAlertDialog;
import com.micar.model.AccessoriesInfo;
import com.micar.model.Model;
import com.micar.parser.AccessoriesParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;

public class ActivityAccessories extends FragmentActivity implements
		OnClickListener, IAsyncCaller {
	
	
	

	private ListView mListViewAccessories;

	private Button mBtnDone;

	private HashMap<String, String> selectedAccessoriesMap = new HashMap<String, String>();

	private HashMap<String, String> preSelectedAccessoriesMap = new HashMap<String, String>();

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_accessories);
		getViewsID();

		preSelectedAccessoriesMap = (HashMap<String, String>) getIntent()
				.getSerializableExtra("preSelected");

		fetchAccessorries();

	}

	private void getViewsID() {

		mListViewAccessories = (ListView) findViewById(R.id.listview_accessories);
		mBtnDone = (Button) findViewById(R.id.btn_done);
		mBtnDone.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_done:

			Intent intent = new Intent();
			intent.putExtra("preSelected", selectedAccessoriesMap);
			setResult(RESULT_OK, intent);
			finish();

			break;

		default:
			break;
		}

	}

	public class AccessoriesAdapter extends BaseAdapter {
		private List<AccessoriesInfo> accessoriesList;
		private ViewHolder holder;
		private LayoutInflater mInflater;

		private Context context;

		private View view = null;

		public AccessoriesAdapter(Context context,
				List<AccessoriesInfo> accessoriesList) {
			this.accessoriesList = accessoriesList;

			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			this.context = context;
		}

		@Override
		public int getCount() {
			return accessoriesList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {

			view = convertView;

			if (view == null) {
				holder = new ViewHolder();

				view = mInflater.inflate(R.layout.accessories_row, null, false);
				holder.textViewName = (TextView) view
						.findViewById(R.id.txtview_accessories);
				holder.chkBoxState = (CheckBox) view
						.findViewById(R.id.checkbox_accessories);

				holder.chkBoxState
						.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								int getPosition = (Integer) buttonView.getTag();
								accessoriesList.get(getPosition).setChecked(
										buttonView.isChecked());

								if (buttonView.isChecked()) {

									selectedAccessoriesMap.put(accessoriesList
											.get(getPosition).getId(),
											accessoriesList.get(getPosition)
													.getName());

								} else {

									selectedAccessoriesMap
											.remove(accessoriesList.get(
													getPosition).getId());

								}
							}
						});

				view.setTag(holder);
				view.setTag(R.id.checkbox_accessories, holder.chkBoxState);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.chkBoxState.setTag(position);

			holder.textViewName
					.setText(accessoriesList.get(position).getName());

			holder.chkBoxState.setChecked(accessoriesList.get(position)
					.isChecked());

			return view;
		}

		public class ViewHolder {

			private TextView textViewName;
			private CheckBox chkBoxState;

		}
	}

	private void fetchAccessorries() {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accessToken", Utility.getSharedPrefStringData(this,
				Constants.KEY_ACCESS_TOKEN));
		paramMap.put("memberId",
				Utility.getSharedPrefStringData(this, Constants.KEY_MEMBER_ID));

		AccessoriesParser accessoriesParser = new AccessoriesParser();
		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				this, getResources().getString(R.string.please_wait), true,
				ApiDetails.HOME_URL + ApiDetails.FETCH_ACCESSORIES_TYPE,
				paramMap, ApiDetails.REQUEST_TYPE.POST, this, accessoriesParser);

		Utility.execute(serverIntractorAsync);

	}

	@Override
	public void onComplete(Model object) {

		if (object instanceof AccessoriesInfo) {
			AccessoriesInfo accessoreisInfo = (AccessoriesInfo) object;

			if (accessoreisInfo.getStatus() == 1) {
				if (accessoreisInfo.getAccessoriesList() != null
						&& accessoreisInfo.getAccessoriesList().size() > 0) {

					if (preSelectedAccessoriesMap != null
							&& preSelectedAccessoriesMap.size() > 0) {

						selectedAccessoriesMap
								.putAll(preSelectedAccessoriesMap);

						Iterator<Entry<String, String>> it = preSelectedAccessoriesMap
								.entrySet().iterator();
						while (it.hasNext()) {
							Map.Entry pairs = (Map.Entry) it.next();
							// System.out.println(pairs.getKey() + " = "
							// + pairs.getValue());

							for (int i = 0; i < accessoreisInfo
									.getAccessoriesList().size(); i++) {
								List<AccessoriesInfo> accessoriesList = accessoreisInfo
										.getAccessoriesList();
								if (accessoriesList.get(i).getId()
										.equals(pairs.getKey())) {
									accessoriesList.get(i).setChecked(true);
								}

							}
							it.remove(); // avoids a
											// ConcurrentModificationException
						}

					}

					AccessoriesAdapter accessoriesAdapter = new AccessoriesAdapter(
							this, accessoreisInfo.getAccessoriesList());
					mListViewAccessories.setAdapter(accessoriesAdapter);

				}
			} else {

				String message = accessoreisInfo.getMessage();

				if (message != null && !message.equalsIgnoreCase("")) {
					FragmentAlertDialog alert = new FragmentAlertDialog(
							FragmentAlertDialog.DIALOG_TYPE.OK_ONLY, this
									.getResources().getString(
											R.string.error_title), message);
					alert.show(getSupportFragmentManager(), "Alert_Dialog");
				}
			}
		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {

	}

}
