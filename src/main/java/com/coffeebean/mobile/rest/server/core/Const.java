package com.coffeebean.mobile.rest.server.core;

public class Const {
	public static long MILLS_IN_DAY = 1000 * 60 * 60 * 24;
	public static int SMS_SIZE = 53;

	public static class ShopUserRole {
		public static final int ADMIN = 1;
		public static final int SHOP = 2;
		public static final int CLERK = 3;
	}
	
	public static class TaskMethods {
		public static final int SMS = 1;
		public static final int SKIP = 2;
		public static final int PHONE = 3;
	}
	
	public static class SmsTplStatus {
		public static final int REJECT = -1;
		public static final int NEW = 3;
		public static final int AUDIT = 1;
		public static final int PASS = 2;
	}
	
	public static class SmsTplDefault {
		public static final int FALSE = -1;
		public static final int TRUE = 1;
	}
	
	public static class YpTplStatus {
		public static final String FAIL = "FAIL";
		public static final String CHECKING = "CHECKING";
		public static final String SUCCESS = "SUCCESS";
	}
	
	public static class Error{
		public static final int ERROR_NO_SMS = -801;
	}
}
