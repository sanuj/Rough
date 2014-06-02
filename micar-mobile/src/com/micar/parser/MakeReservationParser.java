package com.micar.parser;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.micar.model.MakeReservationInfo;
import com.micar.model.Model;

public class MakeReservationParser implements Parser<Model> {

	@Override
	public Model parse(JSONObject json) throws JSONException {
		
		MakeReservationInfo makeReservationInfo=new MakeReservationInfo();
		
		makeReservationInfo.setStatus(json.getInt("status"));
		makeReservationInfo.setMessage(json.getString("message"));
		if(json.getInt("status")==1)
		{
			
			JSONObject data=json.getJSONObject("data");
			makeReservationInfo.setTransactionId(data.getString("transactionId"));
			makeReservationInfo.setUrl(data.getString("url"));
			makeReservationInfo.setCallbackUrl(data.getString("callbackUrl"));
			
		}

		return makeReservationInfo;
	}

	@Override
	public ArrayList<Model> parse(String resp) throws JSONException {

		return null;
	}

}
