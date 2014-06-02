package com.logindemo.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.logindemo.R;

public class FragmentPopup extends DialogFragment {
	
//	public interface OptionsListener {
//		public void onTakePhoto(DialogFragment dialog);
//		public void onChoosePhoto(DialogFragment dialog);
//	}
	
	DialogInterface.OnClickListener mListener;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mListener = (DialogInterface.OnClickListener) activity;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.choose_photo);
		builder.setItems(R.array.options, mListener);
		
		return builder.create();
	}
}
