/*
package org.javaworld.cmsbackend.logging;

import java.io.BufferedReader;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
public class CustomHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out
				.println("************************************** REST REQUEST **************************************");
		System.out.println("Method = " + request.getMethod());
		System.out.println("URL = " + request.getRequestURL());
		System.out.println("Auth type = " + request.getAuthType());
		System.out.println("Character Encoding = " + request.getCharacterEncoding());
		System.out.println("Content Type = " + request.getContentType());
		System.out.println("Protocol = " + request.getProtocol());
		// System.out.println(request.getReader());
		HttpServletRequest requestCacheWrapperObject = new ContentCachingRequestWrapper(request);
		requestCacheWrapperObject.getParameterMap(); // needed for caching!!
		BufferedReader br = requestCacheWrapperObject.getReader();
		String s = null;

		while ((s = br.readLine()) != null) {

			System.out.println(s);
		}
		System.out.println();
		// String requestBody =
		// requestCacheWrapperObject.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		// System.out.println(requestBody);
		// Read inputStream from requestCacheWrapperObject and log it
		System.out
				.println("******************************************************************************************");
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
	}

}
*/
