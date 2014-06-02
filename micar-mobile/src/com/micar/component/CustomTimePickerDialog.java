package com.micar.component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.NumberPicker;
import android.widget.TimePicker;

public class CustomTimePickerDialog extends TimePickerDialog {
	final OnTimeSetListener mCallback;
	TimePicker mTimePicker;
	final int increment;
	int minute, hourOfDay;
	List<String> displayedValues;

	public CustomTimePickerDialog(Context context, OnTimeSetListener callBack,
			int hourOfDay, int minute, boolean is24HourView, int increment) {
		super(context, callBack, hourOfDay, minute / increment, is24HourView);
		this.mCallback = callBack;
		this.increment = increment;
		this.minute = minute;
		this.hourOfDay = hourOfDay;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (mCallback != null && mTimePicker != null) {
			mTimePicker.clearFocus();

			int position = mTimePicker.getCurrentMinute();

			int minute = Integer.parseInt(displayedValues.get(position));

			Log.e("minute", mTimePicker.getCurrentMinute() + "");
			// mCallback.onTimeSet(mTimePicker, mTimePicker.getCurrentHour(),
			// mTimePicker.getCurrentMinute() * increment);

			mCallback.onTimeSet(mTimePicker, mTimePicker.getCurrentHour(),
					minute);
		}
	}

	@Override
	protected void onStop() {
		// override and do nothing
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			Class<?> rClass = Class.forName("com.android.internal.R$id");
			Field timePicker = rClass.getField("timePicker");
			this.mTimePicker = (TimePicker) findViewById(timePicker
					.getInt(null));
			Field m = rClass.getField("minute");

			NumberPicker mMinuteSpinner = (NumberPicker) mTimePicker
					.findViewById(m.getInt(null));
			mMinuteSpinner.setMinValue(0);
			mMinuteSpinner.setMaxValue((60 / increment) - 1);
			displayedValues = new ArrayList<String>();

			for (int i = 0; i < 4; i++) {

				displayedValues.add(String.format("%02d", minute));
				minute = minute + increment;

				if (minute >= 60) {
					minute = minute - 60;
				}

			}
			// for (int i = 0; i < 60; i += increment) {
			// displayedValues.add(String.format("%02d", i));
			// }
			mMinuteSpinner.setDisplayedValues(displayedValues
					.toArray(new String[4]));
			mMinuteSpinner.setValue(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		super.onTimeChanged(view, hourOfDay, minute);
		this.setTitle("Set Time");
	}
}
