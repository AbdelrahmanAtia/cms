package org.javaworld.cmsbackend.logging;

public class ResponseInfo {
	
	private int statusCode;
	private String payload;
	
	public ResponseInfo() {
		
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
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
		builder.append("*************************** Response Info **************************");
		builder.append("\n");
		builder.append("status code = " + statusCode);
		builder.append("\n");
		builder.append("Payload = " + payload);
		builder.append("\n");
		builder.append("********************************************************************");
		return builder.toString();
	}	

}
