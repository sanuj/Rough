package com.micar.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.micar.activity.ActivityLogin;
import com.micar.activity.ActivityMyProfile;
import com.micar.activity.R;
import com.micar.component.CustomProgressDialog;

public class LogOutAsync extends AsyncTask<String, Void, Integer> {

	String response;
	CustomProgressDialog dialog;
	Context context;

	public LogOutAsync(Context context) {
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
		response = webServiceCaller.logOutAPI(arg[0], arg[1]);

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
			
//			((ActivityMyProfile) context).setLogoutResponse(response);
			

		}
	}

}
