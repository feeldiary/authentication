package com.hanwhalife.entity;

import lombok.ToString;

@ToString
public class JwtTokenSet {

	private String accessToken;
	private String refreshToken;


	public JwtTokenSet() {
	}

//	private JwtTokenSet() {
//	}

//	public static JwtTokenSet create() {
//		return new JwtTokenSet();
//	}

	public JwtTokenSet accessToken(String token) {
		this.setAccessToken(token);
		return this;
	}

	public JwtTokenSet refreshToken(String token) {
		this.setRefreshToken(token);
		return this;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
