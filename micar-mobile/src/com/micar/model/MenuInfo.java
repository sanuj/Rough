package com.micar.model;

public class MenuInfo {

	private String menuItemName, memberType;

	private int icon;
	private boolean isUserProfile;

	

	public boolean isUserProfile() {
		return isUserProfile;
	}

	public void setUserProfile(boolean isUserProfile) {
		this.isUserProfile = isUserProfile;
	}

	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

}
