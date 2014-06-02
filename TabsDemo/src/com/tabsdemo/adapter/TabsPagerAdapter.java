package com.tabsdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tabsdemo.activity.Constants;
import com.tabsdemo.fragment.GamesFragment;
import com.tabsdemo.fragment.MoviesFragment;
import com.tabsdemo.fragment.TopRatedFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new TopRatedFragment();
		case 1:
			return new GamesFragment();
		case 2:
			return new MoviesFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		return Constants.NUM_TABS;
	}

}
