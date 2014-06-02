package com.micar.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.micar.activity.ActivityHelp;
import com.micar.activity.ActivityMiPoints;
import com.micar.activity.ActivityOutStandingPayment;
import com.micar.activity.ActivityPreBoughtHrs;
import com.micar.activity.ActivityReferral;
import com.micar.activity.R;

public class FragmentMyAccount extends Fragment implements OnClickListener {

	private ListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_my_account,
				container, false);
		getViewsID(rootView);

		return rootView;
	}

	private void getViewsID(View rootView) {

		ArrayList<String> mListMyAccount = new ArrayList<String>();
		mListMyAccount.add("Referral");
		mListMyAccount.add("Pre Bought Hours");
		mListMyAccount.add("Outstanding Payment");
		mListMyAccount.add("Mi Points");
		mListMyAccount.add("Help");

		mListView = (ListView) rootView.findViewById(R.id.listview_my_account);
		MyAccountAdapter myAccountAdapter = new MyAccountAdapter(getActivity(),
				mListMyAccount);
		mListView.setAdapter(myAccountAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Log.i("position", position + "");

			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				selectItem(position);

			}

		});

	}

	private void selectItem(int position) {

		Intent intent = null;
		switch (position) {
		case 0:
			intent = new Intent(getActivity(), ActivityReferral.class);

			break;
		case 1:

			intent = new Intent(getActivity(), ActivityPreBoughtHrs.class);

			break;
		case 2:
			intent = new Intent(getActivity(), ActivityOutStandingPayment.class);

			break;
		case 3:
			intent = new Intent(getActivity(), ActivityMiPoints.class);

			break;
		case 4:
			intent = new Intent(getActivity(), ActivityHelp.class);

			break;

		default:
			break;
		}
		if (intent != null) {
			startActivity(intent);
		}

	}

	@Override
	public void onClick(View v) {// TODO Auto-generated method stub

	}

	private class MyAccountAdapter extends BaseAdapter {
		private List<String> invitedList;
		private ViewHolder holder;
		private LayoutInflater mInflater;

		private Context context;

		private View view = null;

		public MyAccountAdapter(Context context, List<String> invitedList) {
			this.invitedList = invitedList;

			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			this.context = context;
		}

		@Override
		public int getCount() {
			return invitedList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View convertView, ViewGroup arg2) {

			view = convertView;

			if (view == null) {
				holder = new ViewHolder();

				view = mInflater.inflate(R.layout.account_row, null, false);
				holder.textViewSationName = (TextView) view
						.findViewById(R.id.txtView_station_name);

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.textViewSationName.setText(invitedList.get(arg0));

			return view;
		}

		public class ViewHolder {

			public TextView textViewSationName;

		}
	}
}
