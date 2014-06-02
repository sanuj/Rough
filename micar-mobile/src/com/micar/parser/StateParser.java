package com.micar.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.micar.model.Model;
import com.micar.model.StateInfo;

public class StateParser implements Parser<Model> {

	@Override
	public Model parse(JSONObject jsonObject) throws JSONException {

		StateInfo stateInfo = new StateInfo();
		
		stateInfo.setMessage(jsonObject.getString("message"));
		stateInfo.setStatus(jsonObject.getInt("status"));
		
		
		
		
		if (jsonObject.getInt("status") == 1) {

			JSONObject data = jsonObject.getJSONObject("data");

			JSONArray stateArray = data.getJSONArray("states");

			List<StateInfo> stateList = new ArrayList<StateInfo>();
			for (int i = 0; i < stateArray.length(); i++) {

				JSONObject stateObject = stateArray.getJSONObject(i);
				StateInfo state=new StateInfo();
				
				state.setName(stateObject.getString("name"));
				state.setId(stateObject.getString("id"));
				
				Log.e("name", stateObject.getString("name"));
				Log.e("id", stateObject.getString("id"));
				
				stateList.add(state);

				
			}

			stateInfo.setStateList(stateList);

		}

		return stateInfo;
	}

	@Override
	public ArrayList<Model> parse(String resp) throws JSONException {

		return null;
	}

}