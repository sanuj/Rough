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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.micar.activity.ActivityActiveReservationDetail;
import com.micar.activity.R;
import com.micar.adapter.ActiveReservationAdapter;
import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.model.ActiveReservation;
import com.micar.model.Model;
import com.micar.parser.ActiveReservationParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;

public class FragmentActiveReservation extends Fragment implements IAsyncCaller {

	private ListView mListViewActiveReservation;
	private List<ActiveReservation> reservationList;
	private TextView mTxtViewNoReservation;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_active_reservation,
				container, false);
		getViewsId(rootView);
		fetchActiveReservation();

		return rootView;
	}

	private void fetchActiveReservation() {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("accessToken", Utility.getSharedPrefStringData(
				getActivity(), Constants.KEY_ACCESS_TOKEN));
		paramMap.put("memberId", Utility.getSharedPrefStringData(getActivity(),
				Constants.KEY_MEMBER_ID));

		ActiveReservationParser parser = new ActiveReservationParser();
		ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
				getActivity(), getResources().getString(R.string.please_wait),
				true,
				ApiDetails.HOME_URL + ApiDetails.FETCH_ACTIVE_RESERVATION,
				paramMap, ApiDetails.REQUEST_TYPE.POST, this, parser);

		Utility.execute(serverIntractorAsync);

	}

	private void getViewsId(View rootView) {

		mTxtViewNoReservation = (TextView) rootView
				.findViewById(R.id.textview_no_reservation);
		mListViewActiveReservation = (ListView) rootView
				.findViewById(R.id.listview_active_reservation);

		mListViewActiveReservation
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						showDetails(reservationList.get(position));

					}

				});

	}

	private void showDetails(ActiveReservation activeReservation) {

		Intent intent = new Intent(getActivity(),
				ActivityActiveReservationDetail.class);
		intent.putExtra("activeReservation", activeReservation);
		startActivity(intent);

	}

	@Override
	public void onComplete(Model object) {

		if (object instanceof ActiveReservation) {
			ActiveReservation activeReservation = (ActiveReservation) object;

			if (activeReservation.getActiveList() != null
					&& activeReservation.getActiveList().size() > 0) {

				reservationList = activeReservation.getActiveList();
				Log.i("size of active list", reservationList.size() + "");

				ActiveReservationAdapter activeReservationAdapter = new ActiveReservationAdapter(
						getActivity(), reservationList);
				mListViewActiveReservation.setAdapter(activeReservationAdapter);

				mTxtViewNoReservation.setVisibility(View.GONE);
				mListViewActiveReservation.setVisibility(View.VISIBLE);

			} else {
				mTxtViewNoReservation.setVisibility(View.VISIBLE);
				mListViewActiveReservation.setVisibility(View.GONE);
			}
		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {
		// TODO Auto-generated method stub

	}

}
