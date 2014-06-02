package com.micar.model;

import java.util.List;

public class AccessoriesInfo extends Model {

	private String id, name;

	private boolean isChecked;

	private List<AccessoriesInfo> accessoriesList;

	public List<AccessoriesInfo> getAccessoriesList() {
		return accessoriesList;
	}

	public void setAccessoriesList(List<AccessoriesInfo> accessoriesList) {
		this.accessoriesList = accessoriesList;
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