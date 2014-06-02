package com.micar.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginInfo extends Model implements Parcelable {

	private int status;
	private String accessToken, memberId, fullName, memberPlan, memberType,
			firstName, lastName, memberPlanId, favAddressId, favAddress,
			message, referralCode;
	private boolean activeReservation;

	private List<AddressInfo> addressList = null;

	public LoginInfo() {
	}

	private LoginInfo(Parcel parcel) {
		addressList = new ArrayList<AddressInfo>();

		status = parcel.readInt();
		accessToken = parcel.readString();
		memberId = parcel.readString();
		fullName = parcel.readString();
		memberPlan = parcel.readString();
		memberType = parcel.readString();
		firstName = parcel.readString();
		lastName = parcel.readString();
		memberPlanId = parcel.readString();
		favAddressId = parcel.readString();
		favAddress = parcel.readString();
		message = parcel.readString();
		referralCode = parcel.readString();
		activeReservation = parcel.readByte() != 0;// myBoolean == true if
														// byte != 0

		parcel.readTypedList(addressList, AddressInfo.CREATOR);

	}

	public boolean isActiveReservation() {
		return activeReservation;
	}

	public void setActiveReservation(boolean activeReservation) {
		this.activeReservation = activeReservation;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<AddressInfo> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<AddressInfo> addressList) {
		this.addressList = addressList;
	}

	public String getFavAddressId() {
		return favAddressId;
	}

	public void setFavAddressId(String favAddressId) {
		this.favAddressId = favAddressId;
	}

	public String getFavAddress() {
		return favAddress;
	}

	public void setFavAddress(String favAddress) {
		this.favAddress = favAddress;
	}

	public int getStatus() {
		return status;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMemberPlan() {
		return memberPlan;
	}

	public void setMemberPlan(String memberPlan) {
		this.memberPlan = memberPlan;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMemberPlanId() {
		return memberPlanId;
	}

	public void setMemberPlanId(String memberPlanId) {
		this.memberPlanId = memberPlanId;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeInt(status);
		dest.writeString(accessToken);
		dest.writeString(memberId);
		dest.writeString(fullName);
		dest.writeString(memberPlan);
		dest.writeString(memberType);
		dest.writeString(firstName);
		dest.writeString(lastName);
		dest.writeString(memberPlanId);
		dest.writeString(favAddressId);
		dest.writeString(favAddress);
		dest.writeString(message);
		dest.writeString(referralCode);
		dest.writeByte((byte) (activeReservation ? 1 : 0));
		dest.writeTypedList(addressList);

	}

	public static final Parcelable.Creator<LoginInfo> CREATOR = new Parcelable.Creator<LoginInfo>() {

		@Override
		public LoginInfo createFromParcel(Parcel in) {

			return new LoginInfo(in);
		}

		@Override
		public LoginInfo[] newArray(int size) {

			return new LoginInfo[size];
		}
	};

}
