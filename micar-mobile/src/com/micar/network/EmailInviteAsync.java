package com.micar.network;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.micar.activity.ActivityGmailContacts;
import com.micar.activity.R;
import com.micar.component.CustomProgressDialog;

public class EmailInviteAsync extends AsyncTask<String, Void, Integer> {

	private String response;
	private CustomProgressDialog dialog;
	private Object activityObject;
	
	

	public EmailInviteAsync(Fragment fragment) {
		this.activityObject = fragment;
		dialog = new CustomProgressDialog(fragment.getActivity(),
				fragment.getString(R.string.please_wait));
		dialog.setCancelable(true);
	}
	public EmailInviteAsync(Context context) {
		this.activityObject = context;
		dialog = new CustomProgressDialog(context,
				context.getString(R.string.please_wait));
		dialog.setCancelable(true);
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
	}

	@Override
	protected Integer doInBackground(String... arg) {

		ApiCaller webServiceCaller = new ApiCaller();
		response = webServiceCaller.referrralEmails(arg[0], arg[1], arg[2]);

		if (response == null || response.equalsIgnoreCase("error")) {
			return -1;
		} else {
			return 1;
		}

	}

	@Override
	protected void onPostExecute(Integer result) {

		super.onPostExecute(result);
		if (!this.isCancelled() && dialog != null && dialog.isVisible()) {
			dialog.dismissDialog();
		}
		 if (!this.isCancelled()) {
			
			Log.e("response", response+"");
			
//			if(activityObject instanceof FragmentEmail)
//			{
////				((FragmentEmail)activityObject).setReferralResponse(response);
//			}
//			else if(activityObject instanceof ActivityGmailContacts)
//			{
////				((ActivityGmailContacts)activityObject).setReferralResponse(response);
//			}
			
			

		}
	}

}
