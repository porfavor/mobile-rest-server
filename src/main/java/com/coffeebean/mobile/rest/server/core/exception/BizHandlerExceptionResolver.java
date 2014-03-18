package com.coffeebean.mobile.rest.server.core.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class BizHandlerExceptionResolver extends SimpleMappingExceptionResolver {

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	private String exceptionAttribute = DEFAULT_EXCEPTION_ATTRIBUTE;

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

		// Expose ModelAndView for chosen error view.
		String viewName = determineViewName(ex, request);

		if (viewName != null) {
			// Apply HTTP status code for error views, if specified.
			// Only apply it if we're processing a top-level request.
			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			}
			return getModelAndView(viewName, ex);
		} else {
			return null;
		}
	}

	/**
	 * Return a ModelAndView for the given view name and exception.
	 * <p>
	 * The default implementation adds the specified exception attribute. Can be
	 * overridden in subclasses.
	 * 
	 * @param viewName
	 *            the name of the error view
	 * @param ex
	 *            the exception that got thrown during handler execution
	 * @return the ModelAndView instance
	 * @see #setExceptionAttribute
	 */
	@Override
	protected ModelAndView getModelAndView(String viewName, Exception ex) {
		Exception exception = ex;

		ModelAndView mv = new ModelAndView(viewName);

		if(ex instanceof BizException){
			LOG.debug(ex.getMessage());
		} else {
			LOG.error(ex.getMessage(),ex);
		}
		
		if (this.exceptionAttribute != null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Exposing Exception as model attribute '" + this.exceptionAttribute + "'");
			}

			if (exception instanceof MissingServletRequestParameterException) {
				MissingServletRequestParameterException exp = (MissingServletRequestParameterException)exception;
				mv.addObject("field",500);
				mv.addObject("message","missing parameter:"+exp.getParameterName());
			} else if(exception instanceof MaxUploadSizeExceededException){
				MaxUploadSizeExceededException exp = (MaxUploadSizeExceededException)exception;
				mv.addObject("field",500);
				mv.addObject("message","limit file size:"+exp.getMaxUploadSize());
			} else if (exception instanceof BizException) {
				BizException bizException = (BizException) exception;
				mv.addObject("field", exception.getMessage());
				mv.addObject("message", bizException.getDescr());
			} else {
				mv.addObject("field",500);
				mv.addObject("message","unknow error");
			}
		}
		return mv;
	}
}
