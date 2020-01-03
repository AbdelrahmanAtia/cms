package org.javaworld.cmsbackend.logging;

public class RequestInfo {

	private String requestTime;
	private String method;
	private String remoteAddr;
	private String localAddr;
	private String authType;
	private String contentType;
	private String url;
	private String payload;

	public RequestInfo() {

	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getLocalAddr() {
		return localAddr;
	}

	public void setLocalAddr(String localAddr) {
		this.localAddr = localAddr;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("*************************** Request Info ***************************");
		builder.append("\n");
		builder.append("requestTime = " + requestTime);
		builder.append("\n");
		builder.append("Method = " + method);
		builder.append("\n");
		builder.append("remoteAddr = " + remoteAddr);
		builder.append("\n");
		builder.append("localAddr = " + localAddr);
		builder.append("\n");
		builder.append("authType = " + authType);
		builder.append("\n");
		builder.append("contentType = " + contentType);
		builder.append("\n");
		builder.append("url = " + url);
		builder.append("\n");
		builder.append("payload = " + payload);
		builder.append("\n");
		builder.append("********************************************************************");
		return builder.toString();
	}

}
