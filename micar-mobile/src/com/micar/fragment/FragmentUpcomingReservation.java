package com.micar.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.micar.activity.ActivityReservationDetails;
import com.micar.activity.R;
import com.micar.adapter.UpcomingReservationAdapter;
import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.model.Model;
import com.micar.model.UpcomingReservation;
import com.micar.parser.UpcomingReservationParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;

public class FragmentUpcomingReservation extends Fragment implements
		IAsyncCaller {

	private ListView mListViewReservation;
	private List<UpcomingReservation> reservationList;
	private TextView mTextViewNoReservation;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(
				R.layout.fragment_upcoming_reservation, container, false);
		getViewsID(rootView);

		return rootView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		fetchPendingReservation();
	}

	private void fetchPendingReservation() {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accessToken", Utility.getSharedPrefStringData(
				getActivity(), Constants.KEY_ACCESS_TOKEN));
		paramMap.put("memberId", Utility.getSharedPrefStringData(getActivity(),
				Constants.KEY_MEMBER_ID));

		UpcomingReservationParser parser = new UpcomingReservationParser();
		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				getActivity(), getResources().getString(R.string.please_wait),
				true, ApiDetails.HOME_URL
						+ ApiDetails.FETCH_PENDING_RESERVATION, paramMap,
				ApiDetails.REQUEST_TYPE.POST, this, parser);

		Utility.execute(serverIntractorAsync);

	}

	private void getViewsID(View rootView) {

		mListViewReservation = (ListView) rootView
				.findViewById(R.id.listview_upcoming_reservation);
		mTextViewNoReservation = (TextView) rootView
				.findViewById(R.id.textview_no_reservation);

		mListViewReservation.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				reservationDetails(position);

			}

		});

	}

	private void reservationDetails(int position) {

		Intent intent = new Intent(getActivity(),
				ActivityReservationDetails.class);
		intent.putExtra("reservationId", reservationList.get(position)
				.getReservationId());
		startActivity(intent);

	}

	@Override
	public void onComplete(Model object) {

		if (object instanceof UpcomingReservation) {
			UpcomingReservation upcomingReservation = (UpcomingReservation) object;

			if (upcomingReservation.getStatus() == 1) {
				if (upcomingReservation.getReservationList() != null
						&& upcomingReservation.getReservationList().size() > 0) {

					reservationList = upcomingReservation.getReservationList();
					UpcomingReservationAdapter reservationAdapter = new UpcomingReservationAdapter(
							getActivity(), reservationList);
					mListViewReservation.setAdapter(reservationAdapter);

				} else {
					mListViewReservation.setAdapter(null);
					mTextViewNoReservation.setVisibility(View.VISIBLE);
					mListViewReservation.setVisibility(View.GONE);
				}
			} else {

				String message = upcomingReservation.getMessage();

				if (message != null && !message.equalsIgnoreCase("")) {
					FragmentAlertDialog alert = new FragmentAlertDialog(
							FragmentAlertDialog.DIALOG_TYPE.OK_ONLY, this
									.getResources().getString(
											R.string.error_title), message);
					alert.show(getActivity().getSupportFragmentManager(),
							"Alert_Dialog");
				}
				mListViewReservation.setAdapter(null);
				mTextViewNoReservation.setVisibility(View.VISIBLE);
				mListViewReservation.setVisibility(View.GONE);
			}
		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {

	}

}