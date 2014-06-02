package com.micar.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.micar.activity.ActivityMakeReservation;
import com.micar.activity.R;

public class FragmentAlertDialog extends DialogFragment {

	public interface OnDialogActionListener {
		public void onPositiveClick();

		public void onNegativeClick();
	}

	public enum DIALOG_TYPE {
		OK_ONLY, OK_CANCEL

	};

	private DIALOG_TYPE dialogType;
	private String title, message;

	public FragmentAlertDialog() {

	}

	public FragmentAlertDialog(DIALOG_TYPE dialogType, String title,
			String message) {
		this.dialogType = dialogType;
		this.title = title;
		this.message = message;

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		OnClickListener positiveClick = new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();

				if (getActivity() instanceof ActivityMakeReservation) {
					ActivityMakeReservation activityMakeReservation = (ActivityMakeReservation) getActivity();

					activityMakeReservation.onPositiveClick();
				}
			}
		};

		OnClickListener negativeClick = new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(message);

		switch (dialogType) {
		case OK_ONLY:

			builder.setPositiveButton(
					getActivity().getResources().getString(
							R.string.alert_dialog_ok), positiveClick);

			break;

		case OK_CANCEL:

			builder.setNegativeButton(
					getActivity().getResources().getString(
							R.string.alert_dialog_cancel), negativeClick);
			builder.setPositiveButton(
					getActivity().getResources().getString(
							R.string.alert_dialog_ok), positiveClick);
			break;

		default:
			break;
		}

		builder.setTitle(title);
		Dialog dialog = builder.create();
		return dialog;
	}

}