package com.micar.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.micar.activity.R;
import com.micar.model.ReservationCancelCharge;

public class CancellationChargeAdapter extends BaseAdapter {
	private List<ReservationCancelCharge> invitedList;
	private ViewHolder holder;
	private LayoutInflater mInflater;

	private Context context;

	private View view = null;

	public CancellationChargeAdapter(Context context,
			List<ReservationCancelCharge> invitedList) {
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

			view = mInflater.inflate(R.layout.cancel_charge_row, null, false);
			holder.textViewhrsBeforeReservation = (TextView) view
					.findViewById(R.id.txtView_hrs_before_reservation);
			holder.textViewMaxHrs = (TextView) view
					.findViewById(R.id.txtView_max_hrs_charge);
			holder.textViewChargePercentage = (TextView) view
					.findViewById(R.id.txtView_charge_percentage);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.textViewhrsBeforeReservation.setText("Hours before reservation:"
				+ invitedList.get(arg0).getHoursBeforeReservation());
		holder.textViewMaxHrs.setText("Max hours to be charged for:"
				+ invitedList.get(arg0).getMaxHrsToBeChargeFor());
		holder.textViewChargePercentage.setText("Charge percentage:"
				+ invitedList.get(arg0).getChargePercentage());

		return view;
	}

	public class ViewHolder {

		public TextView textViewhrsBeforeReservation, textViewMaxHrs,
				textViewChargePercentage;

	}
}