package com.micar.activity;

import java.util.List;

import com.micar.adapter.CancellationChargeAdapter;
import com.micar.model.ReservationCancelCharge;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class ActivityCancellationBreakDown extends Activity {
	private ListView mListViewCharge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cancellation_break_down);
		mListViewCharge = (ListView) findViewById(R.id.listview);
		ReservationCancelCharge reservationCancel = getIntent()
				.getParcelableExtra("reservationCancel");

		CancellationChargeAdapter adapter = new CancellationChargeAdapter(this,
				reservationCancel.getCancelChargeList());
		mListViewCharge.setAdapter(adapter);

	}

}
