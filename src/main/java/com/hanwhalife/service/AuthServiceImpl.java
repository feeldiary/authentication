package com.hanwhalife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanwhalife.config.JwtTokenUtil;
import com.hanwhalife.entity.JwtRefreshToken;
import com.hanwhalife.entity.JwtTokenSet;
import com.hanwhalife.entity.UserInfo;
import com.hanwhalife.repository.RefreshTokenRepository;
import com.hanwhalife.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService{
	

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RefreshTokenRepository refreshTokenRepository;


	public JwtTokenSet loginProc(String userId, String password) 
	{
		
//		UserInfo userInfo = userRepository.findByUserIdAndPassword(userId, password);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId("test");
		userInfo.setUserNm("tester");
		userInfo.setPassword("1q2w3e");
		
		JwtTokenSet tokenSet = new JwtTokenSet();

		if(userInfo != null) {
//			return userInfo;
			tokenSet = jwtTokenUtil.createTokenSet(userInfo);
			
		}else {
//			throw new UsernameNotFoundException("User not found with userId: " + userId);
		}
		
		return tokenSet;
	}

	
	public String renewalAccessToken(String userId, String refreshToken)
	{
		JwtRefreshToken token = refreshTokenRepository.findByUserIdAndToken(userId, refreshToken);
		if(token == null) {
			// throw exception
		}
		UserInfo userInfo = userRepository.findByUserId(userId);
		if(userInfo != null) {
			// throw exception
		}
		String accessToken = jwtTokenUtil.getAccessToken(userInfo);
		
		return accessToken;
		
	}

}
