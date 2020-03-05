package com.hanwhalife.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
//@Entity
//@Table(name="userInfo")
public class JwtUserInfo extends User {

	public JwtUserInfo(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		// TODO Auto-generated constructor stub
	}
	public JwtUserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		// TODO Auto-generated constructor stub
	}
	private static final long serialVersionUID = 1L;
	
	
	@Id
	private String userId;
	@Column(length = 40)
	private String userNm;
	@Column(length = 400)
	@JsonIgnore
	private String password;

	
}
