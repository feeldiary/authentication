package com.hanwhalife.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name="userInfo")
public class UserInfo 
{
	private static final long serialVersionUID = 1L;

	@Id
	private String userId;

	@Column(length = 40)
	private String userNm;

	@Column(length = 400)
	@JsonIgnore
	private String password;

}
