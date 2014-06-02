package com.micar.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.micar.model.AddressInfo;
import com.micar.model.LoginInfo;
import com.micar.model.Model;

public class LoginParser implements Parser<Model> {

	@Override
	public Model parse(JSONObject jsonObject) throws JSONException {

		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setStatus(jsonObject.getInt("status"));
		loginInfo.setMessage(jsonObject.getString("message"));
		if (jsonObject.getInt("status") == 1) {

			loginInfo.setAccessToken(jsonObject.getString("accessToken"));

			JSONObject data = jsonObject.getJSONObject("data");

			loginInfo.setMemberId(data.getString("memberId"));
			loginInfo.setFullName(data.getString("fullName"));
			loginInfo.setMemberPlan(data.getString("memberPlan"));
			loginInfo.setMemberPlanId(data.getString("memberPlanId"));
			loginInfo.setMemberType(data.getString("memberType"));
			loginInfo.setFirstName(data.getString("firstName"));
			loginInfo.setLastName(data.getString("lastName"));
			loginInfo.setReferralCode(data.getString("referralCode"));

			JSONArray addressesArray = data.getJSONArray("addresses");

			List<AddressInfo> addressList = new ArrayList<AddressInfo>();
			for (int i = 0; i < addressesArray.length(); i++) {

				JSONObject addressObject = addressesArray.getJSONObject(i);

				AddressInfo addressInfo = new AddressInfo();
				addressInfo.setName(addressObject.getString("name"));
				addressInfo.setId(addressObject.getString("id"));
				addressInfo.setFavorite(addressObject
						.getBoolean("isFavAddress"));

				addressList.add(addressInfo);

				if (addressObject.getBoolean("isFavAddress")) {

					loginInfo.setFavAddress(addressObject
							.getString("name"));
					loginInfo.setFavAddressId(addressObject
							.getString("id"));

				}
			}

			loginInfo.setAddressList(addressList);

		}

		return loginInfo;
	}

	@Override
	public ArrayList<Model> parse(String resp) throws JSONException {

		return null;
	}

}
