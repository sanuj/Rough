package com.micar.utils;

public class ApiDetails {

	/*
	 * 
	 * http://10.1.1.68:8080/micar/mobileApi/
	 * 
	 * 
	 * http://micar.qa3.intelligrape.net/mobileApi/
	 */

	public enum REQUEST_TYPE {
		GET, POST, MULTIPART_GET, MULTIPART_POST
	}

	public static final String APP_KEY = "abc";
	public static final String APP_VERSION = "1.0";

	public static final String HOME_URL = "http://micar2.qa3.intelligrape.net/mobileApi/";
	public static final String LOGIN = "login";
	public static final String FORGOT_PASSWORD = "forgotPassword";
	public static final String LOG_OUT = "logout";
	public static final String VALIDATE_ACCESS_TOKEN = "validateAccessToken";
	public static final String FETCH_PERMITTED_STATES = "fetchPermittedStates";
	public static final String FETCH_ACCESSORIES_TYPE = "fetchAccessoryTypes";
	public static final String FETCH_VEHICLE_AND_CITY_DETAILS = "fetchVehicleAndCityDetails"; // fetchReservationDetails
	public static final String FETCH_CITY_STATIONS = "fetchCityStations";
	public static final String FETCH_MEMBER_MI_POINTS = "fetchMemberMiPoints";
	public static final String MAKE_RESERVATOIN = "makeReservation";
	public static final String CHECK_RESERVATION_PAYMENT = "checkReservationPayment";
	public static final String PRE_BOUGHT_HOURS = "preBoughtHours";
	public static final String FETCH_PENDING_RESERVATION = "fetchPendingReservations";
	public static final String FETCH_RESERVATION_DETAIL = "fetchReservationDetail";
	public static final String FETCH_CANCEL_RESERVATION_CHARGES = "fetchCancelReservationCharges";
	public static final String FETCH_ACTIVE_RESERVATION="fetchActiveReservation";
	
	public static final String CANCEL_RESERVATOIN="cancelReservation";

	public static final String MY_STATION = "fetchStation";
	public static final String REFERRALS = "referrals";
	public static final String REFERRALSMS = "referralSms";
	public static final String FETCH_INVITE_EMAILS = "fetchInviteEmails";

}
