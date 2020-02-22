package org.javaworld.cmsbackend.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginInfo {

	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;

	public LoginInfo() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginInfo [email=" + email + ", password=" + password + "]";
	}

}
