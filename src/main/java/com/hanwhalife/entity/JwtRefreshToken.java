package com.hanwhalife.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="REFRESH_TOKEN")
public class JwtRefreshToken implements Serializable {
	
	private static final long serialVersionUID = -4855988256662523349L;

	@Id
	private String userId;
	@Column(length=255)
	private String token;

	public JwtRefreshToken() {
	}

//
//	public JwtRefreshToken(String token) {
//		this.token = token;
//	}
	
}
