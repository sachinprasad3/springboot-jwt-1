package com.silent.model;

public class UserResponse {
	
	private String token;
	private String message;
	
	
	public UserResponse() {
	}
	
	public UserResponse(String token, String message) {
		super();
		this.token = token;
		this.message = message;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
