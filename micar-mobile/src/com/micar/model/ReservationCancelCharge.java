package com.micar.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class ReservationCancelCharge extends Model implements Parcelable {

	private int status;
	private String message, hoursBeforeReservation, chargePercentage,
			maxHrsToBeChargeFor;

	private boolean isActive;

	private List<ReservationCancelCharge> cancelChargeList;

	public List<ReservationCancelCharge> getCancelChargeList() {
		return cancelChargeList;
	}

	public ReservationCancelCharge() {

	}

	private ReservationCancelCharge(Parcel source) {

		// status;
		// private String message, hoursBeforeReservation, chargePercentage,
		// maxHrsToBeChargeFor, isActive;

		cancelChargeList = new ArrayList<ReservationCancelCharge>();
		status = source.readInt();
		message = source.readString();
		hoursBeforeReservation = source.readString();
		chargePercentage = source.readString();
		maxHrsToBeChargeFor = source.readString();
		isActive = source.readByte() != 0;// myBoolean == true if byte != 0

		source.readTypedList(cancelChargeList, CREATOR);

	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setCancelChargeList(
			List<ReservationCancelCharge> cancelChargeList) {
		this.cancelChargeList = cancelChargeList;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getHoursBeforeReservation() {
		return hoursBeforeReservation;
	}

	public void setHoursBeforeReservation(String hoursBeforeReservation) {
		this.hoursBeforeReservation = hoursBeforeReservation;
	}

	public String getChargePercentage() {
		return chargePercentage;
	}

	public void setChargePercentage(String chargePercentage) {
		this.chargePercentage = chargePercentage;
	}

	public String getMaxHrsToBeChargeFor() {
		return maxHrsToBeChargeFor;
	}

	public void setMaxHrsToBeChargeFor(String maxHrsToBeChargeFor) {
		this.maxHrsToBeChargeFor = maxHrsToBeChargeFor;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeInt(status);
		dest.writeString(message);
		dest.writeString(hoursBeforeReservation);
		dest.writeString(chargePercentage);
		dest.writeString(maxHrsToBeChargeFor);
		dest.writeByte((byte) (isActive ? 1 : 0));
		dest.writeTypedList(cancelChargeList);

	}

	public static final Parcelable.Creator<ReservationCancelCharge> CREATOR = new Parcelable.Creator<ReservationCancelCharge>() {

		@Override
		public ReservationCancelCharge createFromParcel(Parcel in) {

			return new ReservationCancelCharge(in);
		}

		@Override
		public ReservationCancelCharge[] newArray(int size) {

			return new ReservationCancelCharge[size];
		}
	};

}
