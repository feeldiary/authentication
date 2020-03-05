package com.hanwhalife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hanwhalife.entity.JwtTokenSet;
import com.hanwhalife.entity.UserInfo;
import com.hanwhalife.service.AuthService;

@RestController
public class LoginController {

	@Autowired
	private AuthService authService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody UserInfo userInfo) throws Exception 
	{
		JwtTokenSet tokenSet = authService.loginProc(userInfo.getUserId(), userInfo.getPassword());
		
//		final String token = jwtTokenUtil.generateToken(userInfo);
//		System.out.println(tokenSet.toString());
		return ResponseEntity.ok(tokenSet);
	}

/*
	@RequestMapping(value = "/getRefreshToken", method = RequestMethod.POST)
	public ResponseEntity<?> getRefreshToken(@RequestBody UserInfo userInfo) throws Exception 
	{
		JwtTokenSet tokenSet = authService.loginProc(userInfo.getUserId(), userInfo.getPassword());
		
//		final String token = jwtTokenUtil.generateToken(userInfo);
		return ResponseEntity.ok(tokenSet);
	}
*/
	
}
