package com.micar.model;

import java.util.List;

public class UpcomingReservation extends Model {

	private String reservationId, startTime, endTime, accountType;

	private List<UpcomingReservation> reservationList;

	public List<UpcomingReservation> getReservationList() {
		return reservationList;
	}

	public void setReservationList(List<UpcomingReservation> reservationList) {
		this.reservationList = reservationList;
	}

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

}
