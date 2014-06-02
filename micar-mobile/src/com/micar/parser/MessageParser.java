package com.micar.parser;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.micar.model.MessageInfo;
import com.micar.model.Model;

public class MessageParser implements Parser<Model> {

	@Override
	public Model parse(JSONObject json) throws JSONException {

		MessageInfo msgInfo = new MessageInfo();
		msgInfo.setStatus(json.getInt("status"));
		msgInfo.setMessage(json.getString("message"));

		return msgInfo;
	}

	@Override
	public ArrayList<Model> parse(String resp) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}
