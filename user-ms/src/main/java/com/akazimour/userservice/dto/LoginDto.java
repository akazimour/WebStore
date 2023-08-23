package com.akazimour.userservice.dto;

public class LoginDto {

	private String username;
	private String password;
	private String fbToken;

	public LoginDto() {
	}

	public LoginDto(String username, String password, String fbToken) {
		this.username = username;
		this.password = password;
		this.fbToken = fbToken;
	}

	public LoginDto(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFbToken() {
		return fbToken;
	}

	public void setFbToken(String fbToken) {
		this.fbToken = fbToken;
	}
}
