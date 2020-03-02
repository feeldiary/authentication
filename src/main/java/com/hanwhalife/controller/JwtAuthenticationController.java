package com.hanwhalife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hanwhalife.config.JwtTokenUtil;
import com.hanwhalife.entity.JwtRequest;
import com.hanwhalife.entity.JwtResponse;
import com.hanwhalife.entity.UserInfo;
import com.hanwhalife.service.JwtUserDetailsService;

//import com.example.template.JwtRequest;
//import com.example.template.JwtResponse;
//import com.javainuse.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/loginProc", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception 
	{
		authenticate(jwtRequest.getUserId(), jwtRequest.getPassword());
		// 필요 시, UserDetails 상속 객체 생성
		final UserInfo userInfo = userDetailsService.loadUserByUsername(jwtRequest.getUserId());
		final String token = jwtTokenUtil.generateToken(userInfo);

		return ResponseEntity.ok(new JwtResponse(token));
	}


	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserInfo userInfo) throws Exception 
	{
//		userDetailsService.save(jwtRequest.getUsername(), jwtRequest.getPassword());

		return ResponseEntity.ok(userInfo);
	}

	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}