package com.coffeebean.mobile.rest.server.core;

public class Env {
	public static class SysParam{
		public static class SmsPlatform{
			public static final int YP = 1;
			public static final int WJ = 2;
		}
	}
	
	public static int SYS_SMS_PLARFORM = SysParam.SmsPlatform.YP;
}
