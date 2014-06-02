package com.micar.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.micar.activity.R;
import com.micar.adapter.AddressAdapter.ViewHolder;

public class EstimatedCostAdapter extends BaseAdapter
{
	private List<String> costList;
	private ViewHolder holder;
	private LayoutInflater mInflater;

	private Context context;
	private File cacheDir;
	private View view = null;

	public EstimatedCostAdapter(Context context, List<String> costList)
	{
		this.costList = costList;

		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.context = context;
	}

	@Override
	public int getCount()
	{
		return costList.size();
	}

	@Override
	public Object getItem(int arg0)
	{
		return null;
	}

	@Override
	public long getItemId(int arg0)
	{
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2)
	{

		view = convertView;

		if (view == null)
		{
			holder = new ViewHolder();

			view = mInflater.inflate(R.layout.cost_row, null, false);
//			holder.textViewSationName = (TextView) view.findViewById(R.id.txtView_station_name);
			
			

			view.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) view.getTag();
		}

//		holder.textViewSationName.setText(costList.get(arg0).getAddressName());

		

		return view;
	}

	public class ViewHolder
	{
		
		public TextView textViewSationName;
	
	}
}