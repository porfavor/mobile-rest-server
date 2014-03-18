package com.coffeebean.mobile.rest.server.core.exception;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class FieldErrorCompose {

	private boolean success = false; 
	private R R;
	
	private List<FieldError> fieldErrors = new ArrayList<FieldError>();
	
	public FieldErrorCompose(){
		this.R = new R("400", "参数错误");
	}

	public void addFieldError(String path, String message) {
		FieldError error = new FieldError(path, message);
		fieldErrors.add(error);
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@JSONField(name="r")
	public R getR() {
		return R;
	}

	public void setR(R r) {
		this.R = r;
	}
}
