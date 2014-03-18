package com.coffeebean.mobile.rest.server.core.exception;

public class BizError {
	
	private boolean success = false;
	private R R;

	public BizError(String field, String message) {
		this.R = new R(field, message);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public R getR() {
		return R;
	}

	public void setR(R r) {
		this.R = r;
	}
}
