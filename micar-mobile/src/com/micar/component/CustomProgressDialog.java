package com.micar.component;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.micar.activity.R;


public class CustomProgressDialog {

	private Context context;
	private Dialog dialog;
	private String message;

	public CustomProgressDialog(Context context, String message) {
		this.context = context;
		this.message = message;
		showDialog();
	}

	public void setCancelable(boolean flag) {
		if (dialog != null)
			dialog.setCancelable(flag);
	}

	public boolean isVisible() {
		if (dialog != null)
			return dialog.isShowing();
		else {
			return false;
		}
	}

	private void showDialog() {
		try {
			dialog = new Dialog(context,
					android.R.style.Theme_Translucent_NoTitleBar);
			dialog.setContentView(R.layout.progress_dialog_wt_message);
			(((TextView) ((Dialog) dialog)
					.findViewById(R.id.dialog_message_usr))).setText(message);
			dialog.show();
		} catch (Exception e) {

		}
	}

	public void dismissDialog() {
		if (dialog != null && dialog.isShowing())
			dialog.dismiss();
	}

}
