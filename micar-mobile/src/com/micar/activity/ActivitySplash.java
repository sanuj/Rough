package com.micar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.model.LoginInfo;
import com.micar.model.Model;
import com.micar.parser.LoginParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;

public class ActivitySplash extends FragmentActivity implements IAsyncCaller {
	Timer timer = new Timer();
	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (Utility.getSharedPrefBooleanData(this,
				com.micar.utils.Constants.KEY_REMEMBER_ME)) {

			validateAccessToken();

		} else {

			loading();

		}
	}

	private void loading() {

		if (Utility.isNetworkAvailable(this)) {
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					handler.post(new Runnable() {

						@Override
						public void run() {

							startLoginActivity();

						}

					});

				}
			}, 3000);
		} else {

			Utility.showSettingDialog(this,
					this.getResources().getString(R.string.no_internet_msg),
					this.getResources().getString(R.string.no_internet_title),
					Utility.NO_INTERNET_CONNECTION).show();
		}

	}

	private void validateAccessToken() {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accessToken", Utility.getSharedPrefStringData(this,
				Constants.KEY_ACCESS_TOKEN));
		paramMap.put("memberId",
				Utility.getSharedPrefStringData(this, Constants.KEY_MEMBER_ID));

		LoginParser loginParser = new LoginParser();
		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				this, getResources().getString(R.string.please_wait), false,
				ApiDetails.HOME_URL + ApiDetails.VALIDATE_ACCESS_TOKEN,
				paramMap, ApiDetails.REQUEST_TYPE.POST, this, loginParser);

		Utility.execute(serverIntractorAsync);

	}

	@Override
	public void onComplete(Model object) {

		if (object instanceof LoginInfo) {

			LoginInfo loginInfo = (LoginInfo) object;

			setToken(loginInfo);

		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {
		// TODO Auto-generated method stub

	}

	public void setToken(LoginInfo loginInfo) {

		if (loginInfo.getStatus() == 1) {
			Utility.setSharedPreStringData(this, Constants.KEY_MEMBER_ID,
					loginInfo.getMemberId());
			Utility.setSharedPreStringData(this, Constants.KEY_ACCESS_TOKEN,
					loginInfo.getAccessToken());
			Utility.setSharedPreStringData(this, Constants.KEY_REFERRAL_CODE,
					loginInfo.getReferralCode());
			Utility.setSharedPrefBooleanData(this,
					Constants.KEY_HAS_ACTIVE_RESERVATION,
					loginInfo.isActiveReservation());

			Intent intent = new Intent(this, ActivityMicarHome.class);
			intent.putExtra("loginInfo", loginInfo);
			startActivity(intent);
			finish();
		} else {

			String message = loginInfo.getMessage();

			if (message != null && !message.equalsIgnoreCase("")) {

				startLoginActivity();
				/*
				 * FragmentAlertDialog alert = new FragmentAlertDialog(
				 * FragmentAlertDialog.DIALOG_TYPE.OK_ONLY,
				 * this.getResources().getString(R.string.error_title),
				 * message); alert.show(getSupportFragmentManager(),
				 * "Alert_Dialog");
				 */
			}
		}

	}

	private void startLoginActivity() {

		Intent intent = new Intent(ActivitySplash.this, ActivityLogin.class);
		startActivity(intent);
		finish();
	}
}
