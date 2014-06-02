package com.micar.asynctask;

import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;

/**
 * @author rajendra
 * 
 * 
 *         need to be implemented by class do httpget or post request to
 *         generate get URL oir post request params
 */
public interface IGetPostRequest {
	String getURL(String url, HashMap<String, String> paramMap);

	List<NameValuePair> getParams(HashMap<String, String> paramMap);

	String getJsonParam(HashMap<String, String> paramMap);
}
