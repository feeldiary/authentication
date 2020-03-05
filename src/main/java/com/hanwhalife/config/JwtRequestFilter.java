package com.hanwhalife.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
//public class JwtRequestFilter extends AbstractAuthenticationProcessingFilter 
public class JwtRequestFilter extends OncePerRequestFilter
{
//	@Autowired
//	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.tokenHeader}")
	private String TOKEN_HEADER;

	//	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException 
	{
//		final String requestTokenHeader = request.getHeader("x-access-token");
		final String requestTokenHeader = request.getHeader(TOKEN_HEADER);

//		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
//				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				if(jwtTokenUtil.validateAccessToken(jwtToken)) {
					
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(jwtToken, jwtToken);
//					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//					// After setting the Authentication in the context, we specify
//					// that the current user is authenticated. So it passes the
//					// Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}else {
					System.out.println("unauthorized");
				}
			}catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			}catch (SignatureException e) {
				System.out.println("Signature err");
			}catch (ExpiredJwtException e) {
				// 토큰 만료
				System.out.println("JWT Token has expired");
			}
		} else {
//			logger.warn("JWT Token does not begin with Bearer String");
		}
/*
		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) 
		{
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) 
			{
				// TODO. 권한 부여
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
*/
		chain.doFilter(request, response);
	}

}