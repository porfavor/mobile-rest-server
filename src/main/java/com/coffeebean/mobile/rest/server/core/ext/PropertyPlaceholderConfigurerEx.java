package com.coffeebean.mobile.rest.server.core.ext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import com.coffeebean.mobile.rest.server.core.Global;

public class PropertyPlaceholderConfigurerEx extends PropertyPlaceholderConfigurer {

	private boolean ignoreResourceNotFound = false;

//	private String fileEncoding="UTF-8";

	private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();

	private String baseDir = Global.BASE_DIR;
	
	private String[] properties;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	protected void loadProperties(Properties props) throws IOException {
		
		for(String propertie:properties){
			Resource location = new FileSystemResource(baseDir+propertie);
			if(!location.exists()){
				location = new ClassPathResource(propertie);
			}
			LOG.debug("Load config file from {}",location.getURL().getPath());
			InputStream is = null;
			try {
				is = location.getInputStream();
				String filename = null;
				try {
					filename = location.getFilename();
				} catch (IllegalStateException ex) {
					// resource is not file-based. See SPR-7552.
				}
				if (filename != null && filename.endsWith(".xml")) {
					this.propertiesPersister.loadFromXml(props, is);
				} else {
					this.propertiesPersister.load(props, is);
//					if (this.fileEncoding != null) {
//						this.propertiesPersister.load(props, new InputStreamReader(is, this.fileEncoding));
//					} else {
//						this.propertiesPersister.load(props, is);
//					}
				}
			} catch (IOException ex) {
				if (this.ignoreResourceNotFound) {
					if (logger.isWarnEnabled()) {
						logger.warn("Could not load properties from " + location + ": " + ex.getMessage());
					}
				} else {
					throw ex;
				}
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public void setProperties(String[] properties) {
		this.properties = properties;
	}
}
