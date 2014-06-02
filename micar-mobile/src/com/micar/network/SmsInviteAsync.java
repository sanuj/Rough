package com.micar.network;


import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.micar.activity.R;
import com.micar.component.CustomProgressDialog;

public class SmsInviteAsync extends AsyncTask<String, Void, Integer> {

	String response;
	CustomProgressDialog dialog;
	Fragment fragment;

	public SmsInviteAsync(Fragment fragment) {
		this.fragment = fragment;
		dialog = new CustomProgressDialog(fragment.getActivity(),
				fragment.getString(R.string.please_wait));
		dialog.setCancelable(true);
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
	}

	@Override
	protected Integer doInBackground(String... arg) {

		ApiCaller webServiceCaller = new ApiCaller();
		response = webServiceCaller.referrralSMS(arg[0], arg[1], arg[2]);

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
			
//			((FragmentMessage)fragment).setReferralResponse(response);
			

		}
	}

}

