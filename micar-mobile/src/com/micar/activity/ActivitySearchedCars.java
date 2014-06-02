package com.micar.activity;

import java.util.ArrayList;

import com.micar.adapter.SearchedCarsAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ActivitySearchedCars extends Activity implements OnClickListener {

	private Button mBtnList, mBtnMap;
	private ListView mListViewCars;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searched_cars);
		getViewsId();
		
		mListViewCars.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
			
				
				selectItem(position);
				
			}

			
		});
	}
	
	private void selectItem(int position) {
		
		Intent intent=new Intent(ActivitySearchedCars.this,ActivityReserveCar.class);
		startActivity(intent);
		
	}

	private void getViewsId() {
		mBtnList = (Button) findViewById(R.id.btn_list);
		mBtnMap = (Button) findViewById(R.id.btn_map);
		mBtnMap.setOnClickListener(this);
		mListViewCars = (ListView) findViewById(R.id.listview_car);

		ArrayList<String> invitedList = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {

			invitedList.add("abc");

		}
		SearchedCarsAdapter searchedCarsAdapter = new SearchedCarsAdapter(this,
				invitedList);

		mListViewCars.setAdapter(searchedCarsAdapter);

	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_map:
			
			Intent intent=new Intent(this, ActivityStationsInMap.class);
			startActivity(intent);
			
			break;

		default:
			break;
		}
		
		
	}

}
