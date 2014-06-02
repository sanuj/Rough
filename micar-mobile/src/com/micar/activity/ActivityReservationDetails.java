package com.micar.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.fragment.FragmentAlertDialog;
import com.micar.fragment.FragmentAlertDialog.OnDialogActionListener;
import com.micar.model.Model;
import com.micar.model.ReservationDetail;
import com.micar.parser.ReservationDetialParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;

public class ActivityReservationDetails extends ActionBarActivity implements
		OnDialogActionListener, OnClickListener, IAsyncCaller {

	private TableRow tableRowAccessories, tableRowPermittedStates;
	private Button mBtnCancel, mBtnModify;
	private String reservationId;

	private boolean isModify = false;

	private ReservationDetail reservationDetail;

	private TextView mTxtViewCity, mTxtViewCarType, mTxtViewStartTime,
			mTxtViewEndTime, mTxtViewPickUpaddress, mTxtViewDeliveryAddress,
			mTxtViewKmRate, mTxtViewEstimatedCost, mTxtViewAccessories,
			mTxtViewInsurance, mTxtVIewPermitedState;
	
	
	private static ActivityReservationDetails activityReservationDetails;
	
	
	public static ActivityReservationDetails getInstance()
	{
		return activityReservationDetails;
	}
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		activityReservationDetails=this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reservation_details);
		reservationId = getIntent().getStringExtra("reservationId");
		getViewsID();
		fetchReservationDetail();
	}

	private void getViewsID() {
		mBtnCancel = (Button) findViewById(R.id.btn_cancel);
		mBtnModify = (Button) findViewById(R.id.btn_modify);

		mTxtViewCity = (TextView) findViewById(R.id.txtview_city);

		mTxtViewCarType = (TextView) findViewById(R.id.txtview_city);
		mTxtViewStartTime = (TextView) findViewById(R.id.txtview_start_time);
		mTxtViewEndTime = (TextView) findViewById(R.id.txtview_end_time);
		mTxtViewPickUpaddress = (TextView) findViewById(R.id.txtview_pickup_address);
		mTxtViewDeliveryAddress = (TextView) findViewById(R.id.txtview_delivery_address);
		mTxtViewKmRate = (TextView) findViewById(R.id.txtview_km_rate);
		mTxtViewEstimatedCost = (TextView) findViewById(R.id.txtview_estimated_cost);
		mTxtViewAccessories = (TextView) findViewById(R.id.txtview_accessories);
		mTxtViewInsurance = (TextView) findViewById(R.id.txtview_insurance);
		mTxtVIewPermitedState = (TextView) findViewById(R.id.txtview_permited_state);

		tableRowAccessories = (TableRow) findViewById(R.id.tableRow_accessories);
		tableRowPermittedStates = (TableRow) findViewById(R.id.tableRow_permited_states);

		mBtnCancel.setOnClickListener(this);
		mBtnModify.setOnClickListener(this);
		tableRowAccessories.setOnClickListener(this);
		tableRowPermittedStates.setOnClickListener(this);

	}

	@Override
	public void onPositiveClick() {

	}

	@Override
	public void onNegativeClick() {

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_cancel:

			// showCancelDialog();
			launchCancelReservation();

			break;
		case R.id.btn_modify:

			Intent intent = new Intent(ActivityReservationDetails.this,
					ActivityReservationModification.class);
			startActivity(intent);
			break;

		case R.id.tableRow_accessories:

			if (reservationDetail.getAccessoryTypes() != null
					&& !reservationDetail.getAccessoryTypes().equalsIgnoreCase(
							"")) {
				launchDetailActivity(
						getResources().getString(R.string.accessories_label),
						reservationDetail.getAccessoryTypes());
			}

			break;
		case R.id.tableRow_permited_states:

			if (reservationDetail.getPermittedStates() != null
					&& !reservationDetail.getPermittedStates()
							.equalsIgnoreCase("")) {
				launchDetailActivity(
						getResources().getString(R.string.permited_state),
						reservationDetail.getPermittedStates());
			}
			break;

		default:
			break;
		}

	}

	private void launchDetailActivity(String label, String value) {

		Intent intent = new Intent(ActivityReservationDetails.this,
				ActivityStateDetail.class);
		intent.putExtra("detail", value);
		intent.putExtra("detailLabel", label);
		startActivity(intent);

	}

	private void launchCancelReservation() {

		Intent intent = new Intent(ActivityReservationDetails.this,
				ActivityCancelReservation.class);
		intent.putExtra("reservationId", reservationId);

		startActivity(intent);

	}

	private void showCancelDialog() {
		FragmentAlertDialog alert = new FragmentAlertDialog(
				FragmentAlertDialog.DIALOG_TYPE.OK_CANCEL, this.getResources()
						.getString(R.string.canel_reservation_title),
				getResources().getString(R.string.canel_reservation_msg));
		alert.show(getSupportFragmentManager(), "Alert_Dialog");

	}

	private void fetchReservationDetail() {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accessToken", Utility.getSharedPrefStringData(this,
				Constants.KEY_ACCESS_TOKEN));
		paramMap.put("reservationId", reservationId);

		ReservationDetialParser parser = new ReservationDetialParser();
		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				this, getResources().getString(R.string.please_wait), true,
				ApiDetails.HOME_URL + ApiDetails.FETCH_RESERVATION_DETAIL,
				paramMap, ApiDetails.REQUEST_TYPE.POST, this, parser);

		Utility.execute(serverIntractorAsync);

	}

	@Override
	public void onComplete(Model object) {

		if (object instanceof ReservationDetail) {
			reservationDetail = (ReservationDetail) object;

			if (reservationDetail.getStatus() == 1) {

				setData();

			} else {

				String message = reservationDetail.getMessage();

				if (message != null && !message.equalsIgnoreCase("")) {

					Utility.showOKOnlyDialog(this, message, getResources()
							.getString(R.string.error_title)).show();
				}
			}
		}

	}

	private void setData() {

		mTxtViewCity.setText(reservationDetail.getCity());

		mTxtViewCarType.setText(reservationDetail.getVehicleType());
		mTxtViewStartTime.setText(reservationDetail.getStartTime());
		mTxtViewEndTime.setText(reservationDetail.getEndTime());
		// if (reservationDetail.getPickUpAddress() != null
		// && reservationDetail.getPickUpAddress()
		// .equalsIgnoreCase("null")) {
		// mTxtViewPickUpaddress.setText(reservationDetail.getPickUpAddress());
		// }
		//
		// if (reservationDetail.getDeliveryAddress() != null
		// && reservationDetail.getDeliveryAddress().equalsIgnoreCase(
		// "null")) {
		// mTxtViewDeliveryAddress.setText(reservationDetail
		// .getDeliveryAddress());
		// }

		mTxtViewKmRate.setText(reservationDetail.getKmRate());
		mTxtViewEstimatedCost.setText(reservationDetail.getEstimatedCost());
		mTxtViewAccessories.setText(reservationDetail.getAccessoryTypes());

		mTxtViewInsurance.setText(reservationDetail.getInsurance());
		mTxtVIewPermitedState.setText(reservationDetail.getPermittedStates());

	}

	@Override
	public void onComplete(ArrayList<Model> object) {
		// TODO Auto-generated method stub

	}
}
