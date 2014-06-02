package com.micar.parser;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.micar.model.Model;
import com.micar.model.ReservationDetail;

public class ReservationDetialParser implements Parser<Model> {

	@Override
	public Model parse(JSONObject json) throws JSONException {

		ReservationDetail detail = new ReservationDetail();

		detail.setStatus(json.getInt("status"));
		detail.setMessage(json.getString("message"));

		if (json.getInt("status") == 1) {

			JSONObject data = json.getJSONObject("data");
			detail.setCity(data.getString("city"));
			detail.setVehicleType(data.getString("vehicleType"));
			detail.setStartTime(data.getString("startDateTime"));
			detail.setKmRate(data.getString("kmRate"));
			detail.setEstimatedCost(data.getString("estimatedCost"));
			detail.setAccessoryTypes(data.getString("accessoryTypeList"));
			detail.setInsurance(data.getString("insurance"));
			detail.setPermittedStates(data.getString("permittedStates"));
			detail.setPickUpAddress(data.getString("pickUpAddress"));
			detail.setDeliveryAddress(data.getString("deliveryAddress"));

		}

		return detail;
	}

	@Override
	public ArrayList<Model> parse(String resp) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}
