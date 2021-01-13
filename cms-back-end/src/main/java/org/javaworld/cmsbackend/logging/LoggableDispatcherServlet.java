package org.javaworld.cmsbackend.logging;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javaworld.cmsbackend.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

public class LoggableDispatcherServlet extends DispatcherServlet {

	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private AuditInfo auditInfo;
	
	private static final Logger logger = LoggerFactory.getLogger(LoggableDispatcherServlet.class);

	@Override
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("starting doDispatch..");
		logger.info(request.getRequestURL().toString());

		if (!(request instanceof ContentCachingRequestWrapper)) {
			request = new ContentCachingRequestWrapper(request);
		}

		if (!(response instanceof ContentCachingResponseWrapper)) {
			response = new ContentCachingResponseWrapper(response);
		}

		try {
			auditInfo.init();
			super.doDispatch(request, response);
		}
		// catch(Exception e) {
		// logger.info("message = " + e.getMessage());
		// e.printStackTrace();
		// }
		finally {
			
			auditInfo.persist();
			
			if (!request.getRequestURI().contains("/configs/logs/")) {
				logRequest(request);
				logResponse(response);
			}
			updateResponse(response);

		}
	}

	private void logRequest(HttpServletRequest requestToCache) {
		logger.info("******************************** Request Info *****************************");

		// print request type and full url
		StringBuilder requestInfo = new StringBuilder();
		String requestMethod = requestToCache.getMethod();

		requestInfo.append(requestMethod);
		requestInfo.append("  " + requestToCache.getRequestURL());

		//append request params if exist
		String requestParams = requestToCache.getQueryString();
		if (requestParams != null) {
			requestInfo.append("?" + requestParams);
		}

		logger.info(requestInfo.toString());

		// print request body
		if (requestMethod.equals("POST") || requestMethod.equals("PUT")) {
			logger.info("PAYLOAD: ");
			logger.info(StringUtil.removeNewLineCharacters(getRequestPayload(requestToCache)));
		}

		// print request headers
		logger.info("HEADERS: ");
		Enumeration<String> headerNames = requestToCache.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String headerValue = requestToCache.getHeader(headerName);
			if (headerName.toLowerCase().equals("authorization")) {
				String[] splittedStr = headerValue.split(" ");
				headerValue = (splittedStr.length > 0) ? splittedStr[0] : "";
			}
			logger.info("	" + headerName + " = " + headerValue);
		}
	}

	private void logResponse(HttpServletResponse response) {
		logger.info("******************************** Response Info ****************************");
		logger.info("STATUS CODE = " + response.getStatus());
		
		// print response headers
		logger.info("HEADERS: ");
		Collection<String> headerNames = response.getHeaderNames();
		for(String headerName : headerNames) {
			String headerVal = response.getHeader(headerName);
			logger.info("	" + headerName + " = " + headerVal);
		}
		
		logger.info("PAYLOAD: ");
		logger.info(StringUtil.removeNewLineCharacters(getResponsePayload(response)));
		logger.info("***************************************************************************");
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