package com.micar.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.micar.activity.R;

@SuppressLint("NewApi")
public class Utility {

	public static DecimalFormat mDecimalFormat = new DecimalFormat("#.##");

	public static final int NO_INTERNET_CONNECTION = 1;
	public static final int NO_GPS_ACCESS = 2;

	private static final int CONNECTION_TIMEOUT = 25000;

	public static String getDateFormatInHMS(long milliseconds) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(milliseconds);

		return simpleDateFormat.format(date);

	}

	public static boolean CheckEnableGPS(Context context) {
		boolean isGPSEnabled = false;
		try {
			LocationManager service = (LocationManager) context
					.getSystemService(context.LOCATION_SERVICE);
			boolean enabled = service
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			if (enabled) {
				isGPSEnabled = true;// GPS Enabled
			} else {
				isGPSEnabled = false;
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return isGPSEnabled;
	}

	public static void setWindowFlagPref(Context context) {
		((Activity) context).getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
						| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
	}

	public static String getDeviceId(Context context) {
		String str = null;
		str = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		return str;
	}

	public static long getSharedPrefLongData(Context context, String key) {

		SharedPreferences userAcountPreference = context.getSharedPreferences(
				Constants.APP_PREF, Context.MODE_PRIVATE);

		return userAcountPreference.getLong(key, 0l);

	}

	public static boolean getSharedPrefBooleanData(Context context, String key) {

		SharedPreferences userAcountPreference = context.getSharedPreferences(
				Constants.APP_PREF, Context.MODE_PRIVATE);

		return userAcountPreference.getBoolean(key, false);

	}

	public static void setSharedPrefBooleanData(Context context, String key,
			boolean value) {
		SharedPreferences appInstallInfoSharedPref = context
				.getSharedPreferences(Constants.APP_PREF, context.MODE_PRIVATE);
		Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
		appInstallInfoEditor.putBoolean(key, value);
		appInstallInfoEditor.commit();
	}

	public static int getSharedPrefIntData(Context context, String key) {

		SharedPreferences userAcountPreference = context.getSharedPreferences(
				Constants.APP_PREF, Context.MODE_PRIVATE);

		return userAcountPreference.getInt(key, 0);

	}

	public static void setSharedPrefIntData(Context context, String key,
			int value) {
		SharedPreferences appInstallInfoSharedPref = context
				.getSharedPreferences(Constants.APP_PREF, context.MODE_PRIVATE);
		Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
		appInstallInfoEditor.putInt(key, value);
		appInstallInfoEditor.commit();
	}

	public static String getSharedPrefStringData(Context context, String key) {

		SharedPreferences userAcountPreference = context.getSharedPreferences(
				Constants.APP_PREF, Context.MODE_PRIVATE);

		return userAcountPreference.getString(key, "");

	}

	public static void showNwNotAvailToast(Context ctx) {
		Toast.makeText(ctx,
				ctx.getResources().getString(R.string.network_nt_avail),
				Toast.LENGTH_SHORT).show();
	}

	public static void setSharedPreStringData(Context context, String key,
			String value) {
		SharedPreferences appInstallInfoSharedPref = context
				.getSharedPreferences(Constants.APP_PREF, context.MODE_PRIVATE);
		Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
		appInstallInfoEditor.putString(key, value);
		appInstallInfoEditor.commit();
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
				|| connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
						.getState() == NetworkInfo.State.CONNECTING) {
			return true;
		} else if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState() == NetworkInfo.State.CONNECTED
				|| connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
						.getState() == NetworkInfo.State.CONNECTING) {

			return true;
		} else
			return false;

	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {

			/* Log.e("Exception", ex + ""); */

		}
	}

	public static Bitmap decodeFile(File f) {
		try {

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, options);

			final int REQUIRED_SIZE = 300;

			// Find the correct scale value. It should be the power of 2.
			int width_tmp = options.outWidth, height_tmp = options.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inPreferredConfig = Bitmap.Config.ARGB_8888;
			o2.inSampleSize = scale;
			// o2.inPurgeable = true;

			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] getBitmapToByteArray(Bitmap bitmap) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100 /* ignored for PNG */, bos);
		return bos.toByteArray();
	}

	public static String getDateInFormatFromMilisecond(long timeInMs)
			throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		Date dateObj = new Date();
		dateObj.setTime(timeInMs);

		return dateFormat.format(dateObj);

	}

	public static String getTimeInFormatFromDTString(String date)
			throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");

		Date dateObj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);

		return dateFormat.format(dateObj);

	}

	public static String getTimeInFormatFromMS(long ms) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

		Date dateObj = new Date(ms);

		return dateFormat.format(dateObj);

	}

	public static String getAMPMInFormatFromMS(long ms) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("a");

		Date dateObj = new Date(ms);

		return dateFormat.format(dateObj);

	}

	public static String getDayInFormatFromMS(long ms) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d");

		Date dateObj = new Date(ms);

		return dateFormat.format(dateObj);

	}

	public static String httpGetRequestToServer(String Url) {
		String content = null;
		/* Log.w("Url", Url + "  "); */
		try {
			// URLEncoder url =
			String urlStr = Url;
			URL url = new URL(urlStr);
			URI uri = null;
			try {
				uri = new URI(url.getProtocol(), url.getUserInfo(),
						url.getHost(), url.getPort(), url.getPath(),
						URLDecoder.decode(url.getQuery(), "UTF-8"),
						url.getRef());

				url = uri.toURL();

			} catch (URISyntaxException e) {

				e.printStackTrace();
				return "false";
			}

			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					Constants.CONNECTION_TIME_OUT);

			HttpClient client = new DefaultHttpClient(httpParameters);

			client.getParams().setParameter("http.socket.timeout",
					new Integer(15000));

			HttpResponse response = client.execute(new HttpGet(uri));
			Log.w("uri", uri + " ");
			content = EntityUtils.toString(response.getEntity());
		} catch (ParseException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		Log.w("response", content + " ");
		return content;
	}

	public static Address getLatLongFromAddress(String addressString,
			Context context) {

		Geocoder coder = new Geocoder(context, Locale.getDefault());

		List<Address> address = null;

		try {
			address = coder.getFromLocationName(addressString, 1);
		} catch (IOException e) {

			e.printStackTrace();
		}
		if (address != null) {

			Address location = address.get(0);

			return location;

		} else {
			return null;
		}

	}

	public static void showKeyboard(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	public static void hideKeyboard(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

		/*
		 * context.getWindow().setSoftInputMode(
		 * WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		 */
	}

	public static boolean isDebuggable(Context ctx) {
		boolean debuggable = false;

		PackageManager pm = ctx.getPackageManager();
		try {
			ApplicationInfo appinfo = pm.getApplicationInfo(
					ctx.getPackageName(), 0);
			debuggable = (0 != (appinfo.flags &= ApplicationInfo.FLAG_DEBUGGABLE));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return debuggable;
	}

	public static String httpPostRequestToServer(String URL, Object paramsList) {

		HttpParams httpParameters = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(httpParameters,
				CONNECTION_TIMEOUT);

		int timeoutSocket = 15000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));

		ClientConnectionManager cm = new ThreadSafeClientConnManager(
				httpParameters, registry);

		HttpClient client = new DefaultHttpClient(cm, httpParameters);

		/*
		 * client.getParams().setParameter("http.socket.timeout", new
		 * Long(10000));
		 */

		HttpPost httpPost = new HttpPost(URL);

		httpPost.addHeader(HTTP.CONTENT_TYPE,
				"application/x-www-form-urlencoded");

		httpPost.addHeader(HTTP.CHARSET_PARAM, "UTF-8");

		try {
			if (paramsList != null) {
				HttpEntity entity = new StringEntity(paramsList.toString(),
						"UTF-8");

				Log.w("paramsList:", paramsList.toString() + "");
				httpPost.setEntity(entity);
			}
			HttpResponse response = client.execute(httpPost);

			String responseEntity = EntityUtils.toString(response.getEntity());
			responseEntity = responseEntity.trim();

			Log.w("server resp:", responseEntity);

			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (cm != null) {

				cm.shutdown();
			}
		}
	}

	public static String httpJsonRequest(String url, String json) {

		String websiteData = "error";

		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(),
				CONNECTION_TIMEOUT); // Timeout
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

	public static void makeCall(Context context, String number) {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + number));
		context.startActivity(intent);
	}

	public static void sendEmail(Context context, String[] recipients) {
		// String[] recipients = new String[] { "test@gmail.com" };

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("plain/text");
		intent.putExtra(Intent.EXTRA_EMAIL, recipients);
		intent.putExtra(Intent.EXTRA_SUBJECT, "");
		intent.putExtra(Intent.EXTRA_TEXT, "");
		context.startActivity(Intent.createChooser(intent, " "));

	}

	public static void setLocale(Context context, int pos) {
		Locale locale;
		if (!Utility.getSharedPrefStringData(context,
				Constants.SHARED_PREF_VALUE_LOCALE).equals("")) {
			locale = new Locale(Utility.getSharedPrefStringData(context,
					Constants.SHARED_PREF_VALUE_LOCALE));
		} else {
			Locale.setDefault(Locale.getDefault());
			locale = Locale.getDefault();
		}
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		((ContextWrapper) context)
				.getBaseContext()
				.getResources()
				.updateConfiguration(
						config,
						((ContextWrapper) context).getBaseContext()
								.getResources().getDisplayMetrics());
	}

	public static String encryptPassword(String password) {
		String sha1 = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(password.getBytes("UTF-8"));
			sha1 = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sha1;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	public static AlertDialog showOKCancelDialog(Context context, String msg,
			String title) {
		return new AlertDialog.Builder(context)
				// .setIcon(android.R.attr.alertDialogIcon)
				.setMessage(msg)
				.setTitle(title)

				.setPositiveButton(R.string.alert_dialog_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						})
				.setNegativeButton(R.string.alert_dialog_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						}).create();
	}

	public static AlertDialog showOKOnlyDialog(Context context, String msg,
			String title) {
		return new AlertDialog.Builder(context)
				// .setIcon(android.R.attr.alertDialogIcon)
				.setMessage(msg)
				.setTitle(title)

				.setPositiveButton(R.string.alert_dialog_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

							}
						}).create();
	}

	public static AlertDialog showSettingDialog(final Context context,
			String msg, String title, final int id) {
		return new AlertDialog.Builder(context)
				// .setIcon(android.R.attr.alertDialogIcon)
				.setMessage(msg)
				.setTitle(title)

				.setPositiveButton(R.string.alert_dialog_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						})
				.setNegativeButton(R.string.alert_dialog_setting,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								switch (id) {
								case Utility.NO_INTERNET_CONNECTION:
									context.startActivity(new Intent(
											android.provider.Settings.ACTION_SETTINGS));

									break;
								case Utility.NO_GPS_ACCESS:

									context.startActivity(new Intent(
											android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
									break;

								default:
									break;
								}

							}
						}).create();
	}

	public static void showToastMessage(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	/*
	 * 
	 * @rajendra
	 * 
	 * These methods are to make asynctasks concurrent, and run on paralel on
	 * android 3+
	 */

	public static <P, T extends AsyncTask<P, ?, ?>> void execute(T task) {
		execute(task, (P[]) null);
	}

	@SuppressLint("NewApi")
	public static <P, T extends AsyncTask<P, ?, ?>> void execute(T task,
			P... params) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		} else {
			task.execute(params);
		}
	}
	
	
	
}
