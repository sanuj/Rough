package com.micar.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class ActiveReservation extends Model implements Parcelable {

	private String expectedStartDate, expectedEndDate, actualStartDate,
			reservationId, reservationMemberType;
	private List<ActiveReservation> activeList;

	public ActiveReservation() {

	}

	public ActiveReservation(Parcel parcel) {
		activeList = new ArrayList<ActiveReservation>();

		parcel.readTypedList(activeList, CREATOR);

	}

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public String getReservationMemberType() {
		return reservationMemberType;
	}

	public void setReservationMemberType(String reservationMemberType) {
		this.reservationMemberType = reservationMemberType;
	}

	public List<ActiveReservation> getActiveList() {
		return activeList;
	}

	public void setActiveList(List<ActiveReservation> activeList) {
		this.activeList = activeList;
	}

	public String getExpectedStartDate() {
		return expectedStartDate;
	}

	public void setExpectedStartDate(String expectedStartDate) {
		this.expectedStartDate = expectedStartDate;
	}

	public String getExpectedEndDate() {
		return expectedEndDate;
	}

	public void setExpectedEndDate(String expectedEndDate) {
		this.expectedEndDate = expectedEndDate;
	}

	public String getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(activeList);

	}

	public static final Parcelable.Creator<ActiveReservation> CREATOR = new Parcelable.Creator<ActiveReservation>() {

		@Override
		public ActiveReservation createFromParcel(Parcel in) {

			return new ActiveReservation(in);
		}

		@Override
		public ActiveReservation[] newArray(int size) {

			return new ActiveReservation[size];
		}
	};

}
