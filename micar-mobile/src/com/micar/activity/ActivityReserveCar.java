package com.micar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.fragment.FragmentAlertDialog;
import com.micar.model.MakeReservationInfo;
import com.micar.model.Model;
import com.micar.parser.MakeReservationParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Utility;

public class ActivityReserveCar extends FragmentActivity implements
		OnClickListener, IAsyncCaller {

	private static final int PICK_ADDRESS_REQUEST = 1;
	private static final int PICK_ACCESSORIES_REQUEST = 2;
	private static final int PICK_STATE_REQUEST = 3;

	private RelativeLayout mRelativeLytEstimatedCost, mRelativeLytPickUp,
			mRelativeLytDelivery, mRelativeLytAccessroy, mRelativeLytStates;
	private TextView mTxtViewCarType, mTxtViewReservationTime,
			mTxtViewEstimatedCost, mTxtViewPickUpAddress,
			mTxtViewDeliveryAddress, mTxtViewAccessories, mTxtViewStates;
	private CheckBox mChkBoxPickUp, mChkBoxDelivery, mChkBoxInsurance;
	private Button mBtnReserve;

	private boolean isPickUp = false;

	private HashMap<String, String> preSelectedAccessoriesMap = new HashMap<String, String>();

	private HashMap<String, String> preSelectedStateMap = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reserve);
		getViewsID();

	}

	private void getViewsID() {

		mRelativeLytEstimatedCost = (RelativeLayout) findViewById(R.id.relative_lyt_estimated_cost);
		mRelativeLytPickUp = (RelativeLayout) findViewById(R.id.relative_lyt_pickup);
		mRelativeLytDelivery = (RelativeLayout) findViewById(R.id.relative_lyt_delivery);
		mRelativeLytAccessroy = (RelativeLayout) findViewById(R.id.relative_lyt_acessories);
		mRelativeLytStates = (RelativeLayout) findViewById(R.id.relative_lyt_states);

		mTxtViewCarType = (TextView) findViewById(R.id.txtveiw_carname);
		mTxtViewReservationTime = (TextView) findViewById(R.id.txtveiw_carname);
		mTxtViewEstimatedCost = (TextView) findViewById(R.id.txtveiw_estimated_cost);
		mTxtViewPickUpAddress = (TextView) findViewById(R.id.txtview_pickup_address);
		mTxtViewDeliveryAddress = (TextView) findViewById(R.id.txtview_delivery_address);
		mTxtViewAccessories = (TextView) findViewById(R.id.txtview_accessories);
		mTxtViewStates = (TextView) findViewById(R.id.txtview_states);

		mBtnReserve = (Button) findViewById(R.id.btn_reserve);

		/* mChkBoxPickUp = (CheckBox) findViewById(R.id.checkbox_pickup); */
		mChkBoxDelivery = (CheckBox) findViewById(R.id.checkbox_delivery);
		mChkBoxInsurance = (CheckBox) findViewById(R.id.checkbox_insurance);

		mRelativeLytEstimatedCost.setOnClickListener(this);
		mRelativeLytPickUp.setOnClickListener(this);
		mRelativeLytDelivery.setOnClickListener(this);
		mRelativeLytAccessroy.setOnClickListener(this);
		mRelativeLytStates.setOnClickListener(this);
		mBtnReserve.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		Intent intent = null;
		switch (v.getId()) {
		case R.id.relative_lyt_estimated_cost:

			intent = new Intent(this, ActivityCostBreakDown.class);
			startActivity(intent);

			break;
		case R.id.relative_lyt_pickup:
			isPickUp = true;
			intent = new Intent(this, ActivityFindCarNear.class);
			intent.putExtra("from", "ActivityReserveCar");
			startActivityForResult(intent, PICK_ADDRESS_REQUEST);
			break;

		case R.id.relative_lyt_delivery:

			isPickUp = false;
			intent = new Intent(this, ActivityFindCarNear.class);
			intent.putExtra("from", "ActivityReserveCar");
			startActivityForResult(intent, PICK_ADDRESS_REQUEST);

			break;

		case R.id.relative_lyt_acessories:

			intent = new Intent(this, ActivityAccessories.class);
			intent.putExtra("preSelected", preSelectedAccessoriesMap);
			startActivityForResult(intent, PICK_ACCESSORIES_REQUEST);
			break;

		case R.id.relative_lyt_states:

			intent = new Intent(this, ActivityStates.class);
			intent.putExtra("preSelected", preSelectedStateMap);
			startActivityForResult(intent, PICK_STATE_REQUEST);
			break;

		case R.id.btn_reserve:

			fetchMakeReservationUrl();
			break;

		default:
			break;
		}

	}

	private void fetchMakeReservationUrl() {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accessToken", Utility.getSharedPrefStringData(
				ActivityReserveCar.this,
				com.micar.utils.Constants.KEY_ACCESS_TOKEN));

		MakeReservationParser makeReservationParser = new MakeReservationParser();

		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				this, getResources().getString(R.string.please_wait), true,
				ApiDetails.HOME_URL + ApiDetails.MAKE_RESERVATOIN, paramMap,
				ApiDetails.REQUEST_TYPE.POST, this, makeReservationParser);

		Utility.execute(serverIntractorAsync);

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case PICK_ADDRESS_REQUEST:

				String address = data.getStringExtra("address");
				if (isPickUp) {
					mTxtViewPickUpAddress.setText(address);
				} else {
					mTxtViewDeliveryAddress.setText(address);
				}

			case PICK_ACCESSORIES_REQUEST:

				preSelectedAccessoriesMap = (HashMap<String, String>) data
						.getSerializableExtra("preSelected");

				setAccessories();

				break;
			case PICK_STATE_REQUEST:

				preSelectedStateMap = (HashMap<String, String>) data
						.getSerializableExtra("preSelected");
				setState();
				break;

			default:
				break;
			}
		}
	}

	private void setState() {
		
		if (preSelectedStateMap != null
				&& preSelectedStateMap.size() > 0) {

			Iterator<Entry<String, String>> it = preSelectedStateMap
					.entrySet().iterator();
			String accessories = "";
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();

				accessories = accessories + pairs.getValue() + ",";
			}

			accessories = accessories.substring(0, accessories.length() - 1);
			mTxtViewStates.setText(" " + accessories);
		} else {
			mTxtViewStates.setText("");
		}
	}

	private void setAccessories() {

		if (preSelectedAccessoriesMap != null
				&& preSelectedAccessoriesMap.size() > 0) {

			Iterator<Entry<String, String>> it = preSelectedAccessoriesMap
					.entrySet().iterator();
			String accessories = "";
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();

				accessories = accessories + pairs.getValue() + ",";
			}

			accessories = accessories.substring(0, accessories.length() - 1);
			mTxtViewAccessories.setText(" " + accessories);
		} else {
			mTxtViewAccessories.setText("");
		}

	}

	@Override
	public void onComplete(Model object) {

		if (object instanceof MakeReservationInfo) {
			MakeReservationInfo makeReservationInfo = (MakeReservationInfo) object;
			if (makeReservationInfo.getStatus() == 1) {

				Intent intent = new Intent(this, ActivityMakeReservation.class);
				intent.putExtra("makeReservation", makeReservationInfo);
				startActivity(intent);

			} else {
				FragmentAlertDialog alert = new FragmentAlertDialog(
						FragmentAlertDialog.DIALOG_TYPE.OK_ONLY,
						this.getResources().getString(R.string.error_title),
						makeReservationInfo.getMessage());
				alert.show(getSupportFragmentManager(), "Alert_Dialog");

			}
		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {

	}

}
