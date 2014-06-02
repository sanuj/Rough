package com.micar.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.micar.model.InvitedInfo;
import com.micar.model.Model;

public class InvitedFriendParser implements Parser<Model> {

	@Override
	public Model parse(JSONObject jsonObject) throws JSONException {
		InvitedInfo invitedInfo = new InvitedInfo();

		invitedInfo.setStatus(jsonObject.getInt("status"));
		if (jsonObject.getInt("status") == 1) {
			List<InvitedInfo> invitedList = new ArrayList<InvitedInfo>();

			JSONArray refferedArray = jsonObject.getJSONArray("referredData");

			for (int i = 0; i < refferedArray.length(); i++) {

				InvitedInfo friendInfo = new InvitedInfo();
				friendInfo.setInvitedData(refferedArray.getJSONObject(i)
						.getString("data"));
				friendInfo.setInvitedName(refferedArray.getJSONObject(i)
						.getString("name"));
				friendInfo.setInvitedStatus(refferedArray.getJSONObject(i)
						.getString("status"));

				invitedList.add(friendInfo);

			}

			invitedInfo.setInvitedList(invitedList);

		}

		return invitedInfo;
	}

	@Override
	public ArrayList<Model> parse(String resp) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}
