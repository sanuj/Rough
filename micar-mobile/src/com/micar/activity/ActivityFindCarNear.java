package com.micar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.micar.adapter.AddressAdapter;
import com.micar.adapter.PlacesAutoCompleteAdapter;
import com.micar.adapter.StationAdapter;
import com.micar.asynctask.GetAddressTask;
import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.fragment.FragmentErrorDialog;
import com.micar.model.LoginInfo;
import com.micar.model.Model;
import com.micar.model.StationInfo;
import com.micar.parser.CityStationsParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.LocationUtils;
import com.micar.utils.Utility;

public class ActivityFindCarNear extends FragmentActivity implements
		LocationListener, OnClickListener,
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, IAsyncCaller {

	private Button mBtnAddress;
	private AutoCompleteTextView mAutoCompleteTextView;
	private ListView mListViewStation, mListViewFavAddress;
	private LinearLayout mLinearLytAddress;

	// A request to connect to Location Services
	private LocationRequest mLocationRequest;

	// Stores the current instantiation of the location client in this object
	private LocationClient mLocationClient;

	/*
	 * Note if updates have been turned on. Starts out as "false"; is set to
	 * "true" in the method handleRequestSuccess of LocationUpdateReceiver.
	 */
	boolean mUpdatesRequested = false;

	private String mCity;
	private TextView mTextViewSelectedCity;
	private String fromActivity;
	private LoginInfo loginInfo;

	private List<StationInfo> stationList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_car_near);
		fromActivity = getIntent().getStringExtra("from");

		getViewsID();

		initializeLocation();

		if (fromActivity != null
				&& fromActivity.equalsIgnoreCase("ActivityReserveCar")) {

			mLinearLytAddress.setVisibility(View.GONE);

		} else {

			loginInfo = ActivityMicarHome.getLoginInfo();
			mLinearLytAddress.setVisibility(View.VISIBLE);
			String city = getIntent().getStringExtra("city");
			String cityId = getIntent().getStringExtra("cityId");

			fetchCityStation(city, cityId);

			setFavoriteAddress();
			// setSations();
		}

		mAutoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1,
					int position, long arg3) {

				String address = (String) adapterView
						.getItemAtPosition(position);
				Toast.makeText(ActivityFindCarNear.this, address,
						Toast.LENGTH_SHORT).show();

				setAddressResponse(address);

			}
		});

	}

	private void fetchCityStation(String city, String cityId) {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accessToken", Utility.getSharedPrefStringData(this,
				Constants.KEY_ACCESS_TOKEN));
		paramMap.put("cityId", cityId);

		CityStationsParser cityStationsParser = new CityStationsParser();
		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				this, getResources().getString(R.string.please_wait), true,
				ApiDetails.HOME_URL + ApiDetails.FETCH_CITY_STATIONS, paramMap,
				ApiDetails.REQUEST_TYPE.POST, this, cityStationsParser);

		Utility.execute(serverIntractorAsync);

	}

	private void setFavoriteAddress() {
		if (loginInfo != null && loginInfo.getAddressList() != null
				&& loginInfo.getAddressList().size() > 0) {
			AddressAdapter favStationAdapter = new AddressAdapter(this,
					loginInfo.getAddressList());
			mListViewFavAddress.setAdapter(favStationAdapter);
		}

	}

	private void setSations() {

		StationAdapter myStationAdapter = new StationAdapter(this, stationList);
		mListViewStation.setAdapter(myStationAdapter);

	}

	private void initializeLocation() {
		// Create a new global location parameters object
		mLocationRequest = LocationRequest.create();

		/*
		 * Set the update interval
		 */
		mLocationRequest
				.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);

		// Use high accuracy
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

		// Set the interval ceiling to one minute
		mLocationRequest
				.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

		// Note that location updates are off until the user turns them on
		mUpdatesRequested = false;

		/*
		 * Create a new location client, using the enclosing class to handle
		 * callbacks.
		 */
		mLocationClient = new LocationClient(this, this, this);

	}

	private void getViewsID() {
		mBtnAddress = (Button) findViewById(R.id.btn_address);
		mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.auto_complete_txtview);
		mAutoCompleteTextView.setAdapter(new PlacesAutoCompleteAdapter(this,
				R.layout.auto_complete_row));
		mListViewStation = (ListView) findViewById(R.id.listview_station);
		mListViewFavAddress = (ListView) findViewById(R.id.listview_fav_address);
		mTextViewSelectedCity = (TextView) findViewById(R.id.txtView_selected_city);
		mLinearLytAddress = (LinearLayout) findViewById(R.id.linear_fav_station);

		mCity = getIntent().getStringExtra("city");
		if (mCity != null) {
			mTextViewSelectedCity.setText(mCity);
		}
		mBtnAddress.setOnClickListener(this);

	}

	/*
	 * Called when the Activity is no longer visible at all. Stop updates and
	 * disconnect.
	 */
	@Override
	public void onStop() {

		// If the client is connected
		if (mLocationClient.isConnected()) {
			stopPeriodicUpdates();
		}

		// After disconnect() is called, the client is considered "dead".
		mLocationClient.disconnect();

		super.onStop();
	}

	/*
	 * Called when the Activity is going into the background. Parts of the UI
	 * may be visible, but the Activity is inactive.
	 */
	@Override
	public void onPause() {

		super.onPause();
	}

	/*
	 * Called when the Activity is restarted, even before it becomes visible.
	 */
	@Override
	public void onStart() {

		super.onStart();

		/*
		 * Connect the client. Don't re-start any requests here; instead, wait
		 * for onResume()
		 */
		mLocationClient.connect();

	}

	/*
	 * Called when the system detects that this Activity is now visible.
	 */
	@Override
	public void onResume() {
		super.onResume();

	}

	/*
	 * Handle results returned to this Activity by other Activities started with
	 * startActivityForResult(). In particular, the method onConnectionFailed()
	 * in LocationUpdateRemover and LocationUpdateRequester may call
	 * startResolutionForResult() to start an Activity that handles Google Play
	 * services problems. The result of this call returns here, to
	 * onActivityResult.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		// Choose what to do based on the request code
		switch (requestCode) {

		// If the request code matches the code sent in onConnectionFailed
		case LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST:

			switch (resultCode) {
			// If Google Play services resolved the problem
			case Activity.RESULT_OK:

				break;

			// If any other result was returned by Google Play services
			default:

				break;
			}

			// If any other request code was received
		default:

			break;
		}
	}

	/**
	 * Verify that Google Play services is available before making a request.
	 * 
	 * @return true if Google Play services is available, otherwise false
	 */
	private boolean servicesConnected() {

		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d(LocationUtils.APPTAG, "play_services_available");

			// Continue
			return true;
			// Google Play services was not available for some reason
		} else {
			// Display an error dialog
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,
					this, 0);
			if (dialog != null) {
				FragmentErrorDialog errorFragment = new FragmentErrorDialog();
				errorFragment.setDialog(dialog);
				errorFragment.show(getSupportFragmentManager(),
						LocationUtils.APPTAG);
			}
			return false;
		}
	}

	/**
	 * Invoked by the "Get Location" button.
	 * 
	 * Calls getLastLocation() to get the current location
	 * 
	 * @param v
	 *            The view object associated with this method, in this case a
	 *            Button.
	 */
	public void getLocation() {

		// If Google Play Services is available
		if (servicesConnected()) {

			if(mLocationClient!=null)
			{
			// Get the current location
			Location currentLocation = mLocationClient.getLastLocation();
			mBtnAddress.setText(mLocationClient.getLastLocation().getLatitude()
					+ " " + mLocationClient.getLastLocation().getLongitude());

			Toast.makeText(this,
					LocationUtils.getLatLng(this, currentLocation),
					Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * Invoked by the "Get Address" button. Get the address of the current
	 * location, using reverse geocoding. This only works if a geocoding service
	 * is available.
	 */
	// For Eclipse with ADT, suppress warnings about Geocoder.isPresent()
	@SuppressLint("NewApi")
	public void getAddress() {

		// In Gingerbread and later, use Geocoder.isPresent() to see if a
		// geocoder is available.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
				&& !Geocoder.isPresent()) {
			// No geocoder is present. Issue an error message
			Toast.makeText(this, "no_geocoder_available", Toast.LENGTH_LONG)
					.show();
			return;
		}

		if (servicesConnected()) {

			// Get the current location
			Location currentLocation = mLocationClient.getLastLocation();

			// Start the background task
			(new GetAddressTask(this)).execute(currentLocation);
		}
	}

	/**
	 * Invoked by the "Start Updates" button Sends a request to start location
	 * updates
	 */
	public void startUpdates() {
		mUpdatesRequested = true;

		if (servicesConnected()) {
			startPeriodicUpdates();
		}
	}

	/**
	 * Invoked by the "Stop Updates" button Sends a request to remove location
	 * updates request them.
	 * 
	 * @param v
	 *            The view object associated with this method, in this case a
	 *            Button.
	 */
	public void stopUpdates(View v) {
		mUpdatesRequested = false;

		if (servicesConnected()) {
			stopPeriodicUpdates();
		}
	}

	/*
	 * Called by Location Services when the request to connect the client
	 * finishes successfully. At this point, you can request the current
	 * location or start periodic updates
	 */
	@Override
	public void onConnected(Bundle bundle) {

		if (mUpdatesRequested) {
			startPeriodicUpdates();
		}
	}

	/*
	 * Called by Location Services if the connection to the location client
	 * drops because of an error.
	 */
	@Override
	public void onDisconnected() {

	}

	/*
	 * Called by Location Services if the attempt to Location Services fails.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution()) {
			try {

				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this,
						LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */

			} catch (IntentSender.SendIntentException e) {

				// Log the error
				e.printStackTrace();
			}
		} else {

			// If no resolution is available, display a dialog to the user with
			// the error.
			showErrorDialog(connectionResult.getErrorCode());
		}
	}

	/**
	 * Report location updates to the UI.
	 * 
	 * @param location
	 *            The updated location.
	 */
	public void onLocationChanged(Location location) {

		// In the UI, set the latitude and longitude to the value received
		// mLatLng.setText(LocationUtils.getLatLng(this, location));

		Log.e("lat long", LocationUtils.getLatLng(this, location));
	}

	/**
	 * In response to a request to start updates, send a request to Location
	 * Services
	 */
	private void startPeriodicUpdates() {

		mLocationClient.requestLocationUpdates(mLocationRequest,
				(LocationListener) ActivityFindCarNear.this);

	}

	/**
	 * In response to a request to stop updates, send a request to Location
	 * Services
	 */
	private void stopPeriodicUpdates() {
		mLocationClient.removeLocationUpdates(this);

	}

	/**
	 * Show a dialog returned by Google Play services for the connection error
	 * code
	 * 
	 * @param errorCode
	 *            An error code returned from onConnectionFailed
	 */
	private void showErrorDialog(int errorCode) {

		// Get the error dialog from Google Play services
		Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode,
				this, LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

		// If Google Play services can provide an error dialog
		if (errorDialog != null) {

			// Create a new DialogFragment in which to show the error dialog
			FragmentErrorDialog errorFragment = new FragmentErrorDialog();

			// Set the dialog in the DialogFragment
			errorFragment.setDialog(errorDialog);

			// Show the error dialog in the DialogFragment
			errorFragment.show(getSupportFragmentManager(),
					LocationUtils.APPTAG);
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_address:

			getLocation();
			getAddress();

			break;

		default:
			break;
		}

	}

	public void setAddressResponse(String address) {
		Intent intent = new Intent();
		intent.putExtra("address", address);
		setResult(RESULT_OK, intent);
		finish();

	}

	@Override
	public void onComplete(Model object) {

		if (object instanceof StationInfo) {
			StationInfo stationInfo = (StationInfo) object;
			if (stationInfo.getStationList() != null
					&& stationInfo.getStationList().size() > 0) {

				stationList = stationInfo.getStationList();
				setSations();

			}
		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {

	}

}