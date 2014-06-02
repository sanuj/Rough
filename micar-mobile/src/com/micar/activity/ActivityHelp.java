package com.micar.activity;

import com.micar.utils.Utility;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class ActivityHelp extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		((LinearLayout) findViewById(R.id.linearlayout_call))
				.setOnClickListener(this);
		((LinearLayout) findViewById(R.id.linearlayout_email))
				.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.linearlayout_call:
			
			Utility.makeCall(this, "8527489422");

			break;

		case R.id.linearlayout_email:
			String[] recipients = new String[] { "test@gmail.com" };
			Utility.sendEmail(this, recipients);

			break;

		default:;
			break;
		}

	}

}
