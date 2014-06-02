package com.micar.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.micar.utils.ApiDetails;

import android.util.Log;

public class ApiCaller {

	private JSONObject getKeysJson() {

		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("appKey", ApiDetails.APP_KEY);
			jsonObject.put("version", ApiDetails.APP_VERSION);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObject;

	}

	public String loginAPI(String userEmail, String password) {

		
		 /* List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		  pairs.add(new BasicNameValuePair("email", userEmail)); pairs.add(new
		  BasicNameValuePair("appKey", ApiDetails.APP_KEY)); pairs.add(new
		  BasicNameValuePair("password", password)); pairs.add(new
		  BasicNameValuePair("version", ApiDetails.APP_VERSION)); String
		  serverResponse = ServerInteractor.getUrlData(pairs,
		  ApiDetails.HOME_URL + ApiDetails.LOGIN);*/
		 

		JSONObject jsonObject = getKeysJson();

		try {
			jsonObject.put("email", userEmail);

			jsonObject.put("password", password);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		String serverResponse = ServerInteractor.sendJson(ApiDetails.HOME_URL
				+ ApiDetails.LOGIN, jsonObject.toString());

		Log.i("post data",
				ApiDetails.HOME_URL + ApiDetails.LOGIN + jsonObject.toString());
		Log.i("client server", "Server response - " + serverResponse);

		return serverResponse;

	}

	public String forgotPswdAPI(String userEmail) {

		/*
		 * List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 * pairs.add(new BasicNameValuePair("email", userEmail)); pairs.add(new
		 * BasicNameValuePair("appKey", ApiDetails.APP_KEY)); pairs.add(new
		 * BasicNameValuePair("version", ApiDetails.APP_VERSION));
		 * 
		 * String serverResponse = ServerInteractor.getUrlData(pairs,
		 * ApiDetails.HOME_URL + ApiDetails.FORGOT_PASSWORD);
		 */

		JSONObject jsonObject = getKeysJson();
		try {
			jsonObject.put("email", userEmail);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String serverResponse = ServerInteractor.sendJson(ApiDetails.HOME_URL
				+ ApiDetails.FORGOT_PASSWORD, jsonObject.toString());

		Log.i("post data", ApiDetails.HOME_URL + ApiDetails.FORGOT_PASSWORD
				+ jsonObject.toString());
		Log.i("client server", "Server response - " + serverResponse);

		return serverResponse;

	}

	public String logOutAPI(String id, String accessToken) {

		/*
		 * List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 * pairs.add(new BasicNameValuePair("id", id)); pairs.add(new
		 * BasicNameValuePair("appKey", ApiDetails.APP_KEY)); pairs.add(new
		 * BasicNameValuePair("version", ApiDetails.APP_VERSION)); pairs.add(new
		 * BasicNameValuePair("accessToken", accessToken));
		 * 
		 * String serverResponse = ServerInteractor.getUrlData(pairs,
		 * ApiDetails.HOME_URL + ApiDetails.LOG_OUT);
		 */

		JSONObject jsonObject = getKeysJson();
		try {
			jsonObject.put("id", id);
			jsonObject.put("accessToken", accessToken);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String serverResponse = ServerInteractor.sendJson(ApiDetails.HOME_URL
				+ ApiDetails.LOG_OUT, jsonObject.toString());

		Log.i("post data", ApiDetails.HOME_URL + ApiDetails.LOG_OUT
				+ jsonObject.toString());
		Log.i("client server", "Server response - " + serverResponse);

		return serverResponse;
	}

	public String myStationAPI(String id, String accessToken) {

		/*
		 * List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 * pairs.add(new BasicNameValuePair("id", id)); pairs.add(new
		 * BasicNameValuePair("appKey", ApiDetails.APP_KEY)); pairs.add(new
		 * BasicNameValuePair("version", ApiDetails.APP_VERSION)); pairs.add(new
		 * BasicNameValuePair("accessToken", accessToken));
		 * 
		 * String serverResponse = ServerInteractor.getUrlData(pairs,
		 * ApiDetails.HOME_URL + ApiDetails.MY_STATION);
		 */

		JSONObject jsonObject = getKeysJson();
		try {
			jsonObject.put("id", id);

			jsonObject.put("accessToken", accessToken);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String serverResponse = ServerInteractor.sendJson(ApiDetails.HOME_URL
				+ ApiDetails.MY_STATION, jsonObject.toString());

		Log.i("post data",ApiDetails.HOME_URL + ApiDetails.MY_STATION
				+ jsonObject.toString());
		Log.i("client server", "Server response - " + serverResponse);

		return serverResponse;
	}

	public String referrralEmails(String id, String accessToken,
			String emailsJSONArray) {

		/*
		 * List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 * pairs.add(new BasicNameValuePair("id", id)); pairs.add(new
		 * BasicNameValuePair("appKey", ApiDetails.APP_KEY)); pairs.add(new
		 * BasicNameValuePair("version", ApiDetails.APP_VERSION)); pairs.add(new
		 * BasicNameValuePair("accessToken", accessToken)); pairs.add(new
		 * BasicNameValuePair("emails", emails));
		 * 
		 * 
		 * String serverResponse = ServerInteractor.getUrlData(pairs,
		 * ApiDetails.HOME_URL + ApiDetails.REFERRALS);
		 */

		
		
		JSONObject jsonObject = getKeysJson();
		try {
			JSONArray jsonArray=new JSONArray(emailsJSONArray);
			jsonObject.put("id", id);

			jsonObject.put("accessToken", accessToken);
			jsonObject.put("data", jsonArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String serverResponse = ServerInteractor.sendJson(ApiDetails.HOME_URL
				+ ApiDetails.REFERRALS, jsonObject.toString());

		Log.i("post data", ApiDetails.HOME_URL + ApiDetails.REFERRALS
				+ jsonObject.toString());
		Log.i("client server", "Server response - " + serverResponse);

		return serverResponse;
	}

	public String referrralSMS(String id, String accessToken,
			String mobileNoArray) {

		/*
		 * List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 * pairs.add(new BasicNameValuePair("id", id)); pairs.add(new
		 * BasicNameValuePair("appKey", ApiDetails.APP_KEY)); pairs.add(new
		 * BasicNameValuePair("version", ApiDetails.APP_VERSION)); pairs.add(new
		 * BasicNameValuePair("accessToken", accessToken)); pairs.add(new
		 * BasicNameValuePair("mobileNumbers", mobileNo));
		 * 
		 * 
		 * String serverResponse = ServerInteractor.getUrlData(pairs,
		 * ApiDetails.HOME_URL + ApiDetails.REFERRALSMS);
		 */

		JSONObject jsonObject = getKeysJson();
		try {
			JSONArray jsonArray=new JSONArray(mobileNoArray);
			jsonObject.put("id", id);

			jsonObject.put("accessToken", accessToken);
			jsonObject.put("data", jsonArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String serverResponse = ServerInteractor.sendJson(ApiDetails.HOME_URL
				+ ApiDetails.REFERRALSMS, jsonObject.toString());

		Log.i("post data", ApiDetails.HOME_URL + ApiDetails.REFERRALSMS
				+ jsonObject.toString());
		Log.i("client server", "Server response - " + serverResponse);

		return serverResponse;
	}
	
	
	public String fetchInvited(String id, String accessToken) {

		

		JSONObject jsonObject = getKeysJson();
		try {
			jsonObject.put("id", id);

			jsonObject.put("accessToken", accessToken);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String serverResponse = ServerInteractor.sendJson(ApiDetails.HOME_URL
				+ ApiDetails.FETCH_INVITE_EMAILS, jsonObject.toString());

		Log.i("post data",ApiDetails.HOME_URL + ApiDetails.FETCH_INVITE_EMAILS
				+ jsonObject.toString());
		Log.i("client server", "Server response - " + serverResponse);

		return serverResponse;
	}

}
