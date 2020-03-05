package com.hanwhalife.entity;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
//	private final String jwttoken;
	
	private JwtTokenSet tokenSet;

	public JwtResponse(JwtTokenSet tokenSet) {
		tokenSet = tokenSet;
	}

	public JwtTokenSet getJwtTokenSet() {
		return tokenSet;
	}
}