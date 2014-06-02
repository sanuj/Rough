package com.logindemo.utils;

import android.widget.EditText;

public class EditTxtUtil {

	public static boolean isEmpty(EditText editTxt) {

		if ((editTxt.getText().toString()).matches(""))
			return true;

		return false;
	}

	public static boolean isEmail(EditText editTxt) {

		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		if (editTxt.getText().toString().matches(EMAIL_PATTERN))
			return true;

		return false;
	}
	
	public static boolean confirmPassword(EditText pswd, EditText confPswd) {
		
		String password = pswd.getText().toString();
		String confPassword = confPswd.getText().toString();
		
		if (password.equals(confPassword))
			return true;
		
		return false;
	}

}
