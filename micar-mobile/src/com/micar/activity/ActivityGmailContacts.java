package com.micar.activity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.brickred.socialauth.Contact;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.micar.adapter.ContactItem;
import com.micar.adapter.ContactItemComparator;
import com.micar.adapter.ContactItemInterface;
import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.model.MessageInfo;
import com.micar.model.Model;
import com.micar.parser.MessageParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ActivityGmailContacts extends Activity implements TextWatcher,
		OnClickListener, IAsyncCaller {

	private static final String TAG = "GmailOAuth";
	private ProgressBar progressBar;
	private String searchString; // Stores the current search query term
	private ListView mlistViewEmail;
	private EditText mEditTextSearch;
	private ImageView mImageViewCancel;

	private List<ContactItemInterface> contactList;

	List<ContactItemInterface> filterList;
	private SearchListTask curSearchTask = null;
	private Object searchLock = new Object();

	private HashSet<String> selectedHashSet = null;
	private ContactListAdapter contactListAdapter;
	List<Contact> socialContactList;
	String providerName;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gmail_oauth);
		getViewsID();
		socialContactList = (ArrayList<Contact>) getIntent()
				.getSerializableExtra("contact");
		providerName = getIntent().getStringExtra("provider");

		for (int i = 0; i < socialContactList.size(); i++) {

			String name = socialContactList.get(i).getDisplayName();
			String email = socialContactList.get(i).getEmail();

			if (name != null && name.equalsIgnoreCase("")) {
				name = email.substring(0, email.indexOf('@')).trim();
			}
			contactList.add(new ContactItem(name, email, ""));
		}
		if (contactList.size() > 0) {
			if (providerName.equalsIgnoreCase("google")) {
				Collections.sort(contactList, new ContactItemComparator());
			}

			contactListAdapter = new ContactListAdapter(
					ActivityGmailContacts.this, R.layout.gmail_list_row,
					contactList);

			mlistViewEmail.setAdapter(contactListAdapter);

		}

	}

	private void getViewsID() {

		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		mlistViewEmail = (ListView) findViewById(android.R.id.list);
		mEditTextSearch = (EditText) findViewById(R.id.editext_Search);
		mEditTextSearch.addTextChangedListener(this);
		((Button) findViewById(R.id.button_invite)).setOnClickListener(this);
		mImageViewCancel = (ImageView) findViewById(R.id.imageview_cancel);
		mImageViewCancel.setOnClickListener(this);

		filterList = new ArrayList<ContactItemInterface>();
		contactList = new ArrayList<ContactItemInterface>();
		selectedHashSet = new HashSet<String>();

	}

	private void writeFile(String text) {
		File txtFile = getTextFileName(this);
		// Log.e("txtFile", txtFile.toString());
		String response = null;

		try {

			// Log.e("text in edittext", text);
			Writer output = new BufferedWriter(new FileWriter(txtFile));
			Log.e("writing", "writing");
			output.write(text);
			Log.e("text in txtFile", txtFile.toString());
			output.close();

		} catch (java.io.IOException e) {
			e.printStackTrace();

		}
	}

	private File getTextFileName(Context context) {
		final File path = new File(Environment.getExternalStorageDirectory(),
				"MiCarNew");

		if (!path.exists()) {

			path.mkdir();

		}
		String txtFileName = SystemClock.currentThreadTimeMillis() + ".txt";
		return new File(path, txtFileName);

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	public void afterTextChanged(Editable s) {
		searchString = mEditTextSearch.getText().toString().trim()
				.toUpperCase();

		if (searchString.length() > 0) {
			mImageViewCancel.setVisibility(View.VISIBLE);
		} else {
			mImageViewCancel.setVisibility(View.GONE);
		}

		if (curSearchTask != null
				&& curSearchTask.getStatus() != AsyncTask.Status.FINISHED) {
			try {
				curSearchTask.cancel(true);
			} catch (Exception e) {
				Log.i(TAG, "Fail to cancel running search task");
			}

		}
		curSearchTask = new SearchListTask();
		curSearchTask.execute(searchString); // put it in a task so that ui is
												// not freeze

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_invite:

			if (!selectedHashSet.isEmpty()) {

				@SuppressWarnings("rawtypes")
				Iterator it = selectedHashSet.iterator();

				JSONArray jsonArray = new JSONArray();

				while (it.hasNext()) {
					String value = (String) it.next();
					JSONObject jsonObject = new JSONObject();
					String[] nameEmail = value.split(",");
					try {
						jsonObject.put("email", nameEmail[0].trim());
						jsonObject.put("name", nameEmail[1].trim());
					} catch (JSONException e) {

						e.printStackTrace();
					}
					Log.e("jsonObject", jsonObject.toString());
					jsonArray.put(jsonObject);

				}

				// if (Utility.isNetworkAvailable(ActivityGmailContacts.this)) {
				//
				// Log.e("jsonArray", jsonArray.toString());
				//
				// /* TO DO check for guest member */
				// EmailInviteAsync emailInviteAsync = new EmailInviteAsync(
				// this);
				// emailInviteAsync.execute(Utility.getSharedPrefStringData(
				// ActivityGmailContacts.this, Constants.KEY_USER_ID),
				// Utility.getSharedPrefStringData(
				// ActivityGmailContacts.this,
				// Constants.KEY_ACCESS_TOKEN), jsonArray
				// .toString());
				//
				// }

				HashMap<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("id", String.valueOf(Utility
						.getSharedPrefStringData(this, Constants.KEY_MEMBER_ID)));
				paramMap.put("accessToken", Utility.getSharedPrefStringData(
						this, Constants.KEY_ACCESS_TOKEN));
				MessageParser msgParser = new MessageParser();
				ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
						this, getResources().getString(R.string.please_wait),
						true, ApiDetails.HOME_URL + ApiDetails.REFERRALS,
						paramMap, ApiDetails.REQUEST_TYPE.POST, this, msgParser);
				Utility.execute(serverIntractorAsync);

			} else {

				Utility.showOKOnlyDialog(ActivityGmailContacts.this,
						getResources().getString(R.string.select_contact_msg),
						getResources().getString(R.string.select_contact_title))
						.show();

			}

			break;

		case R.id.imageview_cancel:

			mEditTextSearch.setText("");

			filterList.clear();
			selectedHashSet.clear();

			for (ContactItemInterface item : contactList) {
				ContactItem contact = (ContactItem) item;
				contact.setChecked(false);

			}

			ContactListAdapter adapter = new ContactListAdapter(
					ActivityGmailContacts.this, R.layout.contact_list_row,
					contactList);

			mlistViewEmail.setAdapter(adapter);

		default:
			break;
		}

	}

	private class ContactListAdapter extends ArrayAdapter<ContactItemInterface> {

		private LayoutInflater mInflater;
		private List<ContactItemInterface> contactList;
		private ImageLoader imageLoader;
		private DisplayImageOptions options;

		public ContactListAdapter(Context context, int resource,
				List<ContactItemInterface> contactList) {
			super(context, resource, contactList);

			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.contactList = contactList;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final ContactItemInterface item = this.contactList.get(position);

			ViewHolder viewHolder = null;
			if (convertView == null) {

				convertView = mInflater.inflate(R.layout.gmail_list_row, null);
				viewHolder = new ViewHolder();
				viewHolder.textViewName = (TextView) convertView
						.findViewById(R.id.textview_name);
				viewHolder.textViewEmail = (TextView) convertView
						.findViewById(R.id.textview_email);

				viewHolder.checkBoxEmail = (CheckBox) convertView
						.findViewById(R.id.checkbox_contact);
				viewHolder.checkBoxEmail
						.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								int getPosition = (Integer) buttonView.getTag();

								((ContactItem) contactList.get(getPosition))
										.setChecked(buttonView.isChecked());

								/* adding email id and name to hashset */
								if (buttonView.isChecked()) {
									selectedHashSet.add(((ContactItem) contactList
											.get(getPosition)).getEmailID()
											+ ","
											+ ((ContactItem) contactList
													.get(getPosition))
													.getContactName());

								} else {
									/* removing email id and name to hashset */
									selectedHashSet.remove(((ContactItem) contactList
											.get(getPosition)).getEmailID()
											+ ","
											+ ((ContactItem) contactList
													.get(getPosition))
													.getContactName());

								}
							}
						});
				convertView.setTag(viewHolder);
				convertView.setTag(R.id.textview_name, viewHolder.textViewName);
				convertView.setTag(R.id.textview_email,
						viewHolder.textViewEmail);

				convertView.setTag(R.id.checkbox_contact,
						viewHolder.checkBoxEmail);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.checkBoxEmail.setTag(position); // This line is
														// important.

			viewHolder.textViewName.setText(item.getItemForIndex());
			if (item instanceof ContactItem) {
				final ContactItem contactItem = (ContactItem) item;
				viewHolder.textViewEmail.setText(contactItem.getEmailID());

				viewHolder.checkBoxEmail.setChecked(contactItem.isChecked());

			}

			return convertView;
		}

		public class ViewHolder {

			TextView textViewName, textViewEmail;

			CheckBox checkBoxEmail;

		}
	}

	/* Async task to search contacts by name in the existing list */
	private class SearchListTask extends AsyncTask<String, Void, String> {

		boolean inSearchMode = false;

		@Override
		protected String doInBackground(String... params) {
			filterList.clear();

			String keyword = params[0];

			inSearchMode = (keyword.length() > 0);

			if (inSearchMode) {
				// get all the items matching this
				for (ContactItemInterface item : contactList) {
					ContactItem contact = (ContactItem) item;

					if ((contact.getContactName().toUpperCase()
							.indexOf(keyword) > -1)) {
						filterList.add(item);
					}

				}

			}
			return null;
		}

		protected void onPostExecute(String result) {

			synchronized (searchLock) {

				if (inSearchMode) {

					ContactListAdapter adapter = new ContactListAdapter(
							ActivityGmailContacts.this,
							R.layout.gmail_list_row, filterList);

					mlistViewEmail.setAdapter(adapter);
				} else {
					ContactListAdapter adapter = new ContactListAdapter(
							ActivityGmailContacts.this,
							R.layout.gmail_list_row, contactList);

					mlistViewEmail.setAdapter(adapter);
				}
			}

		}
	}

	// public void setReferralResponse(String response) {
	// try {
	// JSONObject jsonObject = new JSONObject(response);
	// if (jsonObject.getInt("status") == 1) {
	// String msg = String.format(
	// getResources().getString(R.string.invited_msg),
	// selectedHashSet.size());
	//
	// Utility.showOKOnlyDialog(ActivityGmailContacts.this, msg,
	// getResources().getString(R.string.invited_title))
	// .show();
	// selectedHashSet.clear();
	//
	// for (ContactItemInterface item : contactList) {
	// ContactItem contact = (ContactItem) item;
	// contact.setChecked(false);
	// }
	//
	// ContactListAdapter adapter = new ContactListAdapter(
	// ActivityGmailContacts.this, R.layout.contact_list_row,
	// contactList);
	//
	// mlistViewEmail.setAdapter(adapter);
	// } else {
	//
	// String msg = String.format(getResources().getString(
	// R.string.error_msg));
	//
	// Utility.showOKOnlyDialog(ActivityGmailContacts.this, msg,
	// getResources().getString(R.string.error_title)).show();
	//
	// }
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// String msg = String.format(getResources().getString(
	// R.string.error_msg));
	//
	// Utility.showOKOnlyDialog(ActivityGmailContacts.this, msg,
	// getResources().getString(R.string.error_title)).show();
	// }
	//
	// }

	private void setReferralResponse(MessageInfo msgInfo) {

		if (msgInfo.getStatus() == 1) {
			String msg = String.format(
					getResources().getString(R.string.invited_msg),
					selectedHashSet.size());

			Utility.showOKOnlyDialog(ActivityGmailContacts.this, msg,
					getResources().getString(R.string.invited_title)).show();
			selectedHashSet.clear();

			for (ContactItemInterface item : contactList) {
				ContactItem contact = (ContactItem) item;
				contact.setChecked(false);
			}

			ContactListAdapter adapter = new ContactListAdapter(
					ActivityGmailContacts.this, R.layout.contact_list_row,
					contactList);

			mlistViewEmail.setAdapter(adapter);
		} else {

			String msg = String.format(getResources().getString(
					R.string.error_msg));

			Utility.showOKOnlyDialog(ActivityGmailContacts.this, msg,
					getResources().getString(R.string.error_title)).show();

		}
	}

	@Override
	public void onComplete(Model object) {
		if (object instanceof MessageInfo) {
			MessageInfo msgInfo = (MessageInfo) object;
			setReferralResponse(msgInfo);
		}

	}

	@Override
	public void onComplete(ArrayList<Model> object) {
		// TODO Auto-generated method stub

	}

}