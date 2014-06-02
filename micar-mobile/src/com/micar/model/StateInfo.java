package com.micar.model;

import java.util.List;

public class StateInfo extends Model {
	private String id, name;

	private boolean isChecked;

	private List<StateInfo> stateList;

	public List<StateInfo> getStateList() {
		return stateList;
	}

	public void setStateList(List<StateInfo> stateList) {
		this.stateList = stateList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

}
