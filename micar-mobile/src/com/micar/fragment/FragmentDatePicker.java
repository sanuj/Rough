package com.micar.fragment;

import java.util.Calendar;

import com.google.android.gms.common.data.Freezable;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.DatePicker;

public class FragmentDatePicker extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	private ONDateSelectedListerner onDateValueSelected;

	private int timesCalled = 1;

	public interface ONDateSelectedListerner {
		public void onDateSelect(int year, int month, int day);
	}

	public FragmentDatePicker() {

	}

	public FragmentDatePicker(ONDateSelectedListerner onDateValueSelected) {

		this.onDateValueSelected = onDateValueSelected;

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {

		timesCalled += 1;

		if ((timesCalled % 2) == 0) {
			onDateValueSelected.onDateSelect(year, month, day);
		}

	}

}