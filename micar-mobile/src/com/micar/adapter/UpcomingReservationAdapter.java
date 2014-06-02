package com.micar.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.micar.activity.R;
import com.micar.model.UpcomingReservation;

public class UpcomingReservationAdapter extends BaseAdapter {
	private List<UpcomingReservation> reservationList;
	private ViewHolder holder;
	private LayoutInflater mInflater;

	private Context context;

	private View view = null;

	public UpcomingReservationAdapter(Context context,
			List<UpcomingReservation> reservationList) {
		this.reservationList = reservationList;

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.context = context;
	}

	@Override
	public int getCount() {
		return reservationList.size();
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
	public View getView(int position, View convertView, ViewGroup arg2) {

		view = convertView;

		if (view == null) {
			holder = new ViewHolder();

			view = mInflater.inflate(R.layout.reservation_row, null, false);
			holder.txtViewId = (TextView) view
					.findViewById(R.id.txtView_reservation_time);
			holder.txtViewStartTime = (TextView) view
					.findViewById(R.id.txtView_reservation_day);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.txtViewId.setText(reservationList.get(position)
				.getReservationId());
		holder.txtViewStartTime.setText(reservationList.get(position)
				.getStartTime());

		return view;
	}

	public class ViewHolder {

		public TextView txtViewId, txtViewStartTime;

	}
}
