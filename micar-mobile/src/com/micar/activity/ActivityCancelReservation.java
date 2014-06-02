package com.micar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.model.MessageInfo;
import com.micar.model.Model;
import com.micar.model.ReservationCancelCharge;
import com.micar.parser.MessageParser;
import com.micar.parser.ReservationCancelChargeParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;

public class ActivityCancelReservation extends Activity implements
		OnClickListener, IAsyncCaller {

	private TextView mTxtViewConfirmation, mTxtViewCharge;
	private Button mButtonCancel;
	private LinearLayout mLinearLytChargeList;
	private String reservationId;
	private ReservationCancelCharge reservationCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_cancel_reservation);
		reservationId = getIntent().getStringExtra("reservationId");
		getViewsId();
		fetchCancelReservationCharges();
	}

	private void fetchCancelReservationCharges() {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accessToken", Utility.getSharedPrefStringData(this,
				Constants.KEY_ACCESS_TOKEN));
		paramMap.put("reservationId", reservationId);

		ReservationCancelChargeParser parser = new ReservationCancelChargeParser();
		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				this, getResources().getString(R.string.please_wait), true,
				ApiDetails.HOME_URL
						+ ApiDetails.FETCH_CANCEL_RESERVATION_CHARGES,
				paramMap, ApiDetails.REQUEST_TYPE.POST, this, parser);

		Utility.execute(serverIntractorAsync);

	}

	private void getViewsId() {

		mButtonCancel = (Button) findViewById(R.id.btn_cancel);
		mTxtViewConfirmation = (TextView) findViewById(R.id.txtview_confirmation);
		mTxtViewCharge = (TextView) findViewById(R.id.txtview_charge_amnt);
		mLinearLytChargeList = (LinearLayout) findViewById(R.id.linear_lyt_charges);
		mButtonCancel.setOnClickListener(this);
		mLinearLytChargeList.setOnClickListener(this);

		String confirmationMsg = String.format(
				getResources().getString(R.string.cancellation_confirm),
				reservationId);
		mTxtViewConfirmation.setText(confirmationMsg);

	}

	@Override
	public void onComplete(Model object) {

		if (object instanceof ReservationCancelCharge) {
			reservationCancel = (ReservationCancelCharge) object;

			if (reservationCancel.getStatus() == 1) {

				List<ReservationCancelCharge> cancelChargeList = reservationCancel
						.getCancelChargeList();

				if (cancelChargeList != null && cancelChargeList.size() > 0) {

					for (int i = 0; i < cancelChargeList.size(); i++) {

						if (cancelChargeList.get(i).isActive()) {

							String charge = String.format(getResources()
									.getString(R.string.cancellation_charge),
									cancelChargeList.get(i)
											.getChargePercentage());

							mTxtViewCharge.setText(charge);

						}

					}

				}

			} else {

				String message = reservationCancel.getMessage();

				if (message != null && !message.equalsIgnoreCase("")) {

					Utility.showOKOnlyDialog(ActivityCancelReservation.this,
							message,
							getResources().getString(R.string.error_title))
							.show();
				}
			}
		} else if (object instanceof MessageInfo) {
			MessageInfo message = (MessageInfo) object;

			if (message.getStatus() == 1) {

				String msg = message.getMessage();

				if (msg != null && !msg.equalsIgnoreCase("")) {

					// Utility.showOKOnlyDialog(ActivityCancelReservation.this,
					// msg, "Cancel").show();

					showOKOnlyDialog(ActivityCancelReservation.this, msg,
							"Cancel");
				}

			}

		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_cancel:

			cancelReservation();

			break;

		case R.id.linear_lyt_charges:

			if (reservationCancel != null) {
				if (reservationCancel.getCancelChargeList() != null
						&& reservationCancel.getCancelChargeList().size() > 0) {
					showCancelReservationList();
				}
			}
			break;

		default:
			break;
		}

	}

	private void cancelReservation() {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accessToken", Utility.getSharedPrefStringData(this,
				Constants.KEY_ACCESS_TOKEN));
		paramMap.put("reservationId", reservationId);

		MessageParser parser = new MessageParser();
		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				this, getResources().getString(R.string.please_wait), true,
				ApiDetails.HOME_URL + ApiDetails.CANCEL_RESERVATOIN, paramMap,
				ApiDetails.REQUEST_TYPE.POST, this, parser);

		Utility.execute(serverIntractorAsync);

	}

	private void showCancelReservationList() {

		Intent intent = new Intent(ActivityCancelReservation.this,
				ActivityCancellationBreakDown.class);
		intent.putExtra("reservationCancel", reservationCancel);
		startActivity(intent);

	}

	public void showOKOnlyDialog(Context context, String msg, String title) {
		new AlertDialog.Builder(context)
				// .setIcon(android.R.attr.alertDialogIcon)
				.setMessage(msg)
				.setTitle(title)

				.setPositiveButton(R.string.alert_dialog_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								if (ActivityReservationDetails.getInstance() != null) {
									ActivityReservationDetails.getInstance()
											.finish();

								}
								ActivityCancelReservation.this.finish();

							}
						}).create().show();
	}

}
