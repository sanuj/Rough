package com.micar.fragment;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.TimePicker;

import com.micar.component.CustomTimePickerDialog;

public class FragmentTimePicker extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {

	private OnTimeSelectedListener onTimeSelectedListener;
	private int timesCalled = 1;

	public static final int TIME_PICKER_INTERVAL = 15;

	private final long ONE_HOUR_FIFTEN_IN_MILI_SECOND = 60 * 60 * 1000;

	private Calendar mCalendar;

	public interface OnTimeSelectedListener {
		public void onTimeSelect(int hourOfDay, int minute);
	}

	public FragmentTimePicker() {

	}

	public FragmentTimePicker(OnTimeSelectedListener OnTimeSelectedListener,
			Calendar calendar) {

		this.onTimeSelectedListener = OnTimeSelectedListener;
		this.mCalendar = calendar;

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = mCalendar.get(Calendar.MINUTE);

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			// only for HONEYCOMB and newer versions

			TimePickerDialog timePicker = new CustomTimePickerDialog(
					getActivity(), this, hour, minute, true,
					TIME_PICKER_INTERVAL);
			timePicker.setTitle("Set Time");

			return timePicker;
		} else {
			// return new TimePickerDialog(getActivity(), this, hour, minute,
			// true);

			TimePickerDialog timePicker = new GingerBreadTimePickerDialog(
					getActivity(), this, hour,
					minute/* getRoundedMinute(minute) */, true,
					TIME_PICKER_INTERVAL);
			timePicker.setTitle("Set Time");
			return timePicker;
		}

	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

		timesCalled += 1;

		if ((timesCalled % 2) == 0) {

			// if (android.os.Build.VERSION.SDK_INT <
			// android.os.Build.VERSION_CODES.HONEYCOMB) {
			// // only for HONEYCOMB and newer versions
			//
			// minute = getRoundedMinute(minute);
			// }

			onTimeSelectedListener.onTimeSelect(hourOfDay, minute);
		}

	}

	public class GingerBreadTimePickerDialog extends TimePickerDialog {

		private int timePickerInterval;
		private boolean mIgnoreEvent = false;
		private int mMinute, mPreviousMinute = -1;

		public GingerBreadTimePickerDialog(Context context,
				OnTimeSetListener callBack, int hourOfDay, int minute,
				boolean is24HourView, int timePickerInterval) {
			super(context, callBack, hourOfDay, minute, is24HourView);
			this.timePickerInterval = timePickerInterval;
			this.mMinute = minute;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.app.TimePickerDialog#onTimeChanged(android.widget.TimePicker,
		 * int, int) Implements Time Change Interval
		 */
		@Override
		public void onTimeChanged(TimePicker timePicker, int hourOfDay,
				int currentMinute) {
			super.onTimeChanged(timePicker, hourOfDay, currentMinute);
			this.setTitle("Set Time");
			if (!mIgnoreEvent) {
				// minute = getRoundedMinute(minute);

				// Log.e("mPreviousMinute currentMinute", mPreviousMinute + " "
				// + currentMinute);
				// Log.e("mMinute", mMinute + "");
				//
				// if (mPreviousMinute != -1 && mPreviousMinute > currentMinute)
				// {
				//
				// mPreviousMinute = currentMinute;
				// this.mMinute = getPreviousMinute();
				// } else {
				// mPreviousMinute = currentMinute;
				// this.mMinute = getNextMinute();
				// }
				this.mMinute = getNextMinute();
				mIgnoreEvent = true;
				timePicker.setCurrentMinute(mMinute);

				mIgnoreEvent = false;
			}

		}

		public int getPreviousMinute() {

			mMinute = mMinute - TIME_PICKER_INTERVAL;

			if (mMinute < 0) {
				mMinute = 60 - mMinute;
				mPreviousMinute = mMinute;

			}

			return mMinute;

		}

		public int getNextMinute() {

			mMinute = mMinute + TIME_PICKER_INTERVAL;

			if (mMinute >= 60) {
				mMinute = mMinute - 60;
				mPreviousMinute = mMinute;
			}

			return mMinute;

		}

		public int getRoundedMinute(int minute) {
			if (minute % TIME_PICKER_INTERVAL != 0) {
				int minuteFloor = minute - (minute % TIME_PICKER_INTERVAL);
				minute = minuteFloor
						+ (minute == minuteFloor + 1 ? TIME_PICKER_INTERVAL : 0);
				if (minute == 60)
					minute = 0;
			}

			return minute;
		}

	}

}
