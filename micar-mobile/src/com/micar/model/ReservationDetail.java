package com.micar.model;

public class ReservationDetail extends Model {

	private String city, vehicleType, startTime, endTime, kmRate,
			estimatedCost, accessoryTypes, permittedStates, insurance,
			pickUpAddress, deliveryAddress;

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getPickUpAddress() {
		return pickUpAddress;
	}

	public void setPickUpAddress(String pickUpAddress) {
		this.pickUpAddress = pickUpAddress;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getKmRate() {
		return kmRate;
	}

	public void setKmRate(String kmRate) {
		this.kmRate = kmRate;
	}

	public String getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(String estimatedCost) {
		this.estimatedCost = estimatedCost;
	}

	public String getAccessoryTypes() {
		return accessoryTypes;
	}

	public void setAccessoryTypes(String accessoryTypes) {
		this.accessoryTypes = accessoryTypes;
	}

	public String getPermittedStates() {
		return permittedStates;
	}

	public void setPermittedStates(String permittedStates) {
		this.permittedStates = permittedStates;
	}
}
