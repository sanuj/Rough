package com.micar.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.micar.model.Model;
import com.micar.model.UpcomingReservation;

public class UpcomingReservationParser implements Parser<Model> {

	@Override
	public Model parse(JSONObject json) throws JSONException {
		UpcomingReservation upcomingReservation = new UpcomingReservation();

		upcomingReservation.setStatus(json.getInt("status"));
		upcomingReservation.setMessage(json.getString("message"));

		if (json.getInt("status") == 1) {

			// {"status":1,"message":"","data":{"reservationList":[{"reservationId":"R0000001","startDateTime":"22/04/2014 18:15","endDateTime":"22/04/2014 23:45","accountType":"Myself"}]}}

			JSONObject data = json.getJSONObject("data");
			JSONArray reservationArray = data.getJSONArray("reservationList");

			List<UpcomingReservation> reservationList = new ArrayList<UpcomingReservation>();

			for (int i = 0; i < reservationArray.length(); i++) {

				UpcomingReservation reservation = new UpcomingReservation();
				JSONObject reservationJSON = reservationArray.getJSONObject(i);
				reservation.setReservationId(reservationJSON
						.getString("reservationId"));
				reservation.setStartTime(reservationJSON
						.getString("startDateTime"));
				reservation
						.setEndTime(reservationJSON.getString("endDateTime"));
				reservation.setAccountType(reservationJSON
						.getString("accountType"));
				reservationList.add(reservation);

			}
			upcomingReservation.setReservationList(reservationList);

		}

		return upcomingReservation;
	}

	@Override
	public ArrayList<Model> parse(String resp) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}
