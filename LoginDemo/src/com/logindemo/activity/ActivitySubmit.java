package com.logindemo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.logindemo.R;

public class ActivitySubmit extends Activity implements OnScrollListener {

	private ListView mViewList;
	List<Map> mData = new ArrayList<Map>();
	SimpleAdapter mAdapter;
	int mIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit);

		GetSampleData(mData);
		mViewList = (ListView) findViewById(R.id.list);
	

		mAdapter = new SimpleAdapter(
				this,
				(List<? extends Map<String, ?>>) mData,
				R.layout.item,
				new String[] { "image_icon", "tview_name", "tview_email" },
				new int[] { R.id.image_icon, R.id.txt_view_name, R.id.txt_view_email });

		mViewList.setAdapter(mAdapter);


		mViewList.setOnScrollListener(this);
		
	}

	void GetSampleData(List<Map> list) {
		
		for (int i=0; i<20; i++) {
			Map map = new HashMap();
			map.put("image_icon", R.drawable.icon);
			map.put("tview_name", "Sherlock Holmes");
			map.put("tview_email", mIndex++);
			list.add(map);	
		}
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		int lastInScreen = firstVisibleItem + visibleItemCount;
		if((lastInScreen == totalItemCount)){
			GetSampleData(mData);
			mAdapter.notifyDataSetChanged();
		}
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
}
