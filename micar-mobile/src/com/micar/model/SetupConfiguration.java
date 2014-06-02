package com.micar.model;

public class SetupConfiguration extends Model {

	private int maxReservationAtATime, inVoicableReservation,
			inventorySlotMultiple, deadSlotDeliveryMiJ, deadSlotPickupMiJ,
			minPinningTime, minPeekTime, maxPeekTime,
			afterReservationDeadSlotFleet, minNewReservationDuration,
			maxContinuousReservationTime, maxTotalReservationDuration,
			dailyRateHours;

	private String consumerMemberGroup;

	public int getDeadSlotPickupMiJ() {
		return deadSlotPickupMiJ;
	}

	public void setDeadSlotPickupMiJ(int deadSlotPickupMiJ) {
		this.deadSlotPickupMiJ = deadSlotPickupMiJ;
	}

	public int getMaxReservationAtATime() {
		return maxReservationAtATime;
	}

	public void setMaxReservationAtATime(int maxReservationAtATime) {
		this.maxReservationAtATime = maxReservationAtATime;
	}

	public int getInVoicableReservation() {
		return inVoicableReservation;
	}

	public void setInVoicableReservation(int inVoicableReservation) {
		this.inVoicableReservation = inVoicableReservation;
	}

	public int getInventorySlotMultiple() {
		return inventorySlotMultiple;
	}

	public void setInventorySlotMultiple(int inventorySlotMultiple) {
		this.inventorySlotMultiple = inventorySlotMultiple;
	}

	public int getDeadSlotDeliveryMiJ() {
		return deadSlotDeliveryMiJ;
	}

	public void setDeadSlotDeliveryMiJ(int deadSlotDeliveryMiJ) {
		this.deadSlotDeliveryMiJ = deadSlotDeliveryMiJ;
	}

	public int getMinPinningTime() {
		return minPinningTime;
	}

	public void setMinPinningTime(int minPinningTime) {
		this.minPinningTime = minPinningTime;
	}

	public int getMinPeekTime() {
		return minPeekTime;
	}

	public void setMinPeekTime(int minPeekTime) {
		this.minPeekTime = minPeekTime;
	}

	public int getMaxPeekTime() {
		return maxPeekTime;
	}

	public void setMaxPeekTime(int maxPeekTime) {
		this.maxPeekTime = maxPeekTime;
	}

	public int getAfterReservationDeadSlotFleet() {
		return afterReservationDeadSlotFleet;
	}

	public void setAfterReservationDeadSlotFleet(
			int afterReservationDeadSlotFleet) {
		this.afterReservationDeadSlotFleet = afterReservationDeadSlotFleet;
	}

	public int getMinNewReservationDuration() {
		return minNewReservationDuration;
	}

	public void setMinNewReservationDuration(int minNewReservationDuration) {
		this.minNewReservationDuration = minNewReservationDuration;
	}

	public int getMaxContinuousReservationTime() {
		return maxContinuousReservationTime;
	}

	public void setMaxContinuousReservationTime(int maxContinuousReservationTime) {
		this.maxContinuousReservationTime = maxContinuousReservationTime;
	}

	public int getMaxTotalReservationDuration() {
		return maxTotalReservationDuration;
	}

	public void setMaxTotalReservationDuration(int maxTotalReservationDuration) {
		this.maxTotalReservationDuration = maxTotalReservationDuration;
	}

	public int getDailyRateHours() {
		return dailyRateHours;
	}

	public void setDailyRateHours(int dailyRateHours) {
		this.dailyRateHours = dailyRateHours;
	}

	public String getConsumerMemberGroup() {
		return consumerMemberGroup;
	}

	public void setConsumerMemberGroup(String consumerMemberGroup) {
		this.consumerMemberGroup = consumerMemberGroup;
	}

}
