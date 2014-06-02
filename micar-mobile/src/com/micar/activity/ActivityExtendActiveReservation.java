package com.micar.activity;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.micar.model.ActiveReservation;
import com.micar.utils.Utility;

public class ActivityExtendActiveReservation extends Activity implements
		OnClickListener {

	private TextView mTxtViewExpectedEndDate, mTxtViewExpectedEndTime,
			mTxtViewPickupAddress;
	private RelativeLayout mRelativeLytEndDate, mRelativeLytEndTime,
			mRelativeLytPickupAddress;
	private CheckBox mCheckboxPickup;
	private Button mButtonExtend;
	private ActiveReservation activeReservation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_extend_active_reservation);
		getViewsId();

		activeReservation = (ActiveReservation) getIntent()
				.getSerializableExtra("activeReservation");
		setData();
	}

	private void setData() {

		if (activeReservation != null) {

			String dateTime = activeReservation.getExpectedEndDate();

			Date date = new Date(dateTime);

			try {
				mTxtViewExpectedEndDate.setText(Utility
						.getDateInFormatFromMilisecond(date.getTime()));
				mTxtViewExpectedEndTime.setText(Utility
						.getTimeInFormatFromMS(date.getTime()));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void getViewsId() {

		mTxtViewExpectedEndDate = (TextView) findViewById(R.id.txtveiw_expected_end_date);
		mTxtViewExpectedEndTime = (TextView) findViewById(R.id.txtveiw_end_time);
		mTxtViewPickupAddress = (TextView) findViewById(R.id.txtview_pickup_address);
		mCheckboxPickup = (CheckBox) findViewById(R.id.checkbox_pickup);
		mButtonExtend = (Button) findViewById(R.id.btn_extend);

	}

	@Override
	public void onClick(View v) {

	}

}
