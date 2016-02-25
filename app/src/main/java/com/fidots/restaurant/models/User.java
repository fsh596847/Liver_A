package com.fidots.restaurant.models;

public class User {
	private String user_id;
	private String username;
	private String avatar;
	private String mobilephone_num;
	private String token;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getMobilephone_num() {
		return mobilephone_num;
	}
	public void setMobilephone_num(String mobilephone_num) {
		this.mobilephone_num = mobilephone_num;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
