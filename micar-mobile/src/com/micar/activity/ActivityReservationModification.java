package com.micar.activity;

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.micar.fragment.FragmentAlertDialog;
import com.micar.fragment.FragmentDatePicker;
import com.micar.fragment.FragmentTimePicker;
import com.micar.fragment.FragmentDatePicker.ONDateSelectedListerner;
import com.micar.fragment.FragmentTimePicker.OnTimeSelectedListener;

public class ActivityReservationModification extends FragmentActivity implements
		OnClickListener, ONDateSelectedListerner, OnTimeSelectedListener {

	private Button mBtnCancel, mBtnDone;
	private RelativeLayout mRelativeLytFromDate, mRelativeLytFromTime,
			mRelativeLytToDate, mRelativeLytToTime, mRelativeLytPickup,
			mRelativeLytDelivery, mRelativeLytAccessroies;
	private TextView mTxtViewCarType, mTxtViewToDate, mTxtViewToTime,
			mTxtViewFromDate, mTxtViewFromTime;

	private CheckBox mChkBoxPickup, mChkBoxDelivery, mChkBoxInsuranceClaim;

	private boolean isFromDate;
	private boolean isFromTime;

	private int mSelectedFromYear;
	private int mSelectedFromMonth;
	private int mSelectedFromDay;

	private Calendar mCurrentCalendar;
	private String mSelectedCity;

	private boolean canModify = false;

	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reservation_modification);
		mCurrentCalendar = Calendar.getInstance();
		getViewsId();
	}

	private void getViewsId() {

		mBtnCancel = (Button) findViewById(R.id.btn_cancel);
		mBtnDone = (Button) findViewById(R.id.btn_done);
		mBtnCancel.setOnClickListener(this);
		mBtnDone.setOnClickListener(this);

		mRelativeLytFromDate = (RelativeLayout) findViewById(R.id.relative_layout_from_date);
		mRelativeLytFromTime = (RelativeLayout) findViewById(R.id.relative_from_time);
		mRelativeLytToDate = (RelativeLayout) findViewById(R.id.relative_layout_to_date);
		mRelativeLytToTime = (RelativeLayout) findViewById(R.id.relative_layout_to_time);
		mRelativeLytPickup = (RelativeLayout) findViewById(R.id.relative_lyt_pickup);
		mRelativeLytDelivery = (RelativeLayout) findViewById(R.id.relative_lyt_delivery);
		mRelativeLytAccessroies = (RelativeLayout) findViewById(R.id.relative_lyt_acessories);

		mChkBoxPickup = (CheckBox) findViewById(R.id.checkbox_pickup);
		mChkBoxDelivery = (CheckBox) findViewById(R.id.checkbox_delivery);
		mChkBoxInsuranceClaim = (CheckBox) findViewById(R.id.checkbox_insurance);

		mTxtViewCarType = (TextView) findViewById(R.id.txtveiw_carname);
		mTxtViewToDate = (TextView) findViewById(R.id.textView_to_date);
		mTxtViewToTime = (TextView) findViewById(R.id.textView_to_time);
		mTxtViewFromDate = (TextView) findViewById(R.id.textView_from_date);
		mTxtViewFromTime = (TextView) findViewById(R.id.textView_from_time);

		mRelativeLytFromDate.setOnClickListener(this);
		mRelativeLytFromTime.setOnClickListener(this);
		mRelativeLytToDate.setOnClickListener(this);
		mRelativeLytToTime.setOnClickListener(this);
		mRelativeLytPickup.setOnClickListener(this);
		mRelativeLytDelivery.setOnClickListener(this);
		mRelativeLytAccessroies.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_done:

			startAlertActivity();

			break;

		case R.id.btn_cancel:

			finish();

		case R.id.relative_layout_from_date:
			isFromDate = true;
			showDatePickerDialog();

			break;
		case R.id.relative_from_time:
			isFromTime = true;
			showTimePickerDialog();

			break;
		case R.id.relative_layout_to_date:
			isFromDate = false;
			showDatePickerDialog();

			break;
		case R.id.relative_layout_to_time:
			isFromTime = false;
			showTimePickerDialog();

		case R.id.relative_lyt_pickup:

			if (canModify) {

			} else {

			}

			break;
		case R.id.relative_lyt_delivery:
			break;
		case R.id.relative_lyt_acessories:
			break;

		default:
			break;
		}

	}

	private void startAlertActivity() {
		Intent intent = new Intent(ActivityReservationModification.this,
				ActivityReservationAlert.class);
		startActivity(intent);

	}

	public void showTimePickerDialog() {

		Calendar calendar = Calendar.getInstance();
		DialogFragment newFragment = new FragmentTimePicker(this, calendar);
		newFragment.show(ActivityReservationModification.this
				.getSupportFragmentManager(), "timePicker");
	}

	public void showDatePickerDialog() {
		DialogFragment newFragment = new FragmentDatePicker(this);
		newFragment.show(ActivityReservationModification.this
				.getSupportFragmentManager(), "datePicker");

	}

	@Override
	public void onTimeSelect(int hourOfDay, int minute) {

		if (isFromTime) {

			Calendar selectedDate = Calendar.getInstance();

			selectedDate.set(selectedDate.get(Calendar.YEAR),
					selectedDate.get(Calendar.MONTH),
					selectedDate.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
			if (selectedDate.getTimeInMillis() >= mCurrentCalendar
					.getTimeInMillis()) {
				mTxtViewFromTime.setText(new StringBuilder().append(hourOfDay)
						.append("/").append(minute));
			} else {

				FragmentAlertDialog alert = new FragmentAlertDialog(
						FragmentAlertDialog.DIALOG_TYPE.OK_ONLY,
						ActivityReservationModification.this.getResources()
								.getString(R.string.invalid_start_time_title),
						ActivityReservationModification.this.getResources()
								.getString(R.string.invalid_start_time_msg));
				alert.show(ActivityReservationModification.this
						.getSupportFragmentManager(), "Alert_Dialog");
			}

		} else {
			mTxtViewToTime.setText(new StringBuilder().append(hourOfDay)
					.append("/").append(minute));
		}

	}

	@Override
	public void onDateSelect(int year, int month, int day) {
		if (isFromDate) {

			Calendar selectedDate = Calendar.getInstance();
			selectedDate.set(year, month, day);
			if (selectedDate.getTimeInMillis() >= mCurrentCalendar
					.getTimeInMillis()) {

				mSelectedFromYear = year;
				mSelectedFromMonth = month;
				mSelectedFromDay = day;
				mTxtViewFromDate.setText(new StringBuilder().append(day)
						.append("/").append(month + 1).append("/").append(year)
						.append(" "));
			} else {

				FragmentAlertDialog alert = new FragmentAlertDialog(
						FragmentAlertDialog.DIALOG_TYPE.OK_ONLY,
						ActivityReservationModification.this.getResources()
								.getString(R.string.invalid_start_time_title),
						ActivityReservationModification.this.getResources()
								.getString(R.string.invalid_start_time_msg));
				alert.show(this.getSupportFragmentManager(), "Alert_Dialog");
			}

		} else {
			mTxtViewToDate.setText(new StringBuilder().append(day).append("/")
					.append(month + 1).append("/").append(year).append(" "));

		}

	}

}
