package org.springframework.security.core.userdetails;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

public class GrantUser implements GrantedAuthority, Serializable {

	
	
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.toString();
	}

}
