package com.micar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.micar.model.ActiveReservation;

public class ActivityActiveReservationDetail extends FragmentActivity implements
		OnClickListener {

	private TextView mTxtViewStartDate, mTxtViewEndDate,
			mTxtViewActualStartDate, mTxtViewReservationId,
			mTxtViewReservationType;
	private Button mBtnExtend, mBtnEnd;

	ActiveReservation activeReservation;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_active_reservation_detail);
		activeReservation = (ActiveReservation) getIntent()
				.getSerializableExtra("activeReservation");

		getViewsId();
		setData();
	}

	private void setData() {

		if (activeReservation != null) {
			mTxtViewStartDate.setText(activeReservation.getExpectedStartDate());
			mTxtViewEndDate.setText(activeReservation.getExpectedEndDate());
			mTxtViewActualStartDate.setText(activeReservation
					.getActualStartDate());
			mTxtViewReservationId.setText(activeReservation.getReservationId());
			mTxtViewReservationType.setText(activeReservation
					.getReservationMemberType());
		}

	}

	private void getViewsId() {
		mTxtViewStartDate = (TextView) findViewById(R.id.txtview_expected_start_Date);
		mTxtViewEndDate = (TextView) findViewById(R.id.txtview_expected_end_date);
		mTxtViewActualStartDate = (TextView) findViewById(R.id.txtview_actual_start_date);
		mTxtViewReservationId = (TextView) findViewById(R.id.txtview_reservation_id);

		mTxtViewReservationType = (TextView) findViewById(R.id.txtview_reservation_for);

		mBtnExtend = (Button) findViewById(R.id.btn_extend);
		mBtnEnd = (Button) findViewById(R.id.btn_end);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_extend:

			extendActiveReservation();

			break;
		case R.id.btn_end:

			break;

		default:
			break;
		}

	}

	private void extendActiveReservation() {

		if (activeReservation != null) {

			Intent intent = new Intent(ActivityActiveReservationDetail.this,
					ActivityExtendActiveReservation.class);
			intent.putExtra("activeReservation", activeReservation);
			startActivity(intent);
		}

	}

}
