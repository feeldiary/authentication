package com.hanwhalife.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hanwhalife.config.JwtTokenUtil;
import com.hanwhalife.entity.JwtRequest;
import com.hanwhalife.entity.UserInfo;
import com.hanwhalife.service.AuthService;
import com.hanwhalife.service.JwtUserDetailsService;
//import com.hanwhalife.service.JwtUserDetailsService;

//import com.example.template.JwtRequest;
//import com.example.template.JwtResponse;
//import com.javainuse.service.JwtUserDetailsService;

@RestController
@CrossOrigin
@RequestMapping("/auth/")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private AuthService authService;

	@Value("${jwt.tokenHeader}")
	private String TOKEN_HEADER;	//Authorization
	
/*
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest
									, HttpServletResponse response) throws Exception 
	{
//		final String token = jwtTokenUtil.generateToken(jwtRequest.getUserId(), jwtRequest.getPassword());
		JwtTokenSet tokenSet = authService.loginProc(jwtRequest.getUserId(), jwtRequest.getPassword());
//		response.setHeader(TOKEN_HEADER, tokenSet);
//		return ResponseEntity.ok(new JwtResponse(tokenSet));
		return ResponseEntity.ok(tokenSet);
	}
*/

	@Deprecated
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserInfo userInfo) throws Exception 
	{
		userDetailsService.save(userInfo);
		return ResponseEntity.ok(userInfo);
	}

	@RequestMapping(value = "/renewalAccessToken", method = RequestMethod.POST)
	public ResponseEntity<?> renewalAccessToken(@RequestBody JwtRequest jwtRequest
												, HttpServletRequest request
												, HttpServletResponse response) throws Exception 
	{
		final String requestTokenHeader = request.getHeader(TOKEN_HEADER);
		
		String accessToken = authService.renewalAccessToken(jwtRequest.getUserId(), requestTokenHeader);
//		final UserDetails userInfo = userDetailsService.loadUserByUsername(jwtRequest.getUserId());
//		final String token = jwtTokenUtil.generateToken(userInfo);

		return ResponseEntity.ok(accessToken);
	}


	@RequestMapping(value = "/getRefreshToken", method = RequestMethod.POST)
//    @RequestMapping(value="/api/auth/token", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  
	{
		final String requestTokenHeader = request.getHeader(TOKEN_HEADER);
		
		/*
//		String accessToken = authService.renewalAccessToken(jwtRequest.getUserId(), requestTokenHeader);
		JwtTokenSet tokenSet = authService.loginProc(userInfo.getUserId(), userInfo.getPassword());
//		final String token = jwtTokenUtil.generateToken(userInfo);
	        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME));
	        
	        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
	        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());

	        String jti = refreshToken.getJti();
	        if (!tokenVerifier.verify(jti)) {
	            throw new InvalidJwtToken();
	        }

	        String subject = refreshToken.getSubject();
	        User user = userService.getByUsername(subject).orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

	        if (user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
	        List<GrantedAuthority> authorities = user.getRoles().stream()
	                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
	                .collect(Collectors.toList());

	        UserContext userContext = UserContext.create(user.getUsername(), authorities);

	        return tokenFactory.createAccessJwtToken(userContext);
	    }		
		*/
		
		return ResponseEntity.ok(null);
	}

	
	
	/*
	@Deprecated
	@RequestMapping(value = "/loginProc", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception 
	{
		authenticate(jwtRequest.getUserId(), jwtRequest.getPassword());
		// 필요 시, UserDetails 상속 객체 생성
		final UserDetails userInfo = userDetailsService.loadUserByUsername(jwtRequest.getUserId());
		final String token = jwtTokenUtil.generateToken(userInfo);

		return ResponseEntity.ok(new JwtResponse(token));
	}
*/
/*	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	*/
}