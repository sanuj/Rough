package com.micar.google.analytics;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.util.Log;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GAServiceManager;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;

public class GoogleAnalyticsUtils
{
	private static final String TRACKING_ID = "UA-43389181-2"; //Rajendra :UA-43437753-1; intelligrape: UA-43389181-2     
		private static final String TEST_TRACKING_ID = "UA-43437753-1";
	private Activity activity;
	private GoogleAnalytics mGaInstance;
	private Tracker mGaTracker;
		private Tracker mTestGaTracker;
	private static GoogleAnalyticsUtils analyticsInstance = null;
	private EasyTracker easyTracker;

	private GoogleAnalyticsUtils(Activity activity)
	{
		try
		{
			this.activity = activity;
			//dispatch interval for send event
			setDispatchPeriod(70);
			initializeTrackerInstance();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static GoogleAnalyticsUtils getGoogleAnalyticsInstance(Activity activity)
	{
		if (analyticsInstance != null)
		{
			return analyticsInstance;
		}
		else
		{
			return analyticsInstance = new GoogleAnalyticsUtils(activity);
		}

	}

	public static String getTimeStampInFormat(long ms) throws Exception
	{
		//Timestamp(string theMonth int theDate int theYear int theHour : int theMinute : int theSecond : int theNano) 
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM dd yyyy HH:mm:ss.SSSZ");

		Date dateObj = new Date(ms);

		return dateFormat.format(dateObj);

	}

	public void setDispatchPeriod(int period)
	{
		GAServiceManager.getInstance().setDispatchPeriod(period);
	}

	public void initializeTrackerInstance()
	{
		try
		{
			mGaInstance = GoogleAnalytics.getInstance(activity);

			// Use the GoogleAnalytics singleton to get two Trackers with
			// unique property IDs.

			mGaTracker = mGaInstance.getTracker(TRACKING_ID);
					mTestGaTracker = mGaInstance.getTracker(TEST_TRACKING_ID);

			
				mGaTracker.setAppName(Constants.AppName);
			

			mGaTracker.setSampleRate(50.0d);
			mTestGaTracker.setSampleRate(50.0d);
			easyTracker = EasyTracker.getInstance();

			easyTracker.activityStart(activity);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void destroyTrackerInstance()
	{
		if (easyTracker != null && activity != null)
		{
			try
			{
				easyTracker.activityStop(activity);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if (mGaInstance != null && mGaTracker != null)
		{
			try
			{
				mGaTracker.close();
				mTestGaTracker.close();
				mGaInstance.closeTracker(mGaTracker);
				mGaInstance.closeTracker(mTestGaTracker);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		easyTracker = null;
		mGaInstance = null;
		mGaTracker = null;
		mTestGaTracker = null;
		this.activity = null;
		analyticsInstance = null;

	}

	public void sendView(String view)
	{
		try
		{
			if (mGaTracker != null)
			{
				mGaTracker.sendView(view);
				mTestGaTracker.sendView(view);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void sendEvent(String category, String action, String label, Long value)
	{
		try
		{
			if (mGaTracker != null)
			{
				mGaTracker.sendEvent(category, action, label, 1l);
				mTestGaTracker.sendEvent(category, action, label, 1l);
				
				
				Log.e("in send Event", "LOGIN"+android.os.Build.VERSION.SDK+
						 android.os.Build.MANUFACTURER+" "+android.os.Build.MODEL);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void sendTiming(String category, String action, String label, Long value)
	{
		//Value interval milisecond
		if (mGaTracker != null)
		{
			mGaTracker.sendTiming(category, value, label, action);
			mTestGaTracker.sendTiming(category, value, label, action);
		}
	}

}
