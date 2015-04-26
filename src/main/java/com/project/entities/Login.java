package com.project.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Id;

@Entity
@Table(name = "login")
public class Login {


/***************************************************/
	
	/*
	 	CREATE TABLE login(
		
		userid					INT(10),
		username				VARCHAR(20),
		password				VARCHAR(20),
		sessionid				INT(10),

		PRIMARY KEY (userid));
	 	 
	 */
	
/***************************************************/
	
	private Integer userid;
	private String username;
	private String password;
	private Integer sessionid;
	
/***************************************************/
	
	@Id
	@Column(name = "userid", unique = true, nullable = false)
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	@Column(name = "username", unique = true, nullable = false)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "password", unique = false, nullable = false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "sessionid", unique = false, nullable = true)
	public Integer getSessionid() {
		return sessionid;
	}
	public void setSessionid(Integer sessionId) {
		this.sessionid = sessionId;
	}
	
/***************************************************/
}