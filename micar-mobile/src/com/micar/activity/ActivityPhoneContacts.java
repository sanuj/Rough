package com.micar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
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
import com.micar.adapter.ContactItemInterface;
import com.micar.asynctask.IAsyncCaller;
import com.micar.asynctask.ServerIntractorAsync;
import com.micar.model.MessageInfo;
import com.micar.model.Model;
import com.micar.parser.MessageParser;
import com.micar.utils.ApiDetails;
import com.micar.utils.Constants;
import com.micar.utils.Utility;
import com.micar.utils.ValidateInputFields;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ActivityPhoneContacts extends Activity implements TextWatcher,
		OnClickListener, IAsyncCaller {

	
	private static final String TAG = "ActivityPhoneContacts";

	private String searchString; // Stores the current search query term
	private ListView mlistViewEmail;
	private EditText mEditTextSearch;
	private ImageView mImageViewCancel;
	private ProgressBar progressBar;

	private List<ContactItemInterface> contactList;

	List<ContactItemInterface> filterList;
	private SearchListTask curSearchTask = null;
	private Object searchLock = new Object();

	private HashSet<String> selectedHashSet = null;
	private ContactListAdapter contactListAdapter;
	private FetchContactsAsync fetchEmailsContactsAsync = null;
	private ContentResolver contentResolver;

	public ImageLoader imageLoader;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (fetchEmailsContactsAsync != null
				&& fetchEmailsContactsAsync.getStatus() == Status.RUNNING) {
			fetchEmailsContactsAsync.cancel(true);
		}

		if (imageLoader != null) {
			imageLoader.destroy();
		}

	}

	@Override
	protected void onStop() {
		super.onStop();
		if (imageLoader != null)
			imageLoader.stop();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_phone_contacts);
		contentResolver = getContentResolver();
		getViewsID();
		initializeImageLoader();
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
		fetchEmailsContactsAsync = new FetchContactsAsync();
		fetchEmailsContactsAsync.execute();

	}

	private void initializeImageLoader() {

		if (imageLoader != null) {
			imageLoader.destroy();
		}
		imageLoader = ImageLoader.getInstance();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPoolSize(1).threadPriority(1)
				.memoryCacheSize(1500000) // 1.5 Mb
				.discCacheSize(50000000) // 50 Mb
				.denyCacheImageMultipleSizesInMemory().build();
		imageLoader.init(config);

	}

	private class FetchContactsAsync extends AsyncTask<String, Void, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Integer doInBackground(String... params) {

			if (Build.VERSION.SDK_INT == Build.VERSION_CODES.GINGERBREAD
					|| Build.VERSION.SDK_INT == Build.VERSION_CODES.GINGERBREAD_MR1) {
				getContactsBelowAPI11(this);

			} else {

				getNameEmailDetails(this);
			}

			return 1;
		}

		protected void onPostExecute(Integer result) {

			if (!this.isCancelled()) {
				progressBar.setVisibility(View.GONE);
				if (contactList.size() > 0) {

					contactListAdapter = new ContactListAdapter(
							ActivityPhoneContacts.this,
							R.layout.contact_list_row, contactList, imageLoader); // AcitivityContact

					mlistViewEmail.setAdapter(contactListAdapter);

				}
			}

		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

	}

	@SuppressLint({ "DefaultLocale", "InlinedApi" })
	public void getNameEmailDetails(FetchContactsAsync fetchContactsAsync) {
		HashSet<String> emlRecsHS = new HashSet<String>();

		String[] PROJECTION = new String[] {

		ContactsContract.Contacts.DISPLAY_NAME,
		/* ContactsContract.Contacts.PHOTO_ID, */
		ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
				ContactsContract.CommonDataKinds.Email.DATA,
		/* ContactsContract.CommonDataKinds.Photo.CONTACT_ID */};
		String order = "CASE WHEN " + ContactsContract.Contacts.DISPLAY_NAME
				+ " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
				+ ContactsContract.Contacts.DISPLAY_NAME + ", "
				+ ContactsContract.CommonDataKinds.Email.DATA
				+ " COLLATE NOCASE";
		String filter = ContactsContract.CommonDataKinds.Email.DATA
				+ " NOT LIKE ''";
		Cursor cur = contentResolver.query(
				ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION,
				filter, null, order);

		Log.e("cur email", cur.getCount() + "@");

		if (cur.moveToFirst() && !fetchContactsAsync.isCancelled()) {
			do {
				// names comes in hand sometimes
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				String emailAddress = cur
						.getString(cur
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

				String uri = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
				/*
				 * String _id = cur.getString(cur
				 * .getColumnIndex(ContactsContract.Contacts._ID));
				 */

				// keep unique only
				if (emlRecsHS.add(emailAddress.toLowerCase())) {

					// emlRecs.add(emlAddr);

					if (ValidateInputFields.isEmailValid(name)) {
						name = name.substring(0, name.indexOf('@'));
					}
					contactList.add(new ContactItem(name, emailAddress, uri));
				}
			} while (cur.moveToNext());

		}

		cur.close();
		Log.e("email cursor close", "email cursor close");

	}

	@SuppressLint("DefaultLocale")
	public void getContactsBelowAPI11(FetchContactsAsync fetchContactsAsync) {
		HashSet<String> emlRecsHS = new HashSet<String>();

		String[] PROJECTION = new String[] {

		ContactsContract.Contacts.DISPLAY_NAME,
		/* ContactsContract.Contacts.PHOTO_ID, */
		/* ContactsContract.Contacts.PHOTO_THUMBNAIL_URI, */
		ContactsContract.CommonDataKinds.Email.DATA,
				ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
		String order = "CASE WHEN " + ContactsContract.Contacts.DISPLAY_NAME
				+ " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
				+ ContactsContract.Contacts.DISPLAY_NAME + ", "
				+ ContactsContract.CommonDataKinds.Email.DATA
				+ " COLLATE NOCASE";
		String filter = ContactsContract.CommonDataKinds.Email.DATA
				+ " NOT LIKE ''";
		Cursor cur = contentResolver.query(
				ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION,
				filter, null, order);

		if (cur.moveToFirst() && !fetchContactsAsync.isCancelled()) {
			do {
				// names comes in hand sometimes
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				String emailAddress = cur
						.getString(cur
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

				long contactID = cur
						.getLong(cur
								.getColumnIndex(ContactsContract.CommonDataKinds.Photo.CONTACT_ID));

				Uri contactUri = ContentUris.withAppendedId(
						Contacts.CONTENT_URI, contactID);
				Uri uri = Uri.withAppendedPath(contactUri,
						Contacts.Photo.CONTENT_DIRECTORY);

				/*
				 * String uri = cur .getString(cur
				 * .getColumnIndex(ContactsContract
				 * .Contacts.PHOTO_THUMBNAIL_URI));
				 */
				/*
				 * String _id = cur.getString(cur
				 * .getColumnIndex(ContactsContract.Contacts._ID));
				 */

				// keep unique only
				if (emlRecsHS.add(emailAddress.toLowerCase())) {

					// emlRecs.add(emlAddr);

					if (ValidateInputFields.isEmailValid(name)) {
						name = name.substring(0, name.indexOf('@'));
					}
					contactList.add(new ContactItem(name, emailAddress, uri
							.toString()));
				}
			} while (cur.moveToNext());

		}

		cur.close();

	}

	private class ContactListAdapter extends ArrayAdapter<ContactItemInterface> {

		private LayoutInflater mInflater;
		private List<ContactItemInterface> contactList;
		private ImageLoader imageLoader;
		private DisplayImageOptions options;

		public void setList(List<ContactItemInterface> contactList) {
			this.contactList = contactList;
		}

		public ContactListAdapter(Context context, int resource,
				List<ContactItemInterface> contactList, ImageLoader imageLoader) {
			super(context, resource, contactList);

			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.contactList = contactList;

			this.imageLoader = imageLoader;
			options = new DisplayImageOptions.Builder()
					.showStubImage(R.drawable.ic_contact_picture_holo_light)
					.resetViewBeforeLoading(true)
					/* .showImageForEmptyUri(R.drawable.opacity) */
					.cacheOnDisc(true).build();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final ContactItemInterface item = this.contactList.get(position);

			ViewHolder viewHolder = null;
			if (convertView == null) {

				convertView = mInflater
						.inflate(R.layout.contact_list_row, null);
				viewHolder = new ViewHolder();
				viewHolder.textViewName = (TextView) convertView
						.findViewById(R.id.textview_name);
				viewHolder.textViewEmail = (TextView) convertView
						.findViewById(R.id.textview_email);
				viewHolder.imageViewProfile = (ImageView) convertView
						.findViewById(R.id.imageview_contact);
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

								if (buttonView.isChecked()) {
									selectedHashSet.add(((ContactItem) contactList
											.get(getPosition)).getEmailID()
											+ ","
											+ ((ContactItem) contactList
													.get(getPosition))
													.getContactName());

								} else {

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
				convertView.setTag(R.id.imageview_contact,
						viewHolder.imageViewProfile);
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
				imageLoader.displayImage(contactItem.getPhotoURI(),
						viewHolder.imageViewProfile, options, null);

				viewHolder.checkBoxEmail.setChecked(contactItem.isChecked());

			}

			return convertView;
		}

		public class ViewHolder {

			TextView textViewName, textViewEmail;
			ImageView imageViewProfile;
			CheckBox checkBoxEmail;

		}
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
							ActivityPhoneContacts.this,
							R.layout.contact_list_row, filterList, imageLoader); // ActivityContacts

					mlistViewEmail.setAdapter(adapter);
				} else {
					ContactListAdapter adapter = new ContactListAdapter(
							ActivityPhoneContacts.this,
							R.layout.contact_list_row, contactList, imageLoader);

					mlistViewEmail.setAdapter(adapter);
				}
			}

		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.button_invite:

			inviteFriends();

			break;

		case R.id.imageview_cancel:

			mEditTextSearch.setText("");

			filterList.clear();
			selectedHashSet.clear();

			for (ContactItemInterface item : contactList) {
				ContactItem contact = (ContactItem) item;
				contact.setChecked(false);

			}

			ContactListAdapter adapter = new ContactListAdapter(this,
					R.layout.contact_list_row, contactList, imageLoader);

			mlistViewEmail.setAdapter(adapter);

		default:
			break;
		}

	}

	private void inviteFriends() {
		if (!selectedHashSet.isEmpty()) {

			if (selectedHashSet.size() <= Constants.EMAIL_LIMITATION) {

				@SuppressWarnings("rawtypes")
				Iterator it = selectedHashSet.iterator();

				JSONArray jsonArray = new JSONArray();

				while (it.hasNext()) {
					String value = (String) it.next();
					JSONObject jsonObject = new JSONObject();
					String[] nameEmail = value.split(",");
					try {
						jsonObject.put("email", nameEmail[0]);
						jsonObject.put("name", nameEmail[1]);
					} catch (JSONException e) {

						e.printStackTrace();
					}
					jsonArray.put(jsonObject);

				}
				HashMap<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("data", jsonArray.toString());
				paramMap.put("memberId",
						String.valueOf(Utility.getSharedPrefStringData(this,
								Constants.KEY_MEMBER_ID)));
				paramMap.put("accessToken", Utility.getSharedPrefStringData(
						this, Constants.KEY_ACCESS_TOKEN));

				MessageParser msgParser = new MessageParser();
				ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(
						this, getResources().getString(R.string.please_wait),
						true, ApiDetails.HOME_URL + ApiDetails.REFERRALS,
						paramMap, ApiDetails.REQUEST_TYPE.POST, this, msgParser);
				Utility.execute(serverIntractorAsync);
			} else {
				Utility.showOKOnlyDialog(this,
						getResources().getString(R.string.email_xceed_msg),
						getResources().getString(R.string.xceed_title)).show();
			}

		} else {

			Utility.showOKOnlyDialog(
					this,
					this.getResources().getString(R.string.select_contact_msg),
					this.getResources()
							.getString(R.string.select_contact_title)).show();

		}

	}

	public void setReferralResponse(MessageInfo msgInfo) {

		if (msgInfo.getStatus() == 1) {
			String msg = msgInfo.getMessage();

			Utility.showOKOnlyDialog(this, msg,
					getResources().getString(R.string.invited_title)).show();
			selectedHashSet.clear();

			for (ContactItemInterface item : contactList) {
				ContactItem contact = (ContactItem) item;
				contact.setChecked(false);
			}

			ContactListAdapter adapter = new ContactListAdapter(this,
					R.layout.contact_list_row, contactList, imageLoader);

			mlistViewEmail.setAdapter(adapter);
		} else {

			String msg = String.format(getResources().getString(
					R.string.error_msg));

			Utility.showOKOnlyDialog(this, msg,
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
