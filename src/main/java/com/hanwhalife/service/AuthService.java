package com.hanwhalife.service;

import com.hanwhalife.entity.JwtTokenSet;

public interface AuthService {

	public JwtTokenSet loginProc(String userId, String password);
	
	public String renewalAccessToken(String userId, String refreshToken);
}
