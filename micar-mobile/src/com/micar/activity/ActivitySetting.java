package com.micar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivitySetting extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		((TextView) findViewById(R.id.textview_help))

		.setOnClickListener(this);
		((TextView) findViewById(R.id.textview_invites))

		.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.textview_help:

			Intent intentHelp = new Intent(this, ActivityHelp.class);
			startActivity(intentHelp);

			break;
		case R.id.textview_invites:

			Intent intentInvite = new Intent(this, ActivityInvite.class);
			startActivity(intentInvite);

			break;

		default:
			break;
		}

	}

}
