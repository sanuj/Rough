package com.micar.model;

import java.util.List;

public class VehicleCityDetail extends Model {

	private List<VehicleInfo> vehicleList;
	private List<CityInfo> cityList;

	private List<MemberDetail> memberList;

	private SetupConfiguration setupConfiguration;

	public SetupConfiguration getSetupConfiguration() {
		return setupConfiguration;
	}

	public void setSetupConfiguration(SetupConfiguration setupConfiguration) {
		this.setupConfiguration = setupConfiguration;
	}

	public List<MemberDetail> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<MemberDetail> memberList) {
		this.memberList = memberList;
	}

	public List<VehicleInfo> getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(List<VehicleInfo> vehicleList) {
		this.vehicleList = vehicleList;
	}

	public List<CityInfo> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityInfo> cityList) {
		this.cityList = cityList;
	}

}
