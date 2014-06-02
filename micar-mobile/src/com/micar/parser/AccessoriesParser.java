package com.micar.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.micar.model.AccessoriesInfo;
import com.micar.model.Model;

public class AccessoriesParser implements Parser<Model> {

	@Override
	public Model parse(JSONObject jsonObject) throws JSONException {

		AccessoriesInfo accessorriesInfo = new AccessoriesInfo();

		accessorriesInfo.setMessage(jsonObject.getString("message"));
		accessorriesInfo.setStatus(jsonObject.getInt("status"));

		if (jsonObject.getInt("status") == 1) {

			JSONObject data = jsonObject.getJSONObject("data");

			JSONArray accessoriesArray = data.getJSONArray("accessoryTypeList");

			List<AccessoriesInfo> accessoriesList = new ArrayList<AccessoriesInfo>();
			for (int i = 0; i < accessoriesArray.length(); i++) {

				JSONObject accessoriesObject = accessoriesArray.getJSONObject(i);
				AccessoriesInfo state = new AccessoriesInfo();

				state.setName(accessoriesObject.getString("name"));
				state.setId(accessoriesObject.getString("id"));

				Log.e("name", accessoriesObject.getString("name"));
				Log.e("id", accessoriesObject.getString("id"));

				accessoriesList.add(state);

			}

			accessorriesInfo.setAccessoriesList(accessoriesList);

		}

		return accessorriesInfo;
	}

	@Override
	public ArrayList<Model> parse(String resp) throws JSONException {

		return null;
	}

}