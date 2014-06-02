package com.micar.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.micar.activity.R;
import com.micar.component.Slider;

public class SearchedCarsAdapter extends BaseAdapter {
	private List<String> invitedList;
	private ViewHolder holder;
	private LayoutInflater mInflater;

	private Context context;
	private File cacheDir;
	private View view = null;

	public SearchedCarsAdapter(Context context, List<String> invitedList) {
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

			view = mInflater.inflate(R.layout.car_row, null, false);
			// holder.textViewEmail = (TextView) view
			// .findViewById(R.id.textview_invited_name);
			// holder.textViewStatus = (TextView) view
			// .findViewById(R.id.textview_status);
			holder.linearLytSlider = (LinearLayout) view
					.findViewById(R.id.linear_slider);
			holder.linearLytSliderTime=(LinearLayout) view.findViewById(R.id.linear_slider_time);

			holder.linearLytSlider.setTag(R.id.linear_slider);
			holder.linearLytSliderTime.setTag(R.id.linear_slider_time);
			
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		// holder.textViewEmail.setText(invitedList.get(arg0));

		Slider slider = new Slider(holder.linearLytSlider,holder.linearLytSliderTime, context);
		slider.CreateSlider();

		return view;
	}

	public class ViewHolder {

		private TextView textViewEmail, textViewStatus;
		private LinearLayout linearLytSlider ,linearLytSliderTime;

	}
}