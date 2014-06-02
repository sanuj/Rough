package com.tabsdemo.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.tabsdemo.R;
import com.tabsdemo.adapter.TabsPagerAdapter;

public class ActivityTabs extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager mViewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabs);

		mViewPager = (ViewPager) findViewById(R.id.pager_view);
		mActionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		mViewPager.setAdapter(mAdapter);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mActionBar.addTab(mActionBar.newTab().setText("Top Rated")
				.setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText("Games")
				.setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText("Movies")
				.setTabListener(this));

		// Swiping View-pager Listener
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				mActionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	@Override
	public void onTabReselected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabSelected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
	}

}
