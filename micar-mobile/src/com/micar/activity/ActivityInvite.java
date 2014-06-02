package com.micar.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.brickred.socialauth.Contact;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.component.CustomProgressDialog;
import com.micar.model.InvitedInfo;
import com.micar.model.Model;
import com.micar.parser.InvitedFriendParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;

public class ActivityInvite extends Activity implements OnClickListener,
		IAsyncCaller {

	private TextView mTextViewEarned;
	private String providerName;
	private LinearLayout linearLayoutInvited;
	private SocialAuthAdapter adapter;
	private CustomProgressDialog dialog;
	private List<InvitedInfo> invitedList;

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}

	@Override
	protected void onResume() {
		super.onResume();

		// if (Utility.isNetworkAvailable(this)) {
		// InvitedFriendsAsync invitedFriendsAsync = new InvitedFriendsAsync(
		// this);
		// invitedFriendsAsync.execute(Utility.getSharedPrefStringData(this,
		// Constants.KEY_USER_ID), Utility.getSharedPrefStringData(
		// this, Constants.KEY_ACCESS_TOKEN));
		//
		// }

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", String.valueOf(Utility.getSharedPrefStringData(this,
				Constants.KEY_MEMBER_ID)));
		paramMap.put("accessToken", Utility.getSharedPrefStringData(this,
				Constants.KEY_ACCESS_TOKEN));
		InvitedFriendParser inviteFriendParser = new InvitedFriendParser();
		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				this, getResources().getString(R.string.please_wait), true,
				ApiDetails.HOME_URL + ApiDetails.FETCH_INVITE_EMAILS, paramMap,
				ApiDetails.REQUEST_TYPE.POST, this, inviteFriendParser);
		Utility.execute(serverIntractorAsync);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite);
		adapter = new SocialAuthAdapter(new ResponseListener());

		getViewsID();

	}

	private void getViewsID() {

		((TextView) findViewById(R.id.textview_from_contact))
				.setOnClickListener(this);
		((TextView) findViewById(R.id.textview_entering_email))
				.setOnClickListener(this);
		((TextView) findViewById(R.id.textview_tweet)).setOnClickListener(this);
		((TextView) findViewById(R.id.textview_share_on_fb))
				.setOnClickListener(this);
		((TextView) findViewById(R.id.textview_yahoo)).setOnClickListener(this);
		((TextView) findViewById(R.id.textview_gmail)).setOnClickListener(this);
		linearLayoutInvited = (LinearLayout) findViewById(R.id.linear_layout_invited);

		mTextViewEarned = (TextView) findViewById(R.id.textview_earned_money);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.textview_from_contact:

//			Intent intentContact = new Intent(ActivityInvite.this,
//					ActivityContacts.class);
//			startActivity(intentContact);

			break;

		case R.id.textview_yahoo:

			inviteRequest("yahoo");
			break;
		case R.id.textview_gmail:

			inviteRequest("google");

			break;

		case R.id.textview_entering_email:

			Intent intentEmail = new Intent(ActivityInvite.this,
					ActivityEnterEmail.class);
			startActivity(intentEmail);

			break;

		case R.id.textview_tweet:

			inviteRequest("twitter");
			// tweet();

			break;
		case R.id.textview_share_on_fb:
			inviteRequest("facebook");
			break;

		default:
			break;
		}

	}

	private void inviteRequest(String provider) {

		if (provider.equalsIgnoreCase("google")) {
			adapter.addCallBack(Provider.GOOGLE,
					"http://micar.qa3.intelligrape.net");
			// This method will enable the selected provider
			adapter.authorize(ActivityInvite.this, Provider.GOOGLE);
		} else if (provider.equalsIgnoreCase("yahoo")) {

			adapter.authorize(ActivityInvite.this, Provider.YAHOO);
		} else if (provider.equalsIgnoreCase("facebook")) {
			adapter.authorize(ActivityInvite.this, Provider.FACEBOOK);
		} else if (provider.equalsIgnoreCase("twitter")) {
			adapter.authorize(ActivityInvite.this, Provider.TWITTER);
			// For twitter use add callback method. Put your own callback url
			// here.
			// adapter.addCallBack(Provider.TWITTER,
			// "http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");
		}

	}

	// To receive the response after authentication
	private final class ResponseListener implements DialogListener {

		@Override
		public void onComplete(Bundle values) {

			dialog = new CustomProgressDialog(ActivityInvite.this,
					ActivityInvite.this.getString(R.string.please_wait));
			dialog.setCancelable(true);

			Log.d("Custom-UI", "Successful");

			// Get the provider
			providerName = values.getString(SocialAuthAdapter.PROVIDER);
			Log.d("Custom-UI", "providername = " + providerName);

			event(providerName);

		}

		@Override
		public void onError(SocialAuthError error) {
			Log.d("Custom-UI", "Error");
			error.printStackTrace();
		}

		@Override
		public void onCancel() {
			Log.d("Custom-UI", "Cancelled");
		}

		@Override
		public void onBack() {
			Log.d("Custom-UI", "Dialog Closed by pressing Back Key");
			if (dialog != null && dialog.isVisible()) {
				dialog.dismissDialog();
			}

		}
	}

	// Method to handle events of providers
	public void event(final String provider) {

		if (provider.equalsIgnoreCase("google")
				|| provider.equalsIgnoreCase("yahoo")) {

			adapter.getContactListAsync(new ContactDataListener());

		} else if (provider.equalsIgnoreCase("facebook")) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			adapter.updateStory("Hey, I am using MiCar!", "MiCar", "",
					"MiCar book your cab",
					"http://micar.qa3.intelligrape.net/",
					"http://fbrell.com/f8.jpg", new MessageListener());

		} else if (provider.equalsIgnoreCase("twitter")) {
			adapter.updateStatus(
					"Hey, I am using MiCar! " + System.currentTimeMillis(),
					new MessageListener(), false);

		}

	}

	// To get status of message after authentication
	private final class MessageListener implements SocialAuthListener<Integer> {
		@Override
		public void onExecute(String provider, Integer t) {
			if (dialog != null && dialog.isVisible()) {
				dialog.dismissDialog();
			}

			Integer status = t;
			if (status.intValue() == 200 || status.intValue() == 201
					|| status.intValue() == 204)
				Toast.makeText(ActivityInvite.this,
						"Message posted on " + provider, Toast.LENGTH_LONG)
						.show();
			else
				Toast.makeText(ActivityInvite.this,
						"Message not posted" + provider, Toast.LENGTH_LONG)
						.show();
		}

		@Override
		public void onError(SocialAuthError e) {

		}
	}

	// To receive the contacts response after authentication
	private final class ContactDataListener implements
			SocialAuthListener<List<Contact>> {

		@Override
		public void onExecute(String provider, List<Contact> t) {

			if (dialog != null && dialog.isVisible()) {
				dialog.dismissDialog();
			}

			List<Contact> contactsList = t;

			Intent intent = new Intent(ActivityInvite.this,
					ActivityGmailContacts.class);
			intent.putExtra("provider", provider);
			intent.putExtra("contact", (Serializable) contactsList);
			startActivity(intent);

		}

		@Override
		public void onError(SocialAuthError e) {

			Log.e("error", e.toString());
			if (dialog != null && dialog.isVisible()) {
				dialog.dismissDialog();
			}

		}
	}

	/**
	 * to add invited friends on views
	 */

	private void addInvitedOnViews() {
		linearLayoutInvited.removeAllViews();

		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		for (int i = 0; i < invitedList.size(); i++) {

			View inflatedView = mInflater.inflate(R.layout.invited_list_row,
					null);

			TextView textviewName = (TextView) inflatedView
					.findViewById(R.id.textview_invited_name);

			TextView textviewEmail = (TextView) inflatedView
					.findViewById(R.id.textview_invited_email);
			TextView textviewStatus = (TextView) inflatedView
					.findViewById(R.id.textview_status);
			textviewName.setText(invitedList.get(i).getInvitedName());
			textviewEmail.setText(invitedList.get(i).getInvitedData());
			textviewStatus.setText("invited");
			linearLayoutInvited.addView(inflatedView);
		}
	}

	public void invitedFriends(InvitedInfo invitedInfo) {

		if (invitedInfo.getStatus() == 1) {
			if (invitedInfo.getInvitedList().size() > 0) {

				invitedList = invitedInfo.getInvitedList();
				addInvitedOnViews();

			}
		}

	}

	@Override
	public void onComplete(Model object) {

		if (object instanceof InvitedInfo) {
			InvitedInfo invitedInfo = (InvitedInfo) object;
			invitedFriends(invitedInfo);

		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {
		// TODO Auto-generated method stub

	}

}
