package org.javaworld.cmsbackend.logging;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javaworld.cmsbackend.util.StringUtil;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

public class LoggableDispatcherServlet extends DispatcherServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("starting doDispatch..");
		System.out.println(request.getRequestURL().toString());

		if (!(request instanceof ContentCachingRequestWrapper)) {
			request = new ContentCachingRequestWrapper(request);
		}

		if (!(response instanceof ContentCachingResponseWrapper)) {
			response = new ContentCachingResponseWrapper(response);
		}

		try {
			super.doDispatch(request, response);
		}
		// catch(Exception e) {
		// System.out.println("message = " + e.getMessage());
		// e.printStackTrace();
		// }
		finally {
			logRequest(request);
			logResponse(response);
			updateResponse(response);
		}
	}

	private void logRequest(HttpServletRequest requestToCache) {
		System.out.println("******************************** Request Info *****************************");

		// print request type and full url
		StringBuilder requestInfo = new StringBuilder();
		String requestMethod = requestToCache.getMethod();

		requestInfo.append(requestMethod);
		requestInfo.append("  " + requestToCache.getRequestURL());

		String requestParams = requestToCache.getQueryString();
		if (requestParams != null) {
			requestInfo.append("?" + requestParams);
		}

		System.out.println(requestInfo);

		// print request body
		if (requestMethod.equals("POST") || requestMethod.equals("PUT")) {
			System.out.println("PAYLOAD: ");
			System.out.println(StringUtil.prettyJson(getRequestPayload(requestToCache)));
		}

		// print request headers
		System.out.println("HEADERS: ");
		Enumeration<String> headerNames = requestToCache.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String headerValue = requestToCache.getHeader(headerName);
			if (headerName.toLowerCase().equals("authorization")) {
				String[] splittedStr = headerValue.split(" ");
				headerValue = (splittedStr.length > 0) ? splittedStr[0] : "";
			}
			System.out.println("	" + headerName + " = " + headerValue);
		}
	}

	private void logResponse(HttpServletResponse response) {
		System.out.println("******************************** Response Info ****************************");
		System.out.println("STATUS CODE = " + response.getStatus());
		
		// print response headers
		System.out.println("HEADERS: ");
		Collection<String> headerNames = response.getHeaderNames();
		for(String headerName : headerNames) {
			String headerVal = response.getHeader(headerName);
			System.out.println("	" + headerName + " = " + headerVal);
		}
		
		System.out.println("PAYLOAD: ");
		System.out.println(StringUtil.prettyJson(getResponsePayload(response)));
		System.out.println("***************************************************************************");
	}

	private String getRequestPayload(HttpServletRequest request) {
		if (request.getMethod().equals("GET")) {
			return "GET METHOD .. No payload exist";
		}

		if (request.getMethod().equals("DELETE")) {
			return "DELETE METHOD .. No payload exist";
		}

		ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
		if (wrapper != null) {
			byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length > 0) {
				try {
					return new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
				} catch (UnsupportedEncodingException ex) {
					// NOOP
				}
			}
		}
		return "";
	}

	private String getResponsePayload(HttpServletResponse response) {
		ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response,
				ContentCachingResponseWrapper.class);
		if (wrapper != null) {
			byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length > 0) {
				int length = Math.min(buf.length, 5120);
				try {
					return new String(buf, 0, length, wrapper.getCharacterEncoding());
				} catch (UnsupportedEncodingException ex) {
					// NOOP
				}
			}
		}
		return "";
	}

	private void updateResponse(HttpServletResponse response) throws IOException {
		ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response,
				ContentCachingResponseWrapper.class);
		responseWrapper.copyBodyToResponse();
	}

}