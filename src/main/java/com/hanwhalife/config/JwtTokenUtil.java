package com.hanwhalife.config;


import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hanwhalife.entity.JwtRefreshToken;
import com.hanwhalife.entity.JwtTokenSet;
import com.hanwhalife.entity.UserInfo;
import com.hanwhalife.repository.RefreshTokenRepository;
import com.hanwhalife.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


enum TokenType {
	ACCESS_TOKEN
	, REFRESH_TOKEN
}

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	public static final long ACCESS_TOKEN_VALIDITY = 10 * 60 * 1000;
	public static final long REFRESH_TOKEN_VALIDITY = 30 * 60 * 1000;	// 5 * 60 * 60

	@Value("${jwt.accessSecretKey}")
	private String ACCESS_SECRET_KEY;

	@Value("${jwt.refreshSecretKey}")
	private String REFRESH_SECRET_KEY;

	@Autowired
	RefreshTokenRepository refreshTokenRepository;

	@Autowired
	UserRepository UserRepository;

	public JwtTokenSet createTokenSet(UserInfo user) 
	{
		JwtTokenSet tokenSet = new JwtTokenSet();

		String strAccessToken = getAccessToken(user);
		String strRefreshToken = getRefreshToken(user);

		/** TODO refreshToken DB 등록 **/
		JwtRefreshToken jwtRefreshToken = new JwtRefreshToken();
		jwtRefreshToken.setUserId(user.getUserId());
		jwtRefreshToken.setToken(strRefreshToken);
		
		refreshTokenRepository.save(jwtRefreshToken);

		tokenSet.setAccessToken(strAccessToken);
		tokenSet.setRefreshToken(strRefreshToken);

		return tokenSet;
	}
	

	//while creating the token -
	//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	//2. Sign the JWT using the HS512 algorithm and secret key.
	//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	//   compaction of the JWT to a URL-safe string 
	public String getAccessToken(UserInfo user)
	{
		long curTime = System.currentTimeMillis();
		/** custom payload 정의 **/
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", user.getUserId());
		claims.put("userName", user.getUserNm());
		claims.put("deptId","testDept");
		claims.put("deptNm","테스트부서");
//		claims.put("authorities",user.getAuthorities());		
		
		// 토큰타입 정보
		claims.put("jwtTokenType", TokenType.ACCESS_TOKEN);

		System.out.println("jwtTokenType"+ new Date(curTime));
		
		
		String jwtToken = Jwts.builder()
							.setHeaderParam("typ", "JWT")
							.setClaims(claims)
							.setAudience((String)claims.get("userId"))
							.setIssuer("www.hanwhalife.com")
			//				.setId("")	// token id
			//				.setSubject(userName)
							.setIssuedAt(new Date(curTime))	// 생성시간
							.setExpiration(new Date(curTime + ACCESS_TOKEN_VALIDITY))	// 만료시간
			//				.signWith(SignatureAlgorithm.HS512, this.generateKey())
							.signWith(SignatureAlgorithm.HS512, generateKey(ACCESS_SECRET_KEY))	// SignatureAlgorithm.HS256
							.compact();
		return jwtToken;
	}


	private String getRefreshToken(UserInfo user)
	{
		long curTime = System.currentTimeMillis();
		/** custom payload 정의 **/
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", user.getUserId());
		claims.put("userName", user.getUserNm());
		claims.put("deptId","testDept");
		claims.put("deptNm","테스트부서");
//		claims.put("authorities",user.getAuthorities());	
		
		// 토큰타입 정보
		claims.put("jwtTokenType", TokenType.REFRESH_TOKEN);
//		.setHeaderParam("jwtTokenType", JwtTokenType.)
		String jwtToken = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setClaims(claims)
				.setAudience((String)claims.get("userId"))
				.setIssuer("www.hanwhalife.com")
//				.setId("")	// token id
				.setIssuedAt(new Date(curTime))	// 생성시간
				.setExpiration(new Date(curTime + REFRESH_TOKEN_VALIDITY))	// 만료시간
				.signWith(SignatureAlgorithm.HS512, generateKey(REFRESH_SECRET_KEY))
				.compact();
		
		return jwtToken;
	}

	
	
	

	private byte[] generateKey(String secretKey)
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


	public Boolean validateAccessToken(String token) 
	{
		return validateToken(token, ACCESS_SECRET_KEY);
	}


	public Boolean validateRefreshToken(String token) 
	{
		return validateToken(token, REFRESH_SECRET_KEY);
	}

	
	public <T> T getClaimFromToken(String token, String secretKey, Function<Claims, T> claimsResolver) 
	{
		final Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		return claimsResolver.apply(claims);
	}
	

	//validate token
	private Boolean validateToken(String token, String secretKey) 
					throws ExpiredJwtException
						, UnsupportedJwtException
						, MalformedJwtException, SignatureException, IllegalArgumentException
	{
//		try {
//			Claims claims = getAllClaimsFromToken(token, secretKey);
		Claims claims = Jwts.parser().setSigningKey(generateKey(secretKey)).parseClaimsJws(token).getBody();
		// TODO 필요 시 claims 검증 로직 추가
//			final Date expiration = claims.getExpiration();
//			System.out.println(expiration);
			return true;
			/*
//			if(expiration.before(new Date())) {
//				return false;
//			}
	*/		
/*
		}catch(ExpiredJwtException e) {
			// 토큰 만료
		}catch(UnsupportedJwtException e) {
			// 토큰 형식 오류
		}catch(MalformedJwtException e) {
			// 툐큰 구성 오류
		}catch(SignatureException e) {
			// 서명 오류
		}catch(IllegalArgumentException e) {
			//
//		}catch(Exception e) {
			return false;
		}
		*/
	}

	/*
    //for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token, String secretKey)
	{
		return Jwts.parser().setSigningKey(generateKey(secretKey)).parseClaimsJws(token).getBody();
	}

	

	//retrieve username from jwt token
	public String getAudienceFromToken(String token, String secretKey) 
	{
		return getClaimFromToken(token, secretKey, Claims::getAudience);	// getSubject
	}


	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token, String secretKey) 
	{
		return getClaimFromToken(token, secretKey, Claims::getExpiration);
	}

//	public <T> T getJwtTokenTypeFromToken(String token, Function<Claims, T> claimsResolver) 
//	{
//		final Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//		return claimsResolver.apply(claims);
//	}



	//check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
*/
	
	/*


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

	private String doGenerateToken(Map<String, Object> claims, String userName) 
	{
		System.out.println("key : "+ secretKey);

		serialVersionUID 
		
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
*/



}