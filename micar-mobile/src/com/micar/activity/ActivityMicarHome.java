package com.micar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.micar.adapter.DrawerMenuAdapter;
import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.fragment.FragmentActiveReservation;
import com.micar.fragment.FragmentMakeBooking;
import com.micar.fragment.FragmentMyAccount;
import com.micar.fragment.FragmentUpcomingReservation;
import com.micar.model.LoginInfo;
import com.micar.model.MenuInfo;
import com.micar.model.MessageInfo;
import com.micar.model.Model;
import com.micar.parser.MessageParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;

public class ActivityMicarHome extends ActionBarActivity implements
		IAsyncCaller {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] menuNameArray;
	private int[] menuIconArray = { R.drawable.profile_image_icon,
			R.drawable.car_drawable, R.drawable.booking_active_drawable,
			R.drawable.mi_points_booking_drawable,

			R.drawable.upcoming_reservation_drawable,
			R.drawable.my_account_drawable, R.drawable.sign_out_drawable };

	private static LoginInfo loginInfo;

	// private Map<Fragment> fragments = new Vector<Fragment>();

	private Map<String, Fragment> fragmentsMap = new HashMap<String, Fragment>();

	public static LoginInfo getLoginInfo() {

		return loginInfo;

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_micar_home);

		loginInfo = (LoginInfo) getIntent().getParcelableExtra("loginInfo");

		mTitle = mDrawerTitle = getTitle();
		menuNameArray = getResources().getStringArray(R.array.menu_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		ArrayList<MenuInfo> menuList = new ArrayList<MenuInfo>();

		for (int i = 0; i < menuNameArray.length; i++) {

			MenuInfo menu = new MenuInfo();
			menu.setIcon(menuIconArray[i]);
			menu.setMenuItemName(menuNameArray[i]);
			if (i == 0) {

				LoginInfo loginInfo = (LoginInfo) getIntent()
						.getParcelableExtra("loginInfo");
				if (loginInfo != null) {
					menu.setMemberType(loginInfo.getMemberType());
					menu.setMenuItemName(loginInfo.getFirstName() + " "
							+ loginInfo.getLastName());
					menu.setUserProfile(true);

				}

			} else {
				menu.setUserProfile(false);
			}

			menuList.add(menu);

		}

		DrawerMenuAdapter adapter = new DrawerMenuAdapter(this, menuList);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
//		getSupportActionBar().setIcon(R.drawable.action_bar_app_icon);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				supportInvalidateOptionsMenu(); // creates call to
												// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				supportInvalidateOptionsMenu(); // creates call to
												// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			
			if(Utility.getSharedPrefBooleanData(this, Constants.KEY_HAS_ACTIVE_RESERVATION))
			{
				selectItem(2);
			}
			else
			{
				selectItem(1);	
			}
			

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call supportInvalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	public void selectItem(int position) {

		Fragment fragment = null;
		switch (position) {
		case 0:

			break;
		case 1:
			// fragment = new FragmentMakeBooking();
			fragment = fragmentsMap.get("makeBooking");

			if (fragment == null) {

				fragment = Fragment.instantiate(this,
						FragmentMakeBooking.class.getName());
				fragmentsMap.put("makeBooking", fragment);
			}

			break;
		case 2:
			fragment = fragmentsMap.get("activeReservation");
			if (fragment == null) {

				fragment = Fragment.instantiate(this,
						FragmentActiveReservation.class.getName());
				fragmentsMap.put("activeReservation", fragment);

			}

			break;
		case 3:

			break;
		case 4:
			// fragment = new FragmentUpcomingReservation();

//			fragment = fragmentsMap.get("upcomingReservation");
//			if (fragment == null) {

				fragment = Fragment.instantiate(this,
						FragmentUpcomingReservation.class.getName());
				fragmentsMap.put("upcomingReservation", fragment);

//			}

			break;
		case 5:
			// fragment = new FragmentMyAccount();
			fragment = fragmentsMap.get("myAccount");

			if (fragment == null) {
				fragment = Fragment.instantiate(this,
						FragmentMyAccount.class.getName());
				fragmentsMap.put("myAccount", fragment);
			}

			break;

		case 6:
			logout();

			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(menuNameArray[position]);

		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	private void logout() {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("memberId", String.valueOf(Utility
				.getSharedPrefStringData(this, Constants.KEY_MEMBER_ID)));
		paramMap.put("accessToken", Utility.getSharedPrefStringData(this,
				Constants.KEY_ACCESS_TOKEN));

		MessageParser msgParser = new MessageParser();

		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				this, getResources().getString(R.string.please_wait), true,
				ApiDetails.HOME_URL + ApiDetails.LOG_OUT, paramMap,
				ApiDetails.REQUEST_TYPE.POST, this, msgParser);

		Utility.execute(serverIntractorAsync);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onComplete(Model object) {

		if (object instanceof MessageInfo) {
			MessageInfo msgInfo = (MessageInfo) object;
			setLogoutResponse(msgInfo);
		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {
		// TODO Auto-generated method stub

	}

	private void setLogoutResponse(MessageInfo msgInfo) {

		if (msgInfo.getStatus() == 1) {

			Utility.setSharedPrefBooleanData(this, Constants.KEY_REMEMBER_ME,
					false);
			Utility.setSharedPreStringData(this, Constants.KEY_ACCESS_TOKEN, "");

			Intent intent = new Intent(this, ActivityLogin.class);
			startActivity(intent);
			finish();

		} else {
			Utility.showOKOnlyDialog(this,
					getResources().getString(R.string.error_msg),
					getResources().getString(R.string.error_title)).show();

		}

	}

}