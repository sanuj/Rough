package com.micar.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.micar.activity.ActivityLogin;
import com.micar.activity.R;
import com.micar.component.CustomProgressDialog;
import com.micar.utils.Utility;

public class LoginAsync extends AsyncTask<String, Void, Integer> {

	String response;
	CustomProgressDialog dialog;
	Context context;

	public LoginAsync(Context context) {
		this.context = context;
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
		response = webServiceCaller.loginAPI(arg[0], arg[1]);

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
		if (result == -1 && !this.isCancelled()) {

			/*
			 * Utility.showOKOnlyDialog(context,
			 * context.getResources().getString(R.string.no_internet_msg),
			 * context.getResources() .getString(R.string.no_internet_title));
			 */

		} else if (!this.isCancelled()) {

			ActivityLogin activityLogin = (ActivityLogin) context;
//			activityLogin.setLoginResponse(response);

		}
	}

}