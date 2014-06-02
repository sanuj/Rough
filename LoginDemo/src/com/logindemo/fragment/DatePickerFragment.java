package com.logindemo.fragment;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	int mValidateYears;
	private DailogOnDateSet dailogOnDateSet;

	public interface DailogOnDateSet {
		public void onDateSetListener(int year, int monthOfYear, int dayOfMonth);
	}

	public DatePickerFragment() {

	}

	public DatePickerFragment(DailogOnDateSet dailogOnDateSet, int validateYears) {
		this.dailogOnDateSet=dailogOnDateSet;
		mValidateYears = validateYears;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR) - mValidateYears;
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		c.add(Calendar.YEAR, -mValidateYears);

		DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this,
				year, month, date);
		datePicker.getDatePicker().setMaxDate(c.getTimeInMillis());

		return datePicker;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		
		
		dailogOnDateSet.onDateSetListener(year, monthOfYear, dayOfMonth);

	}

}
