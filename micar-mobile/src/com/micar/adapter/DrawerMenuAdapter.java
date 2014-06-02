package com.micar.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.micar.activity.R;
import com.micar.model.MenuInfo;

public class DrawerMenuAdapter extends BaseAdapter {

	private List<MenuInfo> menuList;
	private ViewHolder holder;
	private LayoutInflater mInflater;

	private Context context;

	private View view = null;

	public DrawerMenuAdapter(Context context, List<MenuInfo> menuList) {
		this.menuList = menuList;

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.context = context;
	}

	@Override
	public int getCount() {
		return menuList.size();
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

		int type = getItemViewType(position);

		if (type == 1) {
			if (view == null) {
				view = mInflater.inflate(R.layout.drawer_profile_row, null);
				holder = new ViewHolder();
				holder.textViewName = (TextView) view
						.findViewById(R.id.textView_name);
				holder.textViewMemberType = (TextView) view
						.findViewById(R.id.textView_memeberType);
				holder.imageViewIcon = (ImageView) view
						.findViewById(R.id.imageview_profile);

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			// holder.tvTitle.setText(cd.getTitle());

			holder.textViewName.setText(menuList.get(position)
					.getMenuItemName());

			holder.textViewMemberType.setText(menuList.get(position)
					.getMemberType());

		} else {
			if (view == null) {
				holder = new ViewHolder();

				view = mInflater
						.inflate(R.layout.drawer_list_item, null, false);
				holder.textViewName = (TextView) view.findViewById(R.id.text1);
				holder.imageViewIcon = (ImageView) view
						.findViewById(R.id.imageView);

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.textViewName.setText(menuList.get(position)
					.getMenuItemName());
			holder.imageViewIcon.setBackgroundResource(menuList.get(position)
					.getIcon());

		}

		return view;

	}

	public class ViewHolder {

		public TextView textViewName, textViewMemberType;
		public ImageView imageViewIcon;
	}

	@Override
	public int getItemViewType(int position) {

		return menuList.get(position).isUserProfile() ? 1 : 0;
	}

	public int getViewTypeCount() {
		return 2;
	}
}