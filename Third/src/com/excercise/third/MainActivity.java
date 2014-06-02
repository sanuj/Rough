package com.excercise.third;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends Activity {

	Map<String, ArrayList> mSpinner2Maps = new HashMap<String, ArrayList>();

	ArrayList<String> mSpinner1List = new ArrayList<String>();
	
	private Spinner mSpinnerFirst;
	private Spinner mSpinnerSecond;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		createData();

		mSpinnerFirst = (Spinner) findViewById(R.id.spinner_first);
		mSpinnerSecond = (Spinner) findViewById(R.id.spinner_sec);

		ArrayAdapter<String> firstAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mSpinner1List);
		firstAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinnerFirst.setAdapter(firstAdapter);

		mSpinnerFirst.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int pos,
					long id) {

				ArrayAdapter<String> secondAdapter = new ArrayAdapter<String>(
						MainActivity.this,
						android.R.layout.simple_spinner_item, mSpinner2Maps
								.get(parent.getItemAtPosition(pos)));
				secondAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mSpinnerSecond.setAdapter(secondAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void createData() {
		mSpinner1List.add("Fruits");
		mSpinner1List.add("Vegetables");
		mSpinner1List.add("Meat");
		ArrayList<String> list = new ArrayList<String>();
		list.add("orange");
		list.add("guava");
		list.add("apple");
		mSpinner2Maps.put("Fruits", list);
		list = new ArrayList<String>();
		list.add("spinash");
		list.add("carrots");
		list.add("potato");
		mSpinner2Maps.put("Vegetables", list);
		list = new ArrayList<String>();
		list.add("chicken");
		list.add("mutton");
		list.add("beef");
		mSpinner2Maps.put("Meat", list);
	}
}
