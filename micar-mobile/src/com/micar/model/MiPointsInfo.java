package com.micar.model;

public class MiPointsInfo extends Model {

	private String totalPoints, earnedPoints, burnedPoints;

	public String getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(String totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getEarnedPoints() {
		return earnedPoints;
	}

	public void setEarnedPoints(String earnedPoints) {
		this.earnedPoints = earnedPoints;
	}

	public String getBurnedPoints() {
		return burnedPoints;
	}

	public void setBurnedPoints(String burnedPoints) {
		this.burnedPoints = burnedPoints;
	}

}
