package com.micar.adapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactListAdapter extends ArrayAdapter<ContactItemInterface> {

	private int resource; // store the resource layout id for 1 row
	private boolean inSearchMode = false;

	public ContactListAdapter(Context _context, int _resource,
			List<ContactItemInterface> _items) {
		super(_context, _resource, _items);
		resource = _resource;

		// need to sort the items array first, then pass it to the indexer
		Collections.sort(_items, new ContactItemComparator());

	}

	// this should be override by subclass if necessary
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup parentView;

		ContactItemInterface item = getItem(position);

		// Log.i("ContactListAdapter", "item: " + item.getItemForIndex());

		if (convertView == null) {
			parentView = new LinearLayout(getContext()); // Assumption: the
															// resource parent
															// id is a linear
															// layout
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					inflater);
			vi.inflate(resource, parentView, true);
		} else {
			parentView = (LinearLayout) convertView;
		}

		return parentView;

	}

	public boolean isInSearchMode() {
		return inSearchMode;
	}

	public void setInSearchMode(boolean inSearchMode) {
		this.inSearchMode = inSearchMode;
	}

}
