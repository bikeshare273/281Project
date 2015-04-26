package com.project.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	/**********************************************************************************************/

	/*
	 * CREATE TABLE users(
	 * 
	 * userid INT(10), name VARCHAR(100), mobile VARCHAR(20) ,#NOT NULL, address
	 * VARCHAR(200) , city VARCHAR(20) ,#NOT NULL, zipcode VARCHAR(20) ,#NOT
	 * NULL, state VARCHAR(20) ,#NOT NULL, country VARCHAR(20) ,#NOT NULL,
	 * 
	 * PRIMARY KEY (userid));
	 */

	/**********************************************************************************************/
	@Id
	@Column(name = "userid", unique = true, nullable = false)
	private Integer userid;

	@Column(name = "name", unique = false, nullable = false)
	private String name;

	@Column(name = "mobile", unique = false, nullable = true)
	private String mobile;

	@Column(name = "address", unique = false, nullable = true)
	private String address;

	@Column(name = "city", unique = false, nullable = true)
	private String city;

	@Column(name = "zipcode", unique = false, nullable = true)
	private String zipcode;

	@Column(name = "state", unique = false, nullable = true)
	private String state;

	@Column(name = "country", unique = false, nullable = true)
	private String country;

	/**********************************************************************************************/

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/**********************************************************************************************/

}
