package com.micar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ActivityStateDetail extends Activity {

	private TextView mTxtViewDetail, TxtViewName;
	private String detail, detailLabel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_state_detail);
		detail = getIntent().getStringExtra("detail");
		detailLabel = getIntent().getStringExtra("detailLabel");

		getViewsId();
	}

	private void getViewsId() {

		mTxtViewDetail = (TextView) findViewById(R.id.txtview_details);
		TxtViewName = (TextView) findViewById(R.id.txtview_detail_label);
		mTxtViewDetail.setText(detail);
		TxtViewName.setText(detailLabel);

	}

}
