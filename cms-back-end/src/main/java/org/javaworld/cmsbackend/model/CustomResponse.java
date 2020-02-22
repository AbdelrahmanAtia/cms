package org.javaworld.cmsbackend.model;

import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class CustomResponse extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	private static final String STATUS = "status";
	private static final String DESCRIPTION = "description";

	public CustomResponse() {

		put(STATUS, false);
		put(DESCRIPTION, "");
	}

	public void setStatus(boolean status) {
		put(STATUS, status);
	}

	public void setDescription(String description) {
		put(DESCRIPTION, description);
	}

	public void setResponseDetails(String key, Object value) {
		put(key, value);
	}

}
