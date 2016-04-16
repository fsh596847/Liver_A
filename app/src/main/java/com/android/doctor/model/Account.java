package com.android.doctor.model;

public class Account {
	private String avatar;
	private String mobilephone_num;
	private String token;
	private String username;
	private String retail_id;
	private int lifecycle_status;
	
	public Account (String phone, String token, String user){
		this.mobilephone_num = phone;
		this.token = token;
		this.username = user;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getLifecycle_status() {
		return lifecycle_status;
	}

	public void setLifecycle_status(int lifecycle_status) {
		this.lifecycle_status = lifecycle_status;
	}
	
	
	public String getRetail_id() {
		return retail_id;
	}

	public void setRetail_id(String retail_id) {
		this.retail_id = retail_id;
	}
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "Account [mobilephone_num=" + mobilephone_num + ", token="
				+ token + ", username=" + username + ", retail_id=" + retail_id
				+ ", lifecycle_status=" + lifecycle_status + "]";
	}

}
