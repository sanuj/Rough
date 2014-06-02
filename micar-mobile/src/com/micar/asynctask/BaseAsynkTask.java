package com.micar.asynctask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.micar.parser.Parser;
import com.micar.utils.ApiDetails;
import com.micar.utils.ApiDetails.REQUEST_TYPE;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author rajendra BaseAsynk abstract class to handle progress dialog during
 *         async call.
 */
public abstract class BaseAsynkTask extends AsyncTask<Void, Void, Integer>
		implements IGetPostRequest {
	protected ProgressDialog progressDialog = null;
	protected Context mContext;
	protected String mDialogMessage, mApiMessage;
	protected boolean mShowDialog;

	protected HashMap<String, String> mParams;
	protected REQUEST_TYPE mRequestType;
	protected IAsyncCaller caller;
	protected String mUrl;
	@SuppressWarnings("rawtypes")
	protected Parser parser;
	protected HashMap<String, File> mFileMap;

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
	public BaseAsynkTask(Context context, String dialogMessage,
			boolean showDialog, String url, HashMap<String, String> mParamMap,
			REQUEST_TYPE requestType, IAsyncCaller caller, Parser parser) {

		this.mContext = context;
		this.mDialogMessage = dialogMessage;
		mShowDialog = showDialog;
		mParams = mParamMap;
		mRequestType = requestType;
		this.caller = caller;
		this.mUrl = url;
		this.parser = parser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {

		super.onPreExecute();
		if (mShowDialog) {
			progressShow(mDialogMessage);
		}

	}

	/**
	 * Abstract method for interaction with Web Service.
	 */
	public abstract Integer doInBackground(Void... params);

	@Override
	protected void onPostExecute(Integer result) {

		super.onPostExecute(result);

		if (progressDialog != null) {
			progressCancel();
		}
	}

	public void progressShow(String msg) {
		try {

			if (progressDialog == null)
				progressDialog = new ProgressDialog(mContext);
			progressDialog.setMessage(msg);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setCancelable(true);

			progressDialog.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void progressCancel() {
		try {
			if (progressDialog != null)
				progressDialog.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getJsonParam(HashMap<String, String> paramMap) {
		if (paramMap == null) {
			return null;
		}

		JSONObject jsonObject = getKeysJson();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			try {

				if (entry.getValue().startsWith("[")
						&& entry.getValue().endsWith("]")) {
					JSONArray jsonArray = new JSONArray(entry.getValue());
					jsonObject.put(entry.getKey(), jsonArray);
				} else {
					jsonObject.put(entry.getKey(), entry.getValue().toString());

				}

			} catch (JSONException e) {

				e.printStackTrace();
			}
		}

		return jsonObject.toString();
	}

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

	@Override
	public String getURL(String url, HashMap<String, String> paramMap) {
		StringBuilder sb = new StringBuilder().append(url);
		boolean first = true;
		if (paramMap != null && paramMap.size() > 0) {
			sb.append("?");
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				if (first) {
					sb.append(entry.getKey()).append("=")
							.append(entry.getValue());
					first = false;
				} else {
					sb.append("&").append(entry.getKey()).append("=")
							.append(entry.getValue());
				}

			}
		}
		return sb.toString();
	}

	@Override
	public List<NameValuePair> getParams(HashMap<String, String> paramMap) {
		if (paramMap == null) {
			return null;
		}
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();

		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			paramsList.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}
		return paramsList;
	}

}
