package org.javaworld.cmsbackend.interceptor;

import org.hibernate.EmptyInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomQueryInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 1L;
	
	Logger logger = LoggerFactory.getLogger(CustomQueryInterceptor.class);

	@Override
	public String onPrepareStatement(String sql) {
		logger.info(">> Hibernate: " + sql);
		return sql;
	}

}
