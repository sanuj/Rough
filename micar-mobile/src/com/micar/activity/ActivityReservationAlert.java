package com.micar.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class ActivityReservationAlert extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reservation_alert);
	}

}
