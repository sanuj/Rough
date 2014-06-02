package com.micar.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.fragment.FragmentAlertDialog;
import com.micar.model.MakeReservationInfo;
import com.micar.model.MessageInfo;
import com.micar.model.Model;
import com.micar.parser.MessageParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Utility;

public class ActivityMakeReservation extends FragmentActivity implements
		IAsyncCaller, FragmentAlertDialog.OnDialogActionListener {

	private WebView mWebView;
	private MakeReservationInfo makeReservationInfo;
	protected ProgressDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_make_reservation);

		makeReservationInfo = (MakeReservationInfo) getIntent()
				.getParcelableExtra("makeReservation");
		mWebView = (WebView) findViewById(R.id.webView1);

		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.setWebViewClient(new PaymentWebViewClient());
		mWebView.loadUrl(makeReservationInfo.getUrl()); // http://micar.qa3.intelligrape.net//member/mobileReservationPayment
		progressShow("Loading...");
	}

	private class PaymentWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			Log.e("url in override loading", url);
			return false;

		}

		@Override
		public void onPageFinished(WebView view, String url) {

			super.onPageFinished(view, url);
			Log.e("onPageFinished", url);
			progressCancel();
			// Toast.makeText(ActivityMakeReservation.this,
			// "Page loading done: " + url, Toast.LENGTH_SHORT).show();
			if (makeReservationInfo.getCallbackUrl().equalsIgnoreCase(url)) {

				checkReservationPayment();

			}

		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
			Log.e("onPageStarted", url);

		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// TODO Auto-generated method stub
			super.onReceivedError(view, errorCode, description, failingUrl);
			Log.e("failingUrl", failingUrl);

			// Toast.makeText(ActivityMakeReservation.this, "Error page",
			// Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onComplete(Model object) {

		if (object instanceof MessageInfo) {
			MessageInfo msgInfo = (MessageInfo) object;

			if (msgInfo.getStatus() == 1) {
				FragmentAlertDialog alert = new FragmentAlertDialog(
						FragmentAlertDialog.DIALOG_TYPE.OK_ONLY, this
								.getResources().getString(R.string.app_name),
						msgInfo.getMessage());
				alert.setCancelable(false);
				alert.show(getSupportFragmentManager(), "Alert_Dialog");
			} else {
				FragmentAlertDialog alert = new FragmentAlertDialog(
						FragmentAlertDialog.DIALOG_TYPE.OK_ONLY,
						this.getResources().getString(R.string.error_title),
						msgInfo.getMessage());
				alert.setCancelable(false);
				alert.show(getSupportFragmentManager(), "Alert_Dialog");
			}

		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {

	}

	@Override
	public void onPositiveClick() {

		ActivityMakeReservation.this.finish();

	}

	@Override
	public void onNegativeClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void progressShow(String msg) {
		try {

			if (progressDialog == null)
				progressDialog = new ProgressDialog(
						ActivityMakeReservation.this);
			progressDialog.setMessage(msg + "...");
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
			progressDialog = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkReservationPayment() {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accessToken", Utility.getSharedPrefStringData(
				ActivityMakeReservation.this,
				com.micar.utils.Constants.KEY_ACCESS_TOKEN));
		paramMap.put("transactionId", makeReservationInfo.getTransactionId());

		MessageParser messageParser = new MessageParser();

		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				ActivityMakeReservation.this, getResources().getString(
						R.string.please_wait), true, ApiDetails.HOME_URL
						+ ApiDetails.CHECK_RESERVATION_PAYMENT, paramMap,
				ApiDetails.REQUEST_TYPE.POST, ActivityMakeReservation.this,
				messageParser);

		Utility.execute(serverIntractorAsync);

	}

}
