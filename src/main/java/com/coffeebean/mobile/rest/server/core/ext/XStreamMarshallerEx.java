package com.coffeebean.mobile.rest.server.core.ext;

import org.springframework.oxm.xstream.XStreamMarshaller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XStreamMarshallerEx extends XStreamMarshaller {

	private XStream xstream = new XStream() {
		@Override
		protected MapperWrapper wrapMapper(MapperWrapper next) {
			return new MapperWrapper(next) {
				@SuppressWarnings("rawtypes")
				@Override
				public boolean shouldSerializeMember(Class definedIn, String fieldName) {
					if (definedIn == Object.class) {
						try {
							return this.realClass(fieldName) != null;
						} catch (Exception e) {
							return false;
						}
					} else {
						return super.shouldSerializeMember(definedIn, fieldName);
					}
				}
			};
		}
	};
	
	public XStream getXStream() {
		return this.xstream;
	}

}
