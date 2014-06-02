package com.micar.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MakeReservationInfo extends Model implements Parcelable {

	private String transactionId, url, callbackUrl;

	public MakeReservationInfo() {

	}

	private MakeReservationInfo(Parcel source) {

		transactionId = source.readString();
		url = source.readString();
		callbackUrl = source.readString();

	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(transactionId);
		dest.writeString(url);
		dest.writeString(callbackUrl);

	}

	public static final Parcelable.Creator<MakeReservationInfo> CREATOR = new Parcelable.Creator<MakeReservationInfo>() {

		@Override
		public MakeReservationInfo createFromParcel(Parcel source) {

			return new MakeReservationInfo(source);
		}

		@Override
		public MakeReservationInfo[] newArray(int size) {

			return new MakeReservationInfo[size];
		}
	};

}
