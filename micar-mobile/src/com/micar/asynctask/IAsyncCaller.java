package com.micar.asynctask;

import java.util.ArrayList;

import com.micar.model.Model;


/**
 * @author Rajendra
 * 
 *         Interface for async executor to get callback response either Model
 *         object or collection of Model object
 */
public interface IAsyncCaller {
	public static final int STATE_OK = 1;
	public static final int STATE_ERROR = 0;

	public void onComplete(Model object);

	public void onComplete(ArrayList<Model> object);

}
