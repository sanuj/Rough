package com.micar.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.micar.activity.ActivityLogin;
import com.micar.activity.R;
import com.micar.component.CustomProgressDialog;

public class ForgotPasswordAsync extends AsyncTask<String, Void, Integer> {

	String response;
	CustomProgressDialog dialog;
	Context context;

	public ForgotPasswordAsync(Context context) {
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
		response = webServiceCaller.forgotPswdAPI(arg[0]);

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

		} else if (!this.isCancelled()) {
			
			ActivityLogin activityLogin=(ActivityLogin) context;
//			activityLogin.setForgotResult(response);
			
			Log.e("response", response+"");

		}
	}

}
