package com.coffeebean.mobile.rest.server.core.ext;

import java.util.List;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coffeebean.mobile.rest.server.core.exception.BizError;
import com.coffeebean.mobile.rest.server.core.exception.BizException;
import com.coffeebean.mobile.rest.server.core.exception.FieldErrorCompose;


@ControllerAdvice
public class ControllerAdviceHandler {
	
	@ExceptionHandler(BizException.class)
	@ResponseBody
	public BizError processBizError(BizException ex) {
        return new BizError(ex.getMessage(),ex.getDescr());
    }
	
	@ExceptionHandler(BindException.class)
	@ResponseBody
	public FieldErrorCompose processValidationError(BindException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }
	
	private FieldErrorCompose processFieldErrors(List<FieldError> fieldErrors) {
		FieldErrorCompose dto = new FieldErrorCompose();
        for (FieldError fieldError: fieldErrors) {
            dto.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return dto;
    }

}
