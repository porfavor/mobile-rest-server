package com.coffeebean.mobile.rest.server.core.ext;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.coffeebean.mobile.rest.server.core.exception.BizError;
import com.coffeebean.mobile.rest.server.core.exception.FieldErrorCompose;

public class FastJsonHttpMessageConverterEx extends FastJsonHttpMessageConverter {
	private Charset charset = UTF8;

	private SerializerFeature[] features = new SerializerFeature[0];

	public Charset getCharset() {
		return this.charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public SerializerFeature[] getFeatures() {
		return features;
	}

	public void setFeatures(SerializerFeature... features) {
		this.features = features;
	}

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		
		OutputStream out = outputMessage.getBody();
		byte[] bytes = null;
		
		if(obj instanceof BizError || obj instanceof FieldErrorCompose){
			String text = JSON.toJSONString(obj, features);
			bytes = text.getBytes(charset);
		} else {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("success", true);
			map.put("R", obj);
			String text = JSON.toJSONString(map, features);
			bytes = text.getBytes(charset);
		}
		out.write(bytes);
	}

}
