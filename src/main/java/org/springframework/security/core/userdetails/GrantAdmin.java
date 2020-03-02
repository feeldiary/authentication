package org.springframework.security.core.userdetails;

import org.springframework.security.core.GrantedAuthority;

public class GrantAdmin implements GrantedAuthority {

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.toString();
	}

}
