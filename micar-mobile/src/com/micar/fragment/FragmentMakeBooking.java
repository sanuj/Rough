package com.micar.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.micar.activity.ActivityFindCarNear;
import com.micar.activity.ActivityMicarHome;
import com.micar.activity.ActivitySearchedCars;
import com.micar.activity.R;
import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.fragment.FragmentDatePicker.ONDateSelectedListerner;
import com.micar.fragment.FragmentTimePicker.OnTimeSelectedListener;
import com.micar.model.CityInfo;
import com.micar.model.LoginInfo;
import com.micar.model.MemberDetail;
import com.micar.model.Model;
import com.micar.model.VehicleCityDetail;
import com.micar.model.VehicleInfo;
import com.micar.parser.VehicleCityParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Utility;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class FragmentMakeBooking extends Fragment implements OnClickListener,
		ONDateSelectedListerner, OnTimeSelectedListener, IAsyncCaller {

	private Spinner mSpinnerCity, mSpinnerCarType;
	private TextView mTxtViewCity, mTxtViewCarType, mTxtViewFindCarNear,
			mTxtViewToDate, mTxtViewToTime, mTxtViewFromDate, mTxtViewFromTime;
	private Button mBtnFindCar;
	private RelativeLayout mRelativeLytFindCarNear, mRelativeLytFromDate,
			mRelativeLytFromTime, mRelativeLytToDate, mRelativeLytToTime;
	private CheckBox mCheckBoxPickUp, mCheckBoxDelivery;
	private ImageView mImageViewFromDate, mImageViewFromTime, mImageViewToDate,
			mImageViewToTime;
	private RadioGroup mRadioGroupMemberType;
	private RadioButton mRadioBtnMyself, mRadioBtnGuest, mRadioBtnDriver;

	private List<VehicleInfo> mVehicleList;
	private List<CityInfo> mCityList;
	private List<MemberDetail> mMemberList;

	private String mCityId, mVehicleId, mMemberType;

	private int mSelectedFromYear;
	private int mSelectedFromMonth;
	private int mSelectedFromDay;
	private boolean isFromDate;
	private boolean isFromTime;
	private Calendar mCalendarStartDate, mCalendarEndDate;
	private String mSelectedCity;

	private LoginInfo loginInfo;

	public FragmentMakeBooking() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mCalendarStartDate = Calendar.getInstance();

		loginInfo = ActivityMicarHome.getLoginInfo();

	}

	private void fetchReservationDetails(String accessToken, String memberId) {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accessToken", accessToken);
		paramMap.put("memberId", memberId);

		VehicleCityParser detailsParser = new VehicleCityParser();
		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				getActivity(), getResources().getString(R.string.please_wait),
				true, ApiDetails.HOME_URL
						+ ApiDetails.FETCH_VEHICLE_AND_CITY_DETAILS, paramMap,
				ApiDetails.REQUEST_TYPE.POST, this, detailsParser);

		Utility.execute(serverIntractorAsync);

	}

	@Override
	public void onDestroy() {

		super.onDestroy();
	}

	@Override
	public void onPause() {

		super.onPause();
	}

	@Override
	public void onResume() {

		super.onResume();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_make_booking,
				container, false);

		getViewsID(rootView);

		setStartDateTime();
		setEndDateTime();

		if (loginInfo != null) {
			fetchReservationDetails(loginInfo.getAccessToken(),
					loginInfo.getMemberId());

			if (loginInfo.getFavAddress() != null) {
				setCarNear(loginInfo.getFavAddress());

			}
		}

		return rootView;
	}

	private void setCarNear(String defaultFavAddress) {

		mTxtViewFindCarNear.setText(defaultFavAddress);
	}

	private void setEndDateTime() {

		mCalendarEndDate = Calendar.getInstance();
		mCalendarEndDate.setTimeInMillis(mCalendarStartDate.getTimeInMillis()
				+ TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS));

		try {
			mTxtViewToDate.setText(Utility
					.getDateInFormatFromMilisecond(mCalendarEndDate
							.getTimeInMillis()));
			mTxtViewToTime.setText(Utility
					.getTimeInFormatFromMS(mCalendarEndDate.getTimeInMillis()));
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void setStartDateTime() {

		mCalendarStartDate.setTimeInMillis(mCalendarStartDate.getTimeInMillis()
				+ TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS));

		int unroundedMinutes = mCalendarStartDate.get(Calendar.MINUTE);
		int mod = unroundedMinutes % 5;

		if (mod != 0) {

			unroundedMinutes = unroundedMinutes + 5 - mod;
			Log.e("unroundedMinutes, mod ", unroundedMinutes + " " + mod);
		}

		mCalendarStartDate.set(Calendar.MINUTE, unroundedMinutes);

		try {
			mTxtViewFromDate.setText(Utility
					.getDateInFormatFromMilisecond(mCalendarStartDate
							.getTimeInMillis()));
			mTxtViewFromTime
					.setText(Utility.getTimeInFormatFromMS(mCalendarStartDate
							.getTimeInMillis()));
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void getViewsID(View rootView) {
		mSpinnerCity = (Spinner) rootView.findViewById(R.id.spinner_city);
		mSpinnerCarType = (Spinner) rootView
				.findViewById(R.id.spinner_car_type);
		mTxtViewCity = (TextView) rootView
				.findViewById(R.id.textView_city_name);
		mTxtViewCarType = (TextView) rootView
				.findViewById(R.id.textview_car_type);
		mTxtViewFindCarNear = (TextView) rootView
				.findViewById(R.id.textView_find_car_near);
		mTxtViewToDate = (TextView) rootView
				.findViewById(R.id.textView_to_date);
		mTxtViewToTime = (TextView) rootView
				.findViewById(R.id.textView_to_time);
		mTxtViewFromDate = (TextView) rootView
				.findViewById(R.id.textView_from_date);
		mTxtViewFromTime = (TextView) rootView
				.findViewById(R.id.textView_from_time);
		mBtnFindCar = (Button) rootView.findViewById(R.id.btn_find_car);

		mRelativeLytFindCarNear = (RelativeLayout) rootView
				.findViewById(R.id.relative_lyt_find_car_near);

		mRelativeLytFromDate = (RelativeLayout) rootView
				.findViewById(R.id.relative_layout_from_date);
		mRelativeLytFromTime = (RelativeLayout) rootView
				.findViewById(R.id.relative_from_time);
		mRelativeLytToDate = (RelativeLayout) rootView
				.findViewById(R.id.relative_layout_to_date);
		mRelativeLytToTime = (RelativeLayout) rootView
				.findViewById(R.id.relative_layout_to_time);

		mCheckBoxPickUp = (CheckBox) rootView.findViewById(R.id.checkbox_picup);

		mCheckBoxDelivery = (CheckBox) rootView
				.findViewById(R.id.checkbox_delivery);

		mImageViewFromDate = (ImageView) rootView
				.findViewById(R.id.imageview_from_date);

		mImageViewFromTime = (ImageView) rootView
				.findViewById(R.id.imageview_from_time);
		mImageViewToDate = (ImageView) rootView
				.findViewById(R.id.imageview_to_date);
		mImageViewToTime = (ImageView) rootView
				.findViewById(R.id.imageview_to_time);

		mRadioGroupMemberType = (RadioGroup) rootView
				.findViewById(R.id.radio_member_type);

		mRadioBtnMyself = (RadioButton) rootView
				.findViewById(R.id.radio_btn_myself);
		mRadioBtnGuest = (RadioButton) rootView
				.findViewById(R.id.radio_btn_guest);
		mRadioBtnDriver = (RadioButton) rootView
				.findViewById(R.id.radio_btn_driver);

		mRelativeLytFromDate.setOnClickListener(this);
		mRelativeLytFromTime.setOnClickListener(this);
		mRelativeLytToDate.setOnClickListener(this);
		mRelativeLytToTime.setOnClickListener(this);
		mRelativeLytFindCarNear.setOnClickListener(this);
		mBtnFindCar.setOnClickListener(this);

		mRadioGroupMemberType
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {

						switch (checkedId) {
						case R.id.radio_btn_myself:

							mMemberType = (String) mRadioBtnMyself.getTag();

							break;

						case R.id.radio_btn_guest:
							mMemberType = (String) mRadioBtnGuest.getTag();

							break;

						case R.id.radio_btn_driver:

							mMemberType = (String) mRadioBtnDriver.getTag();

							break;

						default:
							break;
						}

					}
				});

	}

	// add items into spinner dynamically
	public void addItemInVehicleType(String[] mCarType) {

		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(
				getActivity(), R.layout.spinner_transparent_txtview, mCarType);
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinnerCarType.setAdapter(adapter_state);

		mSpinnerCarType
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {

						mTxtViewCarType
								.setText(mVehicleList.get(pos).getName());
						mVehicleId = mVehicleList.get(pos).getId();

					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
	}

	private void addItemInCity(String[] cities) {
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(
				getActivity(), R.layout.spinner_transparent_txtview, cities);
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinnerCity.setAdapter(adapter_state);

		mSpinnerCity
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {

						mTxtViewCity.setText(mCityList.get(pos).getName());
						mCityId = mCityList.get(pos).getId();
						mSelectedCity = mCityList.get(pos).getName();

					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;

		switch (v.getId()) {

		case R.id.relative_layout_from_date:

			isFromDate = true;
			showDatePickerDialog();
			break;
		case R.id.relative_from_time:
			isFromTime = true;
			showTimePickerDialog(mCalendarStartDate);

			break;
		case R.id.relative_layout_to_date:
			isFromDate = false;
			showDatePickerDialog();
			break;
		case R.id.relative_layout_to_time:
			isFromTime = false;
			showTimePickerDialog(mCalendarEndDate);
			break;

		case R.id.relative_lyt_find_car_near:

			intent = new Intent(getActivity(), ActivityFindCarNear.class);
			intent.putExtra("city", mSelectedCity);
			intent.putExtra("cityId", mCityId);
			intent.putExtra("from", "FragmentMakeBooking");
			startActivity(intent);

			break;

		case R.id.btn_find_car:

			intent = new Intent(getActivity(), ActivitySearchedCars.class);
			startActivity(intent);

			break;

		default:
			break;
		}

	}

	public void showTimePickerDialog(Calendar calendar) {
		DialogFragment newFragment = new FragmentTimePicker(this, calendar);
		newFragment.show(getActivity().getSupportFragmentManager(),
				"timePicker");
	}

	public void showDatePickerDialog() {
		DialogFragment newFragment = new FragmentDatePicker(this);
		newFragment.show(getActivity().getSupportFragmentManager(),
				"datePicker");

	}

	@Override
	public void onDateSelect(int year, int month, int day) {

		if (isFromDate) {

			Calendar selectedDate = Calendar.getInstance();
			selectedDate.set(year, month, day);
			if (selectedDate.getTimeInMillis() >= mCalendarStartDate
					.getTimeInMillis()) {

				mSelectedFromYear = year;
				mSelectedFromMonth = month;
				mSelectedFromDay = day;
				mTxtViewFromDate.setText(new StringBuilder().append(day)
						.append("/").append(month + 1).append("/").append(year)
						.append(" "));
			} else {

				FragmentAlertDialog alert = new FragmentAlertDialog(
						FragmentAlertDialog.DIALOG_TYPE.OK_ONLY, getActivity()
								.getResources().getString(
										R.string.invalid_start_time_title),
						getActivity().getResources().getString(
								R.string.invalid_start_time_msg));
				alert.show(getActivity().getSupportFragmentManager(),
						"Alert_Dialog");
			}

		} else {
			mTxtViewToDate.setText(new StringBuilder().append(day).append("/")
					.append(month + 1).append("/").append(year).append(" "));

		}

	}

	@Override
	public void onTimeSelect(int hourOfDay, int minute) {

		if (isFromTime) {

			Calendar selectedDate = Calendar.getInstance();

			selectedDate.set(selectedDate.get(Calendar.YEAR),
					selectedDate.get(Calendar.MONTH),
					selectedDate.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);

			// if (selectedDate.getTimeInMillis() >= mCalendarStartDate
			// .getTimeInMillis()) {
			mTxtViewFromTime.setText(new StringBuilder().append(hourOfDay)
					.append(":").append(minute));
			// } else {
			//
			// FragmentAlertDialog alert = new FragmentAlertDialog(
			// FragmentAlertDialog.DIALOG_TYPE.OK_ONLY, getActivity()
			// .getResources().getString(
			// R.string.invalid_start_time_title),
			// getActivity().getResources().getString(
			// R.string.invalid_start_time_msg));
			// alert.show(getActivity().getSupportFragmentManager(),
			// "Alert_Dialog");
			// }

		} else {
			mTxtViewToTime.setText(new StringBuilder().append(hourOfDay)
					.append(":").append(minute));
		}

	}

	@Override
	public void onComplete(Model object) {

		if (object instanceof VehicleCityDetail) {
			VehicleCityDetail detailsInfo = (VehicleCityDetail) object;
			if (detailsInfo.getVehicleList() != null
					&& detailsInfo.getVehicleList().size() > 0) {

				mVehicleList = detailsInfo.getVehicleList();

				String[] cars = new String[detailsInfo.getVehicleList().size()];

				for (int i = 0; i < detailsInfo.getVehicleList().size(); i++) {
					String name = mVehicleList.get(i).getName();
					cars[i] = name;

				}

				addItemInVehicleType(cars);

			}

			if (detailsInfo.getCityList() != null
					&& detailsInfo.getCityList().size() > 0) {

				mCityList = detailsInfo.getCityList();

				String[] cities = new String[detailsInfo.getCityList().size()];

				for (int i = 0; i < detailsInfo.getCityList().size(); i++) {
					String name = mCityList.get(i).getName();
					cities[i] = name;

				}

				addItemInCity(cities);

			}

			if (detailsInfo.getMemberList() != null
					&& detailsInfo.getMemberList().size() > 0) {
				mMemberList = detailsInfo.getMemberList();
				showMemberType();
			}

		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {

	}

	private void showMemberType() {

		for (int i = 0; i < mMemberList.size(); i++) {
			String name = mMemberList.get(i).getName();
			String memberType = mMemberList.get(i).getType();

			if (name != null) {

				if (name.equalsIgnoreCase("Myself")) {
					mRadioBtnMyself.setVisibility(View.VISIBLE);
					mRadioBtnMyself.setText(name);
					mRadioBtnMyself.setTag(memberType);

				} else {
					mRadioBtnMyself.setVisibility(View.GONE);
				}
				if (name.equalsIgnoreCase("Guest")) {
					mRadioBtnGuest.setVisibility(View.VISIBLE);
					mRadioBtnGuest.setText(name);
					mRadioBtnGuest.setTag(memberType);

				} else {
					mRadioBtnGuest.setVisibility(View.GONE);
				}
				if (name.equalsIgnoreCase("Driver")) {
					mRadioBtnDriver.setVisibility(View.VISIBLE);
					mRadioBtnDriver.setText(name);
					mRadioBtnDriver.setTag(memberType);
				} else {
					mRadioBtnDriver.setVisibility(View.GONE);
				}

			}

		}

	}

}