package _org.javaworld.cmsbackend.security;

public class LoginInfo {

	private String userName;

	private String userPassword;

	public LoginInfo() {
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Override
	public String toString() {
		return "LoginInfo [userName=" + userName + ", userPassword=" + userPassword + "]";
	}

}
