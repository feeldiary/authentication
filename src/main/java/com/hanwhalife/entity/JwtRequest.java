package com.hanwhalife.entity;
import java.io.Serializable;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	private String userId;
	private String userNm;
	private String password;
	
	//need default constructor for JSON Parsing
	public JwtRequest()
	{
	}

	public JwtRequest(String userId, String password) {
		this.setUserId(userId);
		this.setPassword(password);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}