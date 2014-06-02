package com.micar.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AddressInfo extends Model implements Parcelable {

	private String name, id;
	boolean isFavorite;

	public AddressInfo() {

	}

	private AddressInfo(Parcel parcel) {

		name = parcel.readString();

		id = parcel.readString();
		isFavorite = parcel.readByte() != 0;// myBoolean == true if byte != 0
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(id);
		dest.writeByte((byte) (isFavorite ? 1 : 0)); // if myBoolean == true,
														// byte
		// ==1

	}

	public static final Parcelable.Creator<AddressInfo> CREATOR = new Parcelable.Creator<AddressInfo>() {

		@Override
		public AddressInfo createFromParcel(Parcel source) {

			return new AddressInfo(source);
		}

		@Override
		public AddressInfo[] newArray(int size) {

			return new AddressInfo[size];
		}
	};

}
