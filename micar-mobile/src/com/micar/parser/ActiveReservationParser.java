package com.micar.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.micar.model.ActiveReservation;
import com.micar.model.Model;

public class ActiveReservationParser implements Parser<Model> {

	@Override
	public Model parse(JSONObject json) throws JSONException {

		ActiveReservation activeReservation = new ActiveReservation();
		activeReservation.setStatus(json.getInt("status"));
		activeReservation.setMessage(json.getString("message"));
		if (json.getInt("status") == 1) {

			JSONObject dataJSON = json.getJSONObject("data");

			JSONArray activeArray = dataJSON
					.getJSONArray("activeReservationDetailList");
			List<ActiveReservation> activeList = new ArrayList<ActiveReservation>();

			for (int i = 0; i < activeArray.length(); i++) {

				ActiveReservation active = new ActiveReservation();
				JSONObject activeJSON = activeArray.getJSONObject(i);

				active.setExpectedStartDate(activeJSON
						.getString("expectedStartDate"));
				active.setActualStartDate(activeJSON
						.getString("actualStartDate"));
				active.setExpectedEndDate(activeJSON
						.getString("expectedEndDate"));
				active.setReservationId(activeJSON.getString("reservationId"));
				active.setReservationMemberType(activeJSON
						.getString("reservationMemberType"));

				activeList.add(active);

			}
			activeReservation.setActiveList(activeList);

		}

		return activeReservation;
	}

	@Override
	public ArrayList<Model> parse(String resp) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}
