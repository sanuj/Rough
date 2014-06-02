package com.micar.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.model.MessageInfo;
import com.micar.model.Model;
import com.micar.parser.MessageParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;

public class ActivityMyProfile extends Activity implements OnClickListener,
		IAsyncCaller {
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_profile);
		mContext = this;
		getViewsID();

	}

	private void getViewsID() {

		((TextView) findViewById(R.id.textview_signout))
				.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.textview_signout:

			// if (Utility.isNetworkAvailable(this)) {
			//
			// LogOutAsync logOutAsync = new LogOutAsync(this);
			// logOutAsync.execute(String.valueOf(Utility
			// .getSharedPrefStringData(this, Constants.KEY_USER_ID)),
			// Utility.getSharedPrefStringData(this,
			// Constants.KEY_ACCESS_TOKEN));
			//
			// } else {
			// Utility.showSettingDialog(this,
			// getResources().getString(R.string.no_internet_msg),
			// getResources().getString(R.string.no_internet_title),
			// Utility.NO_INTERNET_CONNECTION).show();
			// }

			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("id", String.valueOf(Utility.getSharedPrefStringData(
					this, Constants.KEY_MEMBER_ID)));
			paramMap.put("accessToken", Utility.getSharedPrefStringData(this,
					Constants.KEY_ACCESS_TOKEN));

			MessageParser msgParser = new MessageParser();

			ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
					mContext, getResources().getString(R.string.please_wait),
					true, ApiDetails.HOME_URL + ApiDetails.LOG_OUT, paramMap,
					ApiDetails.REQUEST_TYPE.POST, this, msgParser);

			Utility.execute(serverIntractorAsync);

			break;

		default:
			break;
		}

	}

	private void setLogoutResponse(MessageInfo msgInfo) {

		if (msgInfo.getStatus() == 1) {

			Intent intent = new Intent(ActivityMyProfile.this,
					ActivityStationsInMap.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("logout", true);
			startActivity(intent);
			finish();

		} else {
			Utility.showOKOnlyDialog(this,
					getResources().getString(R.string.error_msg),
					getResources().getString(R.string.error_title)).show();

		}

	}

	@Override
	public void onComplete(Model object) {

		if (object instanceof MessageInfo) {
			MessageInfo msgInfo = (MessageInfo) object;
			setLogoutResponse(msgInfo);
		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {

	}

}
