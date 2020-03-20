package org.javaworld.cmsbackend.logging;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javaworld.cmsbackend.util.DateUtil;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;
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
		try {
			super.doDispatch(request, response);
		} 
		//catch(Exception e) {
		//	System.out.println("message = " + e.getMessage());
		//	e.printStackTrace();
		//}
		finally {
			logRequest(request);
		}
	}

	private void logRequest(HttpServletRequest requestToCache) {
		RequestInfo requestInfo = new RequestInfo();
		requestInfo.setRequestTime(DateUtil.getCurrentDate("dd/MM/yyyy HH:mm:ss"));
		requestInfo.setMethod(requestToCache.getMethod());
		requestInfo.setRemoteAddr(requestToCache.getRemoteAddr());		
		requestInfo.setLocalAddr(requestToCache.getLocalAddr());
		requestInfo.setAuthType(getAuthType(requestToCache));
		requestInfo.setContentType(requestToCache.getContentType());
		requestInfo.setUrl(requestToCache.getRequestURL().toString());
		requestInfo.setPayload(getRequestPayload(requestToCache));
		System.out.println(requestInfo);
	}

	private String getAuthType(HttpServletRequest requestToCache) {
		String authType = null;
		String authHeader = requestToCache.getHeader("Authorization");
		if(authHeader != null) {
			String[]  authHeaderSplitted= authHeader.split(" ");
			if (authHeaderSplitted.length > 1) {
				authType = authHeaderSplitted[0];
			}
		}
		return authType;
	}

	private String getRequestPayload(HttpServletRequest request) {
		if(request.getMethod().equals("GET")) {
			return "get method..no payload";
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
}