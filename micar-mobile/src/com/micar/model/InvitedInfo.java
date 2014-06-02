package com.micar.model;

import java.util.List;

import android.os.Parcel;

public class InvitedInfo extends Model {

	private String invitedStatus, invitedName, invitedData;

	private List<InvitedInfo> invitedList;

	public List<InvitedInfo> getInvitedList() {
		return invitedList;
	}

	public void setInvitedList(List<InvitedInfo> invitedList) {
		this.invitedList = invitedList;
	}

	public String getInvitedStatus() {
		return invitedStatus;
	}

	public void setInvitedStatus(String invitedStatus) {
		this.invitedStatus = invitedStatus;
	}

	public String getInvitedName() {
		return invitedName;
	}

	public void setInvitedName(String invitedName) {
		this.invitedName = invitedName;
	}

	public String getInvitedData() {
		return invitedData;
	}

	public void setInvitedData(String invitedData) {
		this.invitedData = invitedData;
	}

	

}
