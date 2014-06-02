package com.micar.parser;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.micar.model.MiPointsInfo;
import com.micar.model.Model;

public class MiPointsParser implements Parser<Model> {

	@Override
	public Model parse(JSONObject json) throws JSONException {

		MiPointsInfo miPointsInfo = new MiPointsInfo();

		miPointsInfo.setStatus(json.getInt("status"));
		miPointsInfo.setMessage(json.getString("message"));

		if (json.getInt("status") == 1) {

			JSONObject data = json.getJSONObject("data");

			miPointsInfo.setTotalPoints(data.getString("available"));
			miPointsInfo.setEarnedPoints(data.getString("earned"));
			miPointsInfo.setBurnedPoints(data.getString("burned"));

		}

		return miPointsInfo;
	}

	@Override
	public ArrayList<Model> parse(String resp) throws JSONException {
		return null;
	}

}
