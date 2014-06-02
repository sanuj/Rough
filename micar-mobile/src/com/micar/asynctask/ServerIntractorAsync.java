package com.micar.asynctask;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.micar.activity.R;
import com.micar.model.Model;
import com.micar.parser.Parser;
import com.micar.utils.ApiDetails.REQUEST_TYPE;
import com.micar.utils.Utility;

public class ServerIntractorAsync extends BaseAsynkTask {

	private String mResponse = null;
	private Model model;
	private ArrayList<Model> modelList;

	/**
	 * 
	 * @param context
	 *            ,Context to show progress dialog
	 * @param dialogMessage
	 *            , Dialog message for progress dialog
	 * @param showDialog
	 *            , boolean varialble to show progress dialog or not
	 * @param url
	 *            , Url of the web service
	 * @param mParamMap
	 *            , HashMap of keys
	 * @param requestType
	 *            , Type of request(GET/POST)
	 * @param caller
	 *            , Caller activity which will recieve response
	 * @param parser
	 *            , JSON parser for the response
	 */
	public ServerIntractorAsync(Context context, String dialogMessage,
			boolean showDialog, String url, HashMap<String, String> mParamMap,
			REQUEST_TYPE requestType, IAsyncCaller caller, Parser parser) {
		super(context, dialogMessage, showDialog, url, mParamMap, requestType,
				caller, parser);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.micar.asynctask.BaseAsynkTask#doInBackground(java.lang.Void[])
	 */
	@Override
	public Integer doInBackground(Void... params) {

		if (!Utility.isNetworkAvailable(mContext)) {
			return 0;
		}

		switch (mRequestType) {
		case GET:
			mResponse = Utility.httpGetRequestToServer(mUrl);
			Log.i("mResponse", mResponse + "@");
			return parseResposnse(mResponse);
		case POST:

			if (mParams != null) {

				mResponse = Utility
						.httpJsonRequest(mUrl, getJsonParam(mParams));
				Log.i("url", mUrl+getJsonParam(mParams).toString());
			} else {

				mResponse = Utility.httpJsonRequest(mUrl, null);
			}
			Log.i("mResponse", mResponse + "@");

			return parseResposnse(mResponse);

		default:
			return -1;
		}

	}

	@Override
	protected void onPostExecute(Integer result) {

		super.onPostExecute(result);
		if (!isCancelled() && result == 1) {
			if (model != null) {
				caller.onComplete(model);
			} else {
				if (modelList != null) {

					caller.onComplete(modelList);
				} else {
					Utility.showToastMessage(mContext, "Server response error!");
				}
			}
		} else if (result == 0) {

			Utility.showSettingDialog(
					mContext,
					mContext.getResources().getString(R.string.no_internet_msg),
					mContext.getResources().getString(
							R.string.no_internet_title),
					Utility.NO_INTERNET_CONNECTION).show();

		} else {
			Utility.showToastMessage(mContext, mContext.getResources().getString(R.string.error_msg));
		}
	}

	@SuppressWarnings("unchecked")
	private int parseResposnse(String response) {
		if (response != null) {
			try {

				model = parser.parse(new JSONObject(response));
				return 1;
			} catch (JSONException e1) {
				e1.printStackTrace();
				return -1;

			}
		}

		return -1;

	}

}
