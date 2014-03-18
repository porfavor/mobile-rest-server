package com.coffeebean.mobile.rest.server.core.exception;

public class BizException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2417867819355182508L;
	
	private String descr;
	
	public BizException(String str){
		super(400+"");
		this.descr = str;
	}
	
	public BizException(String str,String descr){
		super(str);
		this.descr = descr;
	}
	
	public String getDescr() {
		return descr;
	}

}
