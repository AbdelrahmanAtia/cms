package org.javaworld.cmsbackend.model;

public class Response {

	private boolean status;
	private String message;

	public Response() {

	}

	public Response(boolean status, String message) {
		this.status = status;
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Response [status=");
		builder.append(status);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}

}
