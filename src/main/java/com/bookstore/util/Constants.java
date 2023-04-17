package com.bookstore.util;

public class Constants {

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
}
