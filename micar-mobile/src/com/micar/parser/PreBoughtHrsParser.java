package com.micar.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.micar.model.Model;
import com.micar.model.PreBoughtHoursInfo;

public class PreBoughtHrsParser implements Parser<Model> {

	@Override
	public Model parse(JSONObject json) throws JSONException {

		PreBoughtHoursInfo preBoughtHoursInfo = new PreBoughtHoursInfo();
		preBoughtHoursInfo.setStatus(json.getInt("status"));
		preBoughtHoursInfo.setMessage(json.getString("message"));

		if (json.getInt("status") == 1) {

			JSONObject data = json.getJSONObject("data");

			JSONArray preBoughtHrs = data.getJSONArray("preBoughtHours");

			List<PreBoughtHoursInfo> hoursList = new ArrayList<PreBoughtHoursInfo>();
			for (int i = 0; i < preBoughtHrs.length(); i++) {

				JSONObject leftHrsObject = preBoughtHrs.getJSONObject(i);
				PreBoughtHoursInfo hrs = new PreBoughtHoursInfo();
				hrs.setHours(leftHrsObject.getString("hours"));
				hrs.setMonth(leftHrsObject.getString("month"));
				hrs.setYear(leftHrsObject.getString("year"));
				hoursList.add(hrs);

			}

			preBoughtHoursInfo.setHoursList(hoursList);
		}

		return preBoughtHoursInfo;
	}

	@Override
	public ArrayList<Model> parse(String resp) throws JSONException {

		return null;
	}

}
