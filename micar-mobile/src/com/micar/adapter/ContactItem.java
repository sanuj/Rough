package com.micar.adapter;


public class ContactItem implements ContactItemInterface{

	private String contactName;
	private String emailID;
	private String photoURI;
	private boolean isChecked;
	

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getPhotoURI() {
		return photoURI;
	}

	public void setPhotoURI(String photoURI) {
		this.photoURI = photoURI;
	}

	public ContactItem(String contactName, String emailID,String photoURI) {
		super();
		
		this.contactName = contactName;
		this.photoURI=photoURI;
		this.setEmailID(emailID);
	}

	// index the list by nickname
	@Override
	public String getItemForIndex() {
		return contactName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	

}
