package com.micar.network;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.micar.activity.ActivityStationsInMap;
import com.micar.activity.R;
import com.micar.utils.Constants;

public class GeoCodeAsyncTask extends AsyncTask<Void, Void, String> {
	double cameraLatitude = 0.0, cameraLongitude = 0.0;
	String addressString = null;
	private Context context;
	private boolean isRunning = true;
	private HttpURLConnection conn = null;

	public GeoCodeAsyncTask(double latitude, double longitude, Context context) {
		cameraLatitude = latitude;
		cameraLongitude = longitude;
		this.context = context;
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(Void... params) {

		/*while (isRunning) {*/
			addressString = gettingAddressFromDraggingMarker(cameraLatitude,
					cameraLongitude);

			return addressString;

		/*}
		if (conn != null) {
			conn.disconnect();

		}
		return null;*/
	}

	@Override
	protected void onPostExecute(String result) {

		super.onPostExecute(result);

		if (!this.isCancelled() && addressString != null) {
			try {
				JSONObject reverseGeoResp = new JSONObject(result);
				if (reverseGeoResp.getString("status").equals("OK")) {
					JSONArray addressArray = reverseGeoResp
							.getJSONArray("results");
					JSONObject address = addressArray.getJSONObject(0);
					String addressComponents = address
							.getString("formatted_address");
					if (addressComponents.length() > 0) {
						// if (!mIsDropOffLayoutVisible) // update source
						// location only if destination is not set

						((ActivityStationsInMap) context)
								.updteCurrentAddress(addressComponents);

					} else {
						((ActivityStationsInMap) context).updteCurrentAddress(context
								.getResources().getString(
										R.string.action_settings));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// Method for finding the address from the latitude and longitude....
	private String gettingAddressFromDraggingMarker(double lat, double longitude) {
		// mSourceLatitude = lat;
		// mSourceLongitude = longitude;

		StringBuilder jsonResults = new StringBuilder();
		try {
			StringBuilder sb = new StringBuilder(
					Constants.GOOGLE_GEOCODER_SERVICE + lat/* mSourceLatitude */
							+ "," + longitude/* mSourceLongitude */
							+ "&sensor=true");
			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(Constants.CONNECTION_TIME_OUT);

			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			// Load the results into a StringBuilder
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.e("jsonResults.toString()", jsonResults.toString());
		return jsonResults.toString();
	}
}
