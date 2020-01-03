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
		if (!(request instanceof ContentCachingRequestWrapper)) {
			request = new ContentCachingRequestWrapper(request);
		}
		try {
			super.doDispatch(request, response);
		} finally {
			logRequest(request);
		}
	}

	private void logRequest(HttpServletRequest requestToCache) {
		RequestInfo requestInfo = new RequestInfo();
		requestInfo.setRequestTime(DateUtil.getCurrentDate("dd/MM/yyyy HH:mm:ss"));
		requestInfo.setMethod(requestToCache.getMethod());
		requestInfo.setRemoteAddr(requestToCache.getRemoteAddr());
		requestInfo.setLocalAddr(requestToCache.getLocalAddr());
		requestInfo.setAuthType(requestToCache.getAuthType());
		requestInfo.setContentType(requestToCache.getContentType());
		requestInfo.setUrl(requestToCache.getRequestURL().toString());
		requestInfo.setPayload(getRequestPayload(requestToCache));
		System.out.println(requestInfo);
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