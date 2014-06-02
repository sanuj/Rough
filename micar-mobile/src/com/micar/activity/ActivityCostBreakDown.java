package com.micar.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.micar.adapter.EstimatedCostAdapter;

public class ActivityCostBreakDown extends Activity {

	private ListView mListViewCar;
	private List<String> mListCost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cost_break_down);
		getViewsID();

		mListCost = new ArrayList<String>();

		for (int i = 0; i < 10; i++) {

			mListCost.add("abc");

		}

		EstimatedCostAdapter estimatedCostAdapter = new EstimatedCostAdapter(
				this, mListCost);
		mListViewCar.setAdapter(estimatedCostAdapter);

	}

	private void getViewsID() {

		mListViewCar = (ListView) findViewById(R.id.listview_cost);

	}

}
