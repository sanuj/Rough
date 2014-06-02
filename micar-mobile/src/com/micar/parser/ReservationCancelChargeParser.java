package com.micar.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.micar.model.Model;
import com.micar.model.ReservationCancelCharge;

public class ReservationCancelChargeParser implements Parser<Model> {

	@Override
	public Model parse(JSONObject jsonObject) throws JSONException {

		ReservationCancelCharge cancelCharge = new ReservationCancelCharge();

		cancelCharge.setMessage(jsonObject.getString("message"));
		cancelCharge.setStatus(jsonObject.getInt("status"));

		if (jsonObject.getInt("status") == 1) {

			JSONObject dataJSON = jsonObject.getJSONObject("data");

			JSONArray cancelArray = dataJSON
					.getJSONArray("cancelChargeComponentList");
			List<ReservationCancelCharge> cancelChargeList = new ArrayList<ReservationCancelCharge>();

			for (int i = 0; i < cancelArray.length(); i++) {

				ReservationCancelCharge reservationCancelCharge = new ReservationCancelCharge();

				JSONObject cancelJSON = cancelArray.getJSONObject(i);
				reservationCancelCharge.setHoursBeforeReservation(cancelJSON
						.getString("hoursBeforeReservation"));
				reservationCancelCharge.setChargePercentage(cancelJSON
						.getString("chargePercentage"));
				reservationCancelCharge.setMaxHrsToBeChargeFor(cancelJSON
						.getString("maxHoursToBeChargedFor"));
				reservationCancelCharge.setActive(cancelJSON
						.getBoolean("isActive"));

				cancelChargeList.add(reservationCancelCharge);

			}
			cancelCharge.setCancelChargeList(cancelChargeList);

		}

		return cancelCharge;
	}

	@Override
	public ArrayList<Model> parse(String resp) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}
