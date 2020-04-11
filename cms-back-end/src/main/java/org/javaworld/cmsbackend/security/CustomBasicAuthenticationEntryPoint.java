package org.javaworld.cmsbackend.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javaworld.cmsbackend.model.Response;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
			throws IOException, ServletException {

		System.out.println("statrting CustomBasicAuthenticationEntryPoint.commence()");
		System.out.println("An error during authentication happened");
		System.out.println("request url = " + request.getRequestURL());
		Response customResponse = new Response();
		customResponse.setStatus(false);
		customResponse.setMessage("invalid username or password");

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(customResponse);

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		
		PrintWriter writer = response.getWriter();
		writer.println(jsonString);
	}

	public void afterPropertiesSet() throws Exception {
		setRealmName("dev");
	}

}