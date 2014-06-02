package com.micar.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.micar.model.Model;
import com.micar.model.StationInfo;

public class CityStationsParser implements Parser<Model> {

	@Override
	public Model parse(JSONObject jsonObject) throws JSONException {

		StationInfo stationInfo = new StationInfo();

		stationInfo.setMessage(jsonObject.getString("message"));
		stationInfo.setStatus(jsonObject.getInt("status"));

		if (jsonObject.getInt("status") == 1) {

			JSONObject data = jsonObject.getJSONObject("data");

			JSONArray stationArray = data.getJSONArray("stations");

			List<StationInfo> stationList = new ArrayList<StationInfo>();
			for (int i = 0; i < stationArray.length(); i++) {

				JSONObject stationObject = stationArray.getJSONObject(i);
				StationInfo station = new StationInfo();

				station.setName(stationObject.getString("name"));
				station.setId(stationObject.getString("id"));
				station.setLongitude(stationObject.getString("longitude"));
				station.setLatitude(stationObject.getString("latitude"));
				stationList.add(station);

			}

			stationInfo.setStationList(stationList);

		}

		return stationInfo;
	}

	@Override
	public ArrayList<Model> parse(String resp) throws JSONException {

		return null;
	}

}