package com.micar.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.micar.activity.R;
import com.micar.model.AddressInfo;
import com.micar.model.StationInfo;

public class AddressAdapter extends BaseAdapter {
	private List<AddressInfo> addressList;
	private ViewHolder holder;
	private LayoutInflater mInflater;

	private Context context;

	private View view = null;

	public AddressAdapter(Context context, List<AddressInfo> addressList) {
		this.addressList = addressList;

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.context = context;
	}

	@Override
	public int getCount() {
		return addressList.size();
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

			view = mInflater.inflate(R.layout.station_row, null, false);
			holder.textViewSationName = (TextView) view
					.findViewById(R.id.txtView_station_name);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.textViewSationName.setText(addressList.get(position).getName());

		return view;
	}

	public class ViewHolder {

		public TextView textViewSationName;

	}
}