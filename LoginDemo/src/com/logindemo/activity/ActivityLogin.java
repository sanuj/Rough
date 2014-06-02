package com.logindemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.logindemo.R;

public class ActivityLogin extends Activity implements View.OnClickListener {

	
	EditText mEditTxtName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mEditTxtName = (EditText) findViewById(R.id.edit_txt_name);
		Button ButtonSubmit = (Button) findViewById(R.id.btn_submit);
		Button ButtonCancel = (Button) findViewById(R.id.btn_cancel);
		TextView TViewSignUp = (TextView) findViewById(R.id.txt_view_signup);

		ButtonCancel.setOnClickListener(this);
		ButtonSubmit.setOnClickListener(this);
		TViewSignUp.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_cancel:
			finish();
			break;
		case R.id.btn_submit:
			Intent intent = new Intent(this, ActivitySubmit.class);
			startActivity(intent);
			break;
		case R.id.txt_view_signup:
			Intent intentTViewSignup = new Intent(this, ActivitySignup.class);
			startActivityForResult(intentTViewSignup, Constants.SIGNUP_REQUEST);
			break;
		}

	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch (requestCode) {
		case Constants.SIGNUP_REQUEST:
			if (resultCode == Constants.SUCCESS_RETURN_CODE) {
				Bundle b = data.getExtras();
				mEditTxtName.setText(b.getString(Constants.NAME));
			}
		}
	}

}