package com.coffeebean.mobile.rest.server.core.ext;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.BeansException;
import org.springframework.oxm.Marshaller;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.xml.MarshallingView;

public class MarshallingViewEx extends MarshallingView {

	public static final String DEFAULT_CONTENT_TYPE = "application/xml";

	private Marshaller marshaller;

	private String modelKey;

	public MarshallingViewEx(Marshaller marshaller) {
		Assert.notNull(marshaller, "'marshaller' must not be null");
		setContentType(DEFAULT_CONTENT_TYPE);
		this.marshaller = marshaller;
		setExposePathVariables(false);
	}

	public void setMarshaller(Marshaller marshaller) {
		Assert.notNull(marshaller, "'marshaller' must not be null");
		this.marshaller = marshaller;
	}

	public void setModelKey(String modelKey) {
		this.modelKey = modelKey;
	}

	@Override
	protected void initApplicationContext() throws BeansException {
		Assert.notNull(marshaller, "Property 'marshaller' is required");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Object toBeMarshalled = locateToBeMarshalled(model);
		if (toBeMarshalled == null) {
			throw new ServletException("Unable to locate object to be marshalled in model: " + model);
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream(2048);
		marshaller.marshal(toBeMarshalled, new StreamResult(bos));

		setResponseContentType(request, response);
		response.setContentLength(bos.size());
		FileCopyUtils.copy(bos.toByteArray(), response.getOutputStream());
	}

	@Override
	protected Object locateToBeMarshalled(Map<String, Object> model) throws ServletException {
		if (this.modelKey != null) {
			Object o = model.get(this.modelKey);
			if (o == null) {
				throw new ServletException("Model contains no object with key [" + modelKey + "]");
			}
			if (!this.marshaller.supports(o.getClass())) {
				throw new ServletException("Model object [" + o + "] retrieved via key [" + modelKey + "] is not supported by the Marshaller");
			}
			return o;
		}
		for (Object o : model.values()) {
			if (o instanceof BindingResult)
				continue;
			if (o != null && this.marshaller.supports(o.getClass())) {
				return o;
			}
		}
		return model;
	}
}
