package com.hanwhalife.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Board {
	@Id
	private String id;
	
	private String title;
	private String content;
	@Column(updatable = false)
	private String regUserId;
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date regDate;
	@Column(insertable = false, updatable = false, columnDefinition = "number default 0")
	private int readCnt;

}
