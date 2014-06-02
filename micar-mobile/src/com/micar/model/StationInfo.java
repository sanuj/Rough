package com.micar.model;

import java.util.List;

public class StationInfo extends Model {

	private String name, latitude, longitude, id;

	private List<StationInfo> stationList;

	public List<StationInfo> getStationList() {
		return stationList;
	}

	public void setStationList(List<StationInfo> stationList) {
		this.stationList = stationList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
