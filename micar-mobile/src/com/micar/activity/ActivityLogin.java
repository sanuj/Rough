package com.micar.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.fragment.FragmentAlertDialog;
import com.micar.model.LoginInfo;
import com.micar.model.MessageInfo;
import com.micar.model.Model;
import com.micar.parser.LoginParser;
import com.micar.parser.MessageParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;
import com.micar.utils.ValidateInputFields;

/**
 * 
 * @author rajendra
 * 
 */
public class ActivityLogin extends FragmentActivity implements OnClickListener,
		IAsyncCaller {

	private EditText mEdtTextEmail, mEdtTextPassword;
	private TextView mtextViewError, mTextViewAbtMicar, mTextViewPlan,
			mTextViewWhy, mTextViewHow;
	private CheckBox mChkBoxRmberMe;

	private boolean mRememberMe;
	private Context mContext;

	@Override
	protected void onStart() {

		super.onStart();

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mContext = this;
		if (Utility.getSharedPrefBooleanData(this, Constants.KEY_REMEMBER_ME))

		{
			Intent intent = new Intent(this, ActivityMicarHome.class);
			startActivity(intent);
			finish();

		}
		setContentView(R.layout.activity_login);

		getViewsID();
		// initializeAnalytics();

	}

	private void getViewsID() {

		mEdtTextEmail = (EditText) findViewById(R.id.edittext_email);
		mEdtTextPassword = (EditText) findViewById(R.id.edittext_password);
		mtextViewError = (TextView) findViewById(R.id.textview_error_msg);
		((Button) findViewById(R.id.button_login)).setOnClickListener(this);
		TextView txtViewForgotPassword = ((TextView) findViewById(R.id.textview_forgot_password));
		txtViewForgotPassword.setText(Html
				.fromHtml(getString(R.string.forgot_password)));
		txtViewForgotPassword.setOnClickListener(this);

		TextView txtViewDetails = (TextView) findViewById(R.id.txtView_regst_info);
		Spanned spanned = Html.fromHtml(getString(R.string.registration_info));
		txtViewDetails.setMovementMethod(LinkMovementMethod.getInstance());
		txtViewDetails.setText(spanned);
		mChkBoxRmberMe = (CheckBox) findViewById(R.id.checkbox_remember_me);

		mChkBoxRmberMe.setOnCheckedChangeListener(rememberCheckListener);

		mEdtTextEmail.setText("rajendra+1@intelligrape.com"); // tushar.saxena@intelligrape.com
																// ,ajey@ig.com
		mEdtTextPassword.setText("Password1"); // welcome ,ajey@ig.com

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_login:

			if (validateField()) {
				//
				// Intent intent = new Intent(this, ActivityMicarHome.class);
				// startActivity(intent);

				login();

			}
			break;
		case R.id.textview_forgot_password:

			if (mEdtTextEmail.getText().toString().equalsIgnoreCase("")) {
				AlertDialog alertDialog = Utility.showOKOnlyDialog(this,
						getResources().getString(R.string.enter_email_msg),
						getResources().getString(R.string.enter_email));
				alertDialog.show();
			} else if (!ValidateInputFields.isEmailValid(mEdtTextEmail
					.getText().toString())) {
				mtextViewError.setText(getResources().getString(
						R.string.invalid_email));
				// mtextViewError.setVisibility(View.VISIBLE);

			} else {
				// mtextViewError.setVisibility(View.INVISIBLE);

				// ForgotPasswordAsync forgotPasswordAsync = new
				// ForgotPasswordAsync(
				// this);
				// forgotPasswordAsync.execute(mEdtTextEmail.getText()
				// .toString());

				HashMap<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("email", mEdtTextEmail.getText().toString());

				MessageParser msgParser = new MessageParser();
				ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
						mContext, getResources()
								.getString(R.string.please_wait), true,
						ApiDetails.HOME_URL + ApiDetails.FORGOT_PASSWORD,
						paramMap, ApiDetails.REQUEST_TYPE.POST, this, msgParser);

				Utility.execute(serverIntractorAsync);

			}

			break;

		default:
			break;
		}

	}

	private void login() {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("email", mEdtTextEmail.getText().toString());
		paramMap.put("password", mEdtTextPassword.getText().toString());

		LoginParser loginParser = new LoginParser();
		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				mContext, getResources().getString(R.string.please_wait), true,
				ApiDetails.HOME_URL + ApiDetails.LOGIN, paramMap,
				ApiDetails.REQUEST_TYPE.POST, this, loginParser);

		Utility.execute(serverIntractorAsync);

	}

	private boolean validateField() {

		if (mEdtTextEmail.getText().toString().equalsIgnoreCase("")) {

			FragmentAlertDialog alert = new FragmentAlertDialog(
					FragmentAlertDialog.DIALOG_TYPE.OK_ONLY, this
							.getResources().getString(R.string.error_title),
					getResources().getString(R.string.email_required));
			alert.show(getSupportFragmentManager(), "Alert_Dialog");

			return false;
		}

		else if (!ValidateInputFields.isEmailValid(mEdtTextEmail.getText()
				.toString())) {

			FragmentAlertDialog alert = new FragmentAlertDialog(
					FragmentAlertDialog.DIALOG_TYPE.OK_ONLY, this
							.getResources().getString(R.string.error_title),
					getResources().getString(R.string.invalid_email));
			alert.show(getSupportFragmentManager(), "Alert_Dialog");

			return false;
		}

		else if (mEdtTextPassword.getText().toString().equalsIgnoreCase("")) {

			FragmentAlertDialog alert = new FragmentAlertDialog(
					FragmentAlertDialog.DIALOG_TYPE.OK_ONLY, this
							.getResources().getString(R.string.error_title),
					getResources().getString(R.string.email_required));
			alert.show(getSupportFragmentManager(), "Alert_Dialog");
			return false;
		}

		return true;
	}

	public void setLoginResponse(LoginInfo loginInfo) {

		if (loginInfo.getStatus() == 1) {
			Utility.setSharedPreStringData(this, Constants.KEY_MEMBER_ID,
					loginInfo.getMemberId());
			Utility.setSharedPreStringData(this, Constants.KEY_ACCESS_TOKEN,
					loginInfo.getAccessToken());

			Utility.setSharedPrefBooleanData(this, Constants.KEY_REMEMBER_ME,
					mRememberMe);
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
				FragmentAlertDialog alert = new FragmentAlertDialog(
						FragmentAlertDialog.DIALOG_TYPE.OK_ONLY,
						this.getResources().getString(R.string.error_title),
						message);
				alert.show(getSupportFragmentManager(), "Alert_Dialog");
			}
		}

	}

	public void setForgotPwdResult(MessageInfo msgInfo) {

		if (msgInfo.getStatus() == 1) {

			String msg = String.format(
					getResources().getString(R.string.email_sent_msg),
					mEdtTextEmail.getText().toString());

			Utility.showOKOnlyDialog(this, msg,
					getResources().getString(R.string.email_sent_title)).show();
		} else {
			String msg = String.format(
					getResources().getString(R.string.not_register_msg),
					mEdtTextEmail.getText().toString());

			Utility.showOKOnlyDialog(this, msg,
					getResources().getString(R.string.error_title)).show();
		}

	}

	android.widget.CompoundButton.OnCheckedChangeListener rememberCheckListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {

				mRememberMe = true;

			} else {
				mRememberMe = false;
			}
		}
	};

	@Override
	public void onComplete(Model object) {

		if (object instanceof LoginInfo) {

			LoginInfo loginInfo = (LoginInfo) object;

			setLoginResponse(loginInfo);

		} else if (object instanceof MessageInfo) {
			MessageInfo msgInfo = (MessageInfo) object;
			setForgotPwdResult(msgInfo);

		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {

	}
}
