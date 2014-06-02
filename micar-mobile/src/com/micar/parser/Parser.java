package com.micar.parser;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.micar.model.Model;


public interface Parser<T extends Model> {

	public abstract T parse(JSONObject json) throws JSONException;

	// public Collection<IModel> parse(JSONArray array) throws JSONException;
	//
	 public ArrayList<Model> parse(String resp) throws JSONException;
}
