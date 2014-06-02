package com.micar.activity;

import com.micar.utils.Constants;
import com.micar.utils.Utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityReferral extends Activity implements OnClickListener {

	private RelativeLayout mRelativeLytPhoneContacts, mRelativeLytEnterEmail;
	private TextView mTxtViewReferralCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_referral);
		getViewsId();
	}

	private void getViewsId() {

		mRelativeLytPhoneContacts = (RelativeLayout) findViewById(R.id.relative_lyt_from_contact);
		mRelativeLytEnterEmail = (RelativeLayout) findViewById(R.id.relative_lyt_enter_email);
		mTxtViewReferralCode = (TextView) findViewById(R.id.txtview_referral_code);

		mTxtViewReferralCode.setText(getString(R.string.share_ref_code, Utility
				.getSharedPrefStringData(this, Constants.KEY_REFERRAL_CODE)));

		mRelativeLytPhoneContacts.setOnClickListener(this);
		mRelativeLytEnterEmail.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		Intent intent = null;

		switch (v.getId()) {
		case R.id.relative_lyt_from_contact:

			intent = new Intent(this, ActivityPhoneContacts.class);
			break;
		case R.id.relative_lyt_enter_email:
			intent = new Intent(this, ActivityEnterEmail.class);

			break;

		default:
			break;
		}

		if (intent != null) {
			startActivity(intent);
		}

	}

}
