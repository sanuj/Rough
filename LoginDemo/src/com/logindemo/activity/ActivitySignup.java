package com.logindemo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.logindemo.R;
import com.logindemo.fragment.DatePickerFragment;
import com.logindemo.fragment.FragmentPopup;
import com.logindemo.utils.EditTxtUtil;

public class ActivitySignup extends FragmentActivity implements
		View.OnClickListener, DialogInterface.OnClickListener,
		DatePickerFragment.DailogOnDateSet {

	private EditText mEditTxtName;
	private EditText mEditTxtEmail;
	private EditText mEditTxtPswd;
	private EditText mEditTxtConfPswd;
	private static TextView mTxtViewDob;
	private ImageView mImgViewProfilePic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		Button btnRegister = (Button) findViewById(R.id.btn_register);
		Button btnCancel = (Button) findViewById(R.id.btn_cancel_signup);

		btnCancel.setOnClickListener(this);
		btnRegister.setOnClickListener(this);

		mEditTxtName = (EditText) findViewById(R.id.edit_txt_name);
		mEditTxtEmail = (EditText) findViewById(R.id.edit_txt_email);
		mEditTxtPswd = (EditText) findViewById(R.id.edit_txt_pswd);
		mEditTxtConfPswd = (EditText) findViewById(R.id.edit_txt_conf_pswd);
		mTxtViewDob = (TextView) findViewById(R.id.txt_view_dob);
		mImgViewProfilePic = (ImageView) findViewById(R.id.image_view_profile);
		final TextView txtViewError = (TextView) findViewById(R.id.txt_view_error);

		mTxtViewDob.setOnClickListener(this);
		mImgViewProfilePic.setOnClickListener(this);

		mEditTxtConfPswd.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

				if (EditTxtUtil.confirmPassword(mEditTxtPswd, mEditTxtConfPswd))
					txtViewError.setText(R.string.pswd_match);
				else
					txtViewError.setText(R.string.pswd_notmatch);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_cancel_signup:
			finish();
			break;
		case R.id.btn_register:
			String registerMsg = new String("");
			if (EditTxtUtil.isEmpty(mEditTxtName)
					|| EditTxtUtil.isEmpty(mEditTxtEmail)
					|| EditTxtUtil.isEmpty(mEditTxtPswd)
					|| EditTxtUtil.isEmpty(mEditTxtConfPswd))
				registerMsg = registerMsg.concat("Enter all fields.");
			else if (!EditTxtUtil.isEmail(mEditTxtEmail))
				registerMsg = registerMsg.concat("Invalid Email");
			else if (!EditTxtUtil.confirmPassword(mEditTxtPswd,
					mEditTxtConfPswd))
				registerMsg = registerMsg.concat("Retype Password");
			else {
				savePrefernces();
				Intent intent = new Intent();
				Bundle b = new Bundle();
				b.putString(Constants.NAME, mEditTxtName.getText().toString());
				intent.putExtras(b);
				setResult(Constants.SUCCESS_RETURN_CODE, intent);
				finish();
				break;
			}
			Toast toast = Toast.makeText(getApplicationContext(), registerMsg,
					Toast.LENGTH_SHORT);
			toast.show();
			break;
		case R.id.txt_view_dob:
			DialogFragment newDatePicker = new DatePickerFragment(this, 18);
			newDatePicker.show(getSupportFragmentManager(), "datePicker");
			break;
		case R.id.image_view_profile:
			DialogFragment newPhotoChooser = new FragmentPopup();
			newPhotoChooser.show(getSupportFragmentManager(), "photoChooser");
			break;
		}

	}

	private void savePrefernces() {
		SharedPreferences userInfo = getSharedPreferences(Constants.PREFS_NAME,
				0);
		SharedPreferences.Editor editor = userInfo.edit();
		editor.putString("name", mEditTxtName.getText().toString());
		editor.putString("Email", mEditTxtEmail.getText().toString());
		editor.putString("password", mEditTxtPswd.getText().toString());
		editor.commit();
	}

	@Override
	public void onClick(DialogInterface dialog, int option) {

		switch (option) {
		case Constants.TAKE_PHOTO:
			Intent takePictureIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			if (takePictureIntent.resolveActivity(getPackageManager()) != null)
				startActivityForResult(takePictureIntent,
						Constants.REQUEST_IMAGE_CAPTURE);
			break;
		case Constants.CHOOSE_PHOTO:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Constants.REQUEST_IMAGE_CAPTURE
				&& resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap image = (Bitmap) extras.get("data");
			mImgViewProfilePic.setImageBitmap(image);
		}
	}

	@Override
	public void onDateSetListener(int year, int monthOfYear, int dayOfMonth) {
		mTxtViewDob.setText("" + dayOfMonth + " / " + monthOfYear + " / " + year);
	}

}
