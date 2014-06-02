package com.micar.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class ServerInteractor {

	static final int timeoutConnection = 10000;
	private String websiteData = "error";

	public static String getUrlData(List<NameValuePair> pairs, String URL) {
		String websiteData = "error";

		HttpParams httpParameters = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);

		DefaultHttpClient client = new DefaultHttpClient(httpParameters);

		HttpPost post = new HttpPost(URL);
		post.addHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");

		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));

			HttpResponse response = client.execute(post);

			websiteData = EntityUtils.toString(response.getEntity());

			websiteData = websiteData.trim();
		} catch (Exception e) {
			websiteData = "error";
			e.printStackTrace();
		}
		return websiteData;
	}

	/*
	 * public String getUrlData(String URL) {
	 * 
	 * HttpParams httpParameters = new BasicHttpParams();
	 * HttpConnectionParams.setConnectionTimeout(httpParameters,
	 * timeoutConnection); DefaultHttpClient client = new
	 * DefaultHttpClient(httpParameters); HttpPost post = new HttpPost(URL); try
	 * {
	 * 
	 * HttpResponse response = client.execute(post);
	 * 
	 * websiteData = EntityUtils.toString(response.getEntity()); websiteData =
	 * websiteData.trim(); } catch (Exception e) { websiteData = "error";
	 * e.printStackTrace(); } Log.w("Response:", websiteData + ""); return
	 * websiteData; }
	 */

	public static String httpGetRequestToServer(String Url) {
		String content = "";
		try {
			String urlStr = Url.trim();
			URI uri = null;
			try {
				uri = new URI(urlStr.replaceAll(" ", "%20"));
			} catch (URISyntaxException e) {
				e.printStackTrace();
				return "false";
			}

			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			HttpClient httpclient = new DefaultHttpClient(httpParameters);
			HttpResponse response = httpclient.execute(new HttpGet(uri));
			content = EntityUtils.toString(response.getEntity());
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/* Log.w("response", content + " "); */
		return content;
	}

	/*
	 * public void postData(String url,JSONObject obj) { // Create a new
	 * HttpClient and Post Header
	 * 
	 * HttpParams myParams = new BasicHttpParams();
	 * HttpConnectionParams.setConnectionTimeout(myParams, 10000);
	 * HttpConnectionParams.setSoTimeout(myParams, 10000); HttpClient httpclient
	 * = new DefaultHttpClient(HttpConnectionParams); String
	 * json=obj.toString();
	 * 
	 * try {
	 * 
	 * HttpPost httppost = new HttpPost(url.toString());
	 * httppost.setHeader("Content-type", "application/json");
	 * 
	 * StringEntity se = new StringEntity(obj.toString());
	 * se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
	 * "application/json")); httppost.setEntity(se);
	 * 
	 * HttpResponse response = httpclient.execute(httppost); String temp =
	 * EntityUtils.toString(response.getEntity()); Log.i("tag", temp);
	 * 
	 * 
	 * } catch (ClientProtocolException e) {
	 * 
	 * } catch (IOException e) { } }
	 */

	public static String sendJson(String url, String json) {

		String websiteData = "error";

		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), timeoutConnection); // Timeout
																				// Limit
		HttpResponse response;

		HttpPost post = new HttpPost(url);

		StringEntity se;
		try {
			se = new StringEntity(json.toString());

			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			post.setEntity(se);
			response = client.execute(post);

			/* Checking response */
			if (response != null) {

				websiteData = EntityUtils.toString(response.getEntity());
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			websiteData = "error";
			return websiteData;
		}

		return websiteData;

	}

}