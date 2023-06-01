package com.bookstore.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constants {
	public static final Pattern VALID_EMAIL  = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	public static final Pattern VALID_NUMBER = Pattern.compile("\\d+", Pattern.CASE_INSENSITIVE);

	public static String standardized(String s) {
		String res = s.replaceAll("[àáảãạăắằẳẵặâấầẩẫậ]", "a")
				         .replaceAll("[đ]", "d")
				         .replaceAll("[èéẻẽẹêếềểễệ]", "e")
				         .replaceAll("[ìíỉĩị]", "i")
				         .replaceAll("[òóỏõọôốồổỗộơớờởỡợ]", "o")
				         .replaceAll("[ùúủũụưứừửữự]", "u")
				         .replaceAll("[ỳýỷỹỵ]", "y")
				         .replaceAll("[Đ]", "D");
		return res.toLowerCase().trim().replaceAll(" ", "-");
	}
	
	// check validate email
	public static boolean validateEmail(String email) {
		Matcher matcher = VALID_EMAIL.matcher(email);
		return matcher.find();
	}

	// check validate phoneNumber
	public static boolean validatePhoneNumber(String phoneNumber) {
		Matcher matcher = VALID_NUMBER.matcher(phoneNumber);
		return matcher.find();
	}
}
