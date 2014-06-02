package com.micar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.fragment.FragmentAlertDialog;
import com.micar.model.MessageInfo;
import com.micar.model.Model;
import com.micar.parser.MessageParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;
import com.micar.utils.ValidateInputFields;

public class ActivityEnterEmail extends FragmentActivity implements
		OnClickListener, IAsyncCaller {

	private EditText mEditTextEmails;
	// private TextView mTextViewError;
	private List<String> emailsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_enter_email);
		emailsList = new ArrayList<String>();
		getViewsID();

	}

	private void getViewsID() {

		mEditTextEmails = (EditText) findViewById(R.id.edittext_emails);
		// mTextViewError = (TextView) findViewById(R.id.textview_error);
		((Button) findViewById(R.id.button_invite)).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.button_invite:

			emailsList.clear();

			inviteFriends();

			break;

		default:
			break;
		}

	}

	private void inviteFriends() {
		if (validateField()) {

			if (emailsList.size() <= Constants.EMAIL_LIMITATION) {

				JSONArray jsonArray = new JSONArray();
				for (int sel = 0; sel < emailsList.size(); sel++) {
					JSONObject jsonObject = new JSONObject();

					try {
						jsonObject.put("email", emailsList.get(sel));
						jsonObject.put("name", emailsList.get(sel));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					jsonArray.put(jsonObject);

				}

				HashMap<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("data", jsonArray.toString());
				paramMap.put("memberId",
						String.valueOf(Utility.getSharedPrefStringData(this,
								Constants.KEY_MEMBER_ID)));
				paramMap.put("accessToken", Utility.getSharedPrefStringData(
						this, Constants.KEY_ACCESS_TOKEN));
				MessageParser msgParser = new MessageParser();
				ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
						this, getResources().getString(R.string.please_wait),
						true, ApiDetails.HOME_URL + ApiDetails.REFERRALS,
						paramMap, ApiDetails.REQUEST_TYPE.POST, this, msgParser);
				Utility.execute(serverIntractorAsync);

			} else {
				Utility.showOKOnlyDialog(this,
						getResources().getString(R.string.email_xceed_msg),
						getResources().getString(R.string.xceed_title)).show();
			}

		}

	}

	private boolean validateField() {

		if (mEditTextEmails.getText().toString().equalsIgnoreCase("")) {

			showFragmentDialog(getResources().getString(
					R.string.enter_frnd_email));

			return false;
		}

		else {

			StringTokenizer emailsTkenizer = new StringTokenizer(
					mEditTextEmails.getText().toString(), ",");

			while (emailsTkenizer.hasMoreElements()) {
				String email = emailsTkenizer.nextToken().trim();
				Log.e("email", email);
				if (!ValidateInputFields.isEmailValid(email)) {

					showFragmentDialog(getResources().getString(
							R.string.invalid_email));
					return false;
				} else {
					emailsList.add(email);
				}
			}

		}

		return true;
	}

	private void showFragmentDialog(String msg) {
		FragmentAlertDialog alert = new FragmentAlertDialog(
				FragmentAlertDialog.DIALOG_TYPE.OK_ONLY, this.getResources()
						.getString(R.string.error_title), msg);
		alert.show(getSupportFragmentManager(), "Alert_Dialog");

	}

	public void setResponse(MessageInfo msgInfo) {

		if (msgInfo.getStatus() == 1) {

			String message = msgInfo.getMessage();

			mEditTextEmails.setText("");
			Utility.showOKOnlyDialog(this, message,
					getResources().getString(R.string.invited_title)).show();

		} else {

			String msg = msgInfo.getMessage(); /*
												 * getResources().getString(R.string
												 * .error_msg)
												 */
			;

			if (msg.equalsIgnoreCase("")) {
				msg = getResources().getString(R.string.error_msg);
			}

			Utility.showOKOnlyDialog(this, msg,
					getResources().getString(R.string.error_title)).show();

		}

	}

	@Override
	public void onComplete(Model object) {
		if (object instanceof MessageInfo) {
			MessageInfo msgInfo = (MessageInfo) object;
			setResponse(msgInfo);
		}
	}

	@Override
	public void onComplete(ArrayList<Model> object) {

	}

}
