package org.javaworld.cmsbackend.interceptor;

import org.hibernate.EmptyInterceptor;

public class CustomQueryInterceptor extends EmptyInterceptor {

	@Override
	public String onPrepareStatement(String sql) {
		System.out.println(">> Hibernate: " + sql);
		return sql;
	}
	
}
