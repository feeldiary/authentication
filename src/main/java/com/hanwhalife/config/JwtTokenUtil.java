package com.hanwhalife.config;


import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	@Value("${jwt.secretKey}")
	private String secretKey;

	//retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getAudience);	// getSubject
	}

	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
    //for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

	//check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	//generate token for user
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId",userDetails.getUsername());
		claims.put("userName","홍길동");
		claims.put("deptId","testDept");
		claims.put("deptNm","테스트부서");
		claims.put("authorities",userDetails.getAuthorities());
		return doGenerateToken(claims, userDetails.getUsername());
	}

	//while creating the token -
	//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	//2. Sign the JWT using the HS512 algorithm and secret key.
	//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	//   compaction of the JWT to a URL-safe string 
	private String doGenerateToken(Map<String, Object> claims, String userName) 
	{
		System.out.println("key : "+ secretKey);

		String jwtToken = Jwts.builder()
								.setHeaderParam("typ", "JWT")
								.setAudience(userName)
								.setIssuer("www.hanwhalife.com")
//								.setId("")	// token id
//								.setSubject(userName)
								.setIssuedAt(new Date(System.currentTimeMillis()))	// 생성시간
								.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))	// 만료시간
								.setClaims(claims)

//								.signWith(SignatureAlgorithm.HS512, this.generateKey())
								.signWith(SignatureAlgorithm.HS512, secretKey)	// SignatureAlgorithm.HS256
								
								.compact();

		System.out.println("jwtToken : " + jwtToken);

		return jwtToken; 
	}


	private byte[] generateKey()
	{
		byte[] key = null;
		try {
			key = secretKey.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
//			if(log.isInfoEnabled()){
				e.printStackTrace();
//			}else{
//				log.error("Making JWT Key Error ::: {}", e.getMessage());
//			}
		}
		
		return key;
	}


	//validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}