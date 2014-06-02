package com.micar.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.component.DateTimePicker;
import com.micar.component.DateTimePicker.ICustomDateTimeListener;
import com.micar.model.Model;
import com.micar.model.StationInfo;
import com.micar.network.GeoCodeAsyncTask;
import com.micar.parser.CityStationsParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;

public class ActivityStationsInMap extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, OnClickListener,
		ICustomDateTimeListener, IAsyncCaller {

	private GeoCodeAsyncTask geoCodeAsyncTask;

	private final int RQS_GooglePlayServices = 1;
	private GoogleMap mMap;
	private LocationManager locationManager;
	private static final long MIN_TIME = 400;
	private static final float MIN_DISTANCE = 1000;
	public TextView textViewAddress;

	private Location mCurrentLocation;

	private LocationClient mLocationClient;
	private MyLocationListener myLocationListeners;
	private LocationRequest mLocationRequest;
	private static final float MAX_ZOOM = 14.0f;

	// List<StationInfo> stationList;

	public static ActivityStationsInMap activityHome;
	private Context mContext;
	private Button mButtonList;

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {

		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		/* destroy all the async task if activity destroy */
		if (geoCodeAsyncTask != null
				&& geoCodeAsyncTask.getStatus() == Status.RUNNING) {
			geoCodeAsyncTask.cancel(true);
			geoCodeAsyncTask = null;
		}

		// if (myStationAsync != null
		// && myStationAsync.getStatus() == Status.RUNNING) {
		// myStationAsync.cancel(true);
		// myStationAsync = null;
		// }
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_stations_in_map);

		activityHome = this;
		mContext = this;

		getViewsID();

		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		try {
			mMap.setMyLocationEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		MyLocationListener myLocationListener = new MyLocationListener();

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE,
				myLocationListener);
		centerMapOnMyLocation();
		fetchStation();

	}

	/**
	 * method to get user micar station, parking and preffered addresses from
	 * server.
	 */
	private void fetchStation() {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accessToken", Utility.getSharedPrefStringData(this,
				Constants.KEY_ACCESS_TOKEN));
		paramMap.put("cityId", "");

		CityStationsParser cityStationsParser = new CityStationsParser();
		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				this, getResources().getString(R.string.please_wait), true,
				ApiDetails.HOME_URL + ApiDetails.FETCH_CITY_STATIONS, paramMap,
				ApiDetails.REQUEST_TYPE.POST, this, cityStationsParser);

		Utility.execute(serverIntractorAsync);

	}

	private void getViewsID() {

		textViewAddress = (TextView) findViewById(R.id.home_map_location_textview);
		textViewAddress.setSelected(true);
		textViewAddress.setOnClickListener(this);
		mButtonList = (Button) findViewById(R.id.btn_list);
		mButtonList.setOnClickListener(this);

		// ((TextView) findViewById(R.id.txtview_profile))
		// .setOnClickListener(this);
		// ((TextView) findViewById(R.id.txtview_setting))
		// .setOnClickListener(this);
		/*
		 * ((Button) findViewById(R.id.button_reservation))
		 * .setOnClickListener(this);
		 */

	}

	@Override
	protected void onResume() {
		super.onResume();

		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());
		if (!Utility.CheckEnableGPS(this)) {

			Utility.showSettingDialog(this,
					getResources().getString(R.string.no_location_msg),
					getResources().getString(R.string.no_location_title),
					Utility.NO_GPS_ACCESS).show();

		}

		if (resultCode != ConnectionResult.SUCCESS) {

			GooglePlayServicesUtil.getErrorDialog(resultCode, this,
					RQS_GooglePlayServices);
		} else {
			setMap();
		}

	}

	private void setMap() {

		// Check if we were successful in obtaining the map.
		if (mMap != null) {
			mMap.setOnCameraChangeListener(new OnCameraChangeListener() {
				@Override
				public void onCameraChange(CameraPosition position) {
					{
						if (position != null) {

							/**
							 * when user change the location in the map geocode
							 * async call to get current address of user.
							 */

							textViewAddress.setText(ActivityStationsInMap.this
									.getResources().getString(
											R.string.getting_address));

							Log.e("latitude, logitude",
									position.target.latitude + " "
											+ position.target.longitude);

							if (geoCodeAsyncTask != null
									&& geoCodeAsyncTask.getStatus() == Status.RUNNING) {
								geoCodeAsyncTask.cancel(true);
							}

							geoCodeAsyncTask = new GeoCodeAsyncTask(
									position.target.latitude,
									position.target.longitude,
									ActivityStationsInMap.this);
							geoCodeAsyncTask.execute();
						}
						{
						}
					}

				}
			});
			try {
				mMap.getUiSettings().setZoomControlsEnabled(true);
				// CameraPosition position = mMap.getCameraPosition();
				// mLatitudePosition = position.target.latitude;
				// mLongitudePosition = position.target.longitude;
			} catch (Exception e) {
				e.printStackTrace();
			}

			// centerMapOnMyLocation();
		}
	}

	private void centerMapOnMyLocation() {

		Location location = mMap.getMyLocation();

		if (location != null) {
			LatLng myLocation = new LatLng(location.getLatitude(),
					location.getLongitude());
			// mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
			// MAX_ZOOM));
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
					MAX_ZOOM));

		}

	}

	/**
	 * to add user miStation,miParking markers in map
	 * 
	 * @param markerDrawable
	 * @param stationList
	 */
	private void addMarkers(int markerDrawable, List<StationInfo> stationList) {

		for (int i = 0; i < stationList.size(); i++) {

			try {

				Double lat = Double.valueOf(stationList.get(i).getLatitude());
				Double lon = Double.valueOf(stationList.get(i).getLongitude());
				LatLng latlong = new LatLng(lat, lon);
				mMap.addMarker(new MarkerOptions().position(latlong)

				/*
				 * .title(stationList.get(i).getName()) .snippet( "Latitude:" +
				 * stationList.get(i).getLatitude() + " Longitude:" +
				 * stationList.get(i).getLongitude())
				 */

				.icon(BitmapDescriptorFactory.fromResource(markerDrawable)));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public class MyLocationListener implements LocationListener,
			com.google.android.gms.location.LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			LatLng latLng = new LatLng(location.getLatitude(),
					location.getLongitude());
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
					latLng, MAX_ZOOM);
			// mMap.animateCamera(cameraUpdate);
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAX_ZOOM));
			Log.e("onLocationChanged", "onLocationChanged");
			locationManager.removeUpdates(this);

		}

		@Override
		public void onProviderDisabled(String provider) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

	}

	public void updteCurrentAddress(String addressComponents) {

		textViewAddress.setText(addressComponents);
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {

	}

	@Override
	public void onConnected(Bundle arg0) {
		mCurrentLocation = mLocationClient.getLastLocation();
		mLocationClient.requestLocationUpdates(mLocationRequest,
				myLocationListeners);
		if (mCurrentLocation != null) {
			// mLatitudePosition = mCurrentLocation.getLatitude();
			// mLongitudePosition = mCurrentLocation.getLongitude();
			// mSourceLatitude = mLatitudePosition;
			// mSourceLongitude = mLongitudePosition;
		}

	}

	@Override
	public void onDisconnected() {

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.btn_list:
			finish();
			break;
		/*
		 * case R.id.txtview_profile:
		 * 
		 * Intent intentProfile = new Intent(ActivityHome.this,
		 * ActivityMyProfile.class); startActivity(intentProfile);
		 * 
		 * break; case R.id.txtview_setting:
		 * 
		 * Intent intentSetting = new Intent(ActivityHome.this,
		 * ActivitySetting.class); startActivity(intentSetting); break;
		 */

		/*
		 * case R.id.button_reservation:
		 * 
		 * showDateTimeDialog(); break;
		 */
		case R.id.home_map_location_textview:

			/**
			 * if user tap on the location address view check first if geo
			 * coding task is done or not then launch Address activity to save
			 * user address
			 */

			/*
			 * if (!textViewAddress .getText() .toString() .equalsIgnoreCase(
			 * ActivityStationsInMap.this.getResources().getString(
			 * R.string.getting_address)) || textViewAddress .getText()
			 * .toString() .equalsIgnoreCase(
			 * ActivityStationsInMap.this.getResources().getString(
			 * R.string.address_not_found))) {
			 * 
			 * Intent intentAddress = new Intent(ActivityStationsInMap.this,
			 * ActivityAddress.class); startActivity(intentAddress);
			 * 
			 * }
			 */
			break;

		default:
			break;
		}

	}

	private void showDateTimeDialog() {

		DateTimePicker dateTimePicker = new DateTimePicker(
				ActivityStationsInMap.this, this);
		dateTimePicker.set24HourFormat(false);
		dateTimePicker.showDialog();

	}

	/**
	 * callback method of date/time picker
	 */
	@Override
	public void onSet(Calendar calendarSelected, Date dateSelected, int year,
			String monthFullName, String monthShortName, int monthNumber,
			int date, String weekDayFullName, String weekDayShortName,
			int hour24, int hour12, int min, int sec, String AM_PM) {
		// textView.setText(dateSelected.toLocaleString());
	}

	@Override
	public void onCancel() {
		Log.d("datetimepickerdialog", "canceled");
	}

	@Override
	public void onComplete(Model object) {
		if (object instanceof StationInfo) {
			StationInfo stationInfo = (StationInfo) object;
			if (stationInfo.getStationList() != null
					&& stationInfo.getStationList().size() > 0) {

				List<StationInfo> stationList = stationInfo.getStationList();
				addMarkers(R.drawable.micar_buble, stationList);

			}
		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {

	}

}
