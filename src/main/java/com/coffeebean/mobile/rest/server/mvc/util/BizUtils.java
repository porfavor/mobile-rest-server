package com.coffeebean.mobile.rest.server.mvc.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class BizUtils {

	public static Boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		if (o instanceof Collection) {
			Collection<?> col = (Collection<?>) o;
			if (col.size() == 0) {
				return true;
			}
		}
		if (o instanceof Character) {
			Character ch = (Character) o;
			if (ch.charValue() == '\0') {
				return true;
			}
		}
		return StringUtils.isEmpty(String.valueOf(o))
				|| String.valueOf(o).equals("null")
				|| String.valueOf(o).equals("0");
	}

	public static List<Integer> convert(int[] ids) {
		List<Integer> newIds = new ArrayList<Integer>();
		for (int id : ids) {
			newIds.add(id);
		}
		return newIds;
	}

	public static boolean isDigitOrLetter(String val) {
		if (val == null)
			return false;
		Pattern p = Pattern.compile("^[a-zA-Z0-9]{6,8}$");
		Matcher m = p.matcher(val);
		return m.find();
	}

	public static String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		for (char letter : name.toCharArray()) {
			if (Character.isUpperCase(letter)) {
				result.append("_");
				result.append(String.valueOf(letter).toLowerCase());
			} else {
				result.append(letter);
			}
		}
		return result.toString();
	}

	public static void main(String[] args) {
		System.out.println(isDigitOrLetter(null));
	}

}
