package com.micar.model;

import java.util.List;

public class PreBoughtHoursInfo extends Model {
	
	
	private String  hours,month, year;
	
	private List<PreBoughtHoursInfo> hoursList;
	
	
	public List<PreBoughtHoursInfo> getHoursList() {
		return hoursList;
	}
	public void setHoursList(List<PreBoughtHoursInfo> hoursList) {
		this.hoursList = hoursList;
	}
	
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

}
