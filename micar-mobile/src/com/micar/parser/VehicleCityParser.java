package com.micar.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.micar.model.CityInfo;
import com.micar.model.MemberDetail;
import com.micar.model.Model;
import com.micar.model.SetupConfiguration;
import com.micar.model.VehicleCityDetail;
import com.micar.model.VehicleInfo;

public class VehicleCityParser implements Parser<Model> {

	@Override
	public Model parse(JSONObject jsonObject) throws JSONException {

		VehicleCityDetail detailsInfo = new VehicleCityDetail();

		detailsInfo.setMessage(jsonObject.getString("message"));
		detailsInfo.setStatus(jsonObject.getInt("status"));

		if (jsonObject.getInt("status") == 1) {

			JSONObject data = jsonObject.getJSONObject("data");

			JSONArray cityArray = data.getJSONArray("cities");

			List<CityInfo> cityList = new ArrayList<CityInfo>();
			for (int i = 0; i < cityArray.length(); i++) {

				JSONObject cityObject = cityArray.getJSONObject(i);
				CityInfo city = new CityInfo();

				city.setName(cityObject.getString("name"));
				city.setId(cityObject.getString("id"));

				Log.e("name", cityObject.getString("name"));
				Log.e("id", cityObject.getString("id"));

				cityList.add(city);

			}

			detailsInfo.setCityList(cityList);

			JSONArray vehicleTypeArray = data.getJSONArray("vehicleTypes");

			List<VehicleInfo> vehicleList = new ArrayList<VehicleInfo>();
			for (int i = 0; i < vehicleTypeArray.length(); i++) {

				JSONObject vehicleObject = vehicleTypeArray.getJSONObject(i);
				VehicleInfo vehicleInfo = new VehicleInfo();

				vehicleInfo.setName(vehicleObject.getString("name"));
				vehicleInfo.setId(vehicleObject.getString("id"));

				Log.e("name", vehicleObject.getString("name"));
				Log.e("id", vehicleObject.getString("id"));

				vehicleList.add(vehicleInfo);

			}
			detailsInfo.setVehicleList(vehicleList);

			JSONArray memberTypeArray = data.getJSONArray("memberTypeList");

			List<MemberDetail> memberList = new ArrayList<MemberDetail>();

			for (int i = 0; i < memberTypeArray.length(); i++) {

				JSONObject memberJSON = memberTypeArray.getJSONObject(i);
				MemberDetail memberDetail = new MemberDetail();
				memberDetail.setName(memberJSON.getString("name"));
				memberDetail.setType(memberJSON.getString("type"));

				memberList.add(memberDetail);

			}
			detailsInfo.setMemberList(memberList);

			JSONObject setupConfigJSON = data
					.getJSONObject("setupConfiguration");
			SetupConfiguration setupConfiguration = new SetupConfiguration();

			setupConfiguration.setMaxReservationAtATime(setupConfigJSON
					.getInt("maxReservationAtATime"));
			setupConfiguration.setInVoicableReservation(setupConfigJSON
					.getInt("inVoicableReservation"));
			setupConfiguration.setInventorySlotMultiple(setupConfigJSON
					.getInt("inventorySlotMultiple"));
			setupConfiguration.setDeadSlotDeliveryMiJ(setupConfigJSON
					.getInt("deadSlotDeliveryMiJ"));
			setupConfiguration.setDeadSlotPickupMiJ(setupConfigJSON
					.getInt("deadSlotPickupMiJ"));
			setupConfiguration.setMinPinningTime(setupConfigJSON
					.getInt("minPinningTime"));
			setupConfiguration.setMinPeekTime(setupConfigJSON
					.getInt("minPeekTime"));
			setupConfiguration.setMaxPeekTime(setupConfigJSON
					.getInt("maxPeekTime"));
			setupConfiguration.setAfterReservationDeadSlotFleet(setupConfigJSON
					.getInt("afterReservationDeadSlotFleet"));
			setupConfiguration.setMinNewReservationDuration(setupConfigJSON
					.getInt("minNewReservationDuration"));
			setupConfiguration.setMaxContinuousReservationTime(setupConfigJSON
					.getInt("maxContinuousReservationTime"));
			setupConfiguration.setMaxTotalReservationDuration(setupConfigJSON
					.getInt("maxTotalReservationDuration"));
			setupConfiguration.setConsumerMemberGroup(setupConfigJSON
					.getString("consumerMemberGroup"));
			setupConfiguration.setDailyRateHours(setupConfigJSON
					.getInt("dailyRateHours"));
			
			detailsInfo.setSetupConfiguration(setupConfiguration);

		}

		return detailsInfo;
	}

	@Override
	public ArrayList<Model> parse(String resp) throws JSONException {

		return null;
	}

}
