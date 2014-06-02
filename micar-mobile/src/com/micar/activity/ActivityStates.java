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

import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.fragment.FragmentAlertDialog;
import com.micar.model.AccessoriesInfo;
import com.micar.model.Model;
import com.micar.model.StateInfo;
import com.micar.parser.StateParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;

public class ActivityStates extends FragmentActivity implements
		OnClickListener, IAsyncCaller {

	private ListView mListViewStates;

	private Button mBtnDone;
	private String cityId;

	private HashMap<String, String> selectedStateMap = new HashMap<String, String>();

	private HashMap<String, String> preSelectedStateMap = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_accessories);
		cityId = getIntent().getStringExtra("cityId");
		getViewsID();
		preSelectedStateMap = (HashMap<String, String>) getIntent()
				.getSerializableExtra("preSelected");
		fetchStates();

	}

	private void getViewsID() {

		mListViewStates = (ListView) findViewById(R.id.listview_accessories);
		mBtnDone = (Button) findViewById(R.id.btn_done);
		mBtnDone.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_done:
			Intent intent = new Intent();
			intent.putExtra("preSelected", selectedStateMap);
			setResult(RESULT_OK, intent);
			finish();

			break;

		default:
			break;
		}

	}

	private class StatesAdapter extends BaseAdapter {
		private List<StateInfo> stateList;
		private ViewHolder holder;
		private LayoutInflater mInflater;

		private Context context;

		private View view = null;

		public StatesAdapter(Context context, List<StateInfo> stateList) {
			this.stateList = stateList;

			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			this.context = context;
		}

		@Override
		public int getCount() {
			return stateList.size();
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
				holder.textViewStateName = (TextView) view
						.findViewById(R.id.txtview_accessories);
				holder.chkBoxState = (CheckBox) view
						.findViewById(R.id.checkbox_accessories);

				holder.chkBoxState
						.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								int getPosition = (Integer) buttonView.getTag();

								stateList.get(getPosition).setChecked(
										buttonView.isChecked());

								if (buttonView.isChecked()) {
									selectedStateMap.put(
											stateList.get(getPosition).getId(),
											stateList.get(getPosition)
													.getName());

								} else {

									selectedStateMap.remove(stateList.get(
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

			holder.textViewStateName.setText(stateList.get(position).getName());

			holder.chkBoxState.setChecked(stateList.get(position).isChecked());

			return view;
		}

		public class ViewHolder {

			private TextView textViewStateName;
			private CheckBox chkBoxState;

		}
	}

	private void fetchStates() {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accessToken", Utility.getSharedPrefStringData(this,
				Constants.KEY_ACCESS_TOKEN));
		paramMap.put("memberId",
				Utility.getSharedPrefStringData(this, Constants.KEY_MEMBER_ID));

		StateParser stateParser = new StateParser();
		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				this, getResources().getString(R.string.please_wait), true,
				ApiDetails.HOME_URL + ApiDetails.FETCH_PERMITTED_STATES,
				paramMap, ApiDetails.REQUEST_TYPE.POST, this, stateParser);

		Utility.execute(serverIntractorAsync);

	}

	@Override
	public void onComplete(Model object) {

		if (object instanceof StateInfo) {
			StateInfo stateInfo = (StateInfo) object;

			if (stateInfo.getStatus() == 1) {
				if (stateInfo.getStateList() != null
						&& stateInfo.getStateList().size() > 0) {

					if (preSelectedStateMap != null
							&& preSelectedStateMap.size() > 0) {

						selectedStateMap.putAll(preSelectedStateMap);

						Iterator<Entry<String, String>> it = preSelectedStateMap
								.entrySet().iterator();
						while (it.hasNext()) {
							Map.Entry pairs = (Map.Entry) it.next();
							// System.out.println(pairs.getKey() + " = "
							// + pairs.getValue());

							for (int i = 0; i < stateInfo.getStateList().size(); i++) {
								List<StateInfo> stateList = stateInfo
										.getStateList();
								if (stateList.get(i).getId()
										.equals(pairs.getKey())) {
									stateList.get(i).setChecked(true);
								}

							}
							it.remove(); // avoids a
											// ConcurrentModificationException
						}

					}

					StatesAdapter accessoriesAdapter = new StatesAdapter(this,
							stateInfo.getStateList());
					mListViewStates.setAdapter(accessoriesAdapter);

				}
			} else {

				String message = stateInfo.getMessage();

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
