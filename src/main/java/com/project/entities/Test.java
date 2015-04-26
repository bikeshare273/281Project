package com.project.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "test")
public class Test {
	
/*************************************************************************/
	@Id
	@Column(name = "TestIdString", unique = true, nullable = false)
	private String TestIdString;
	
	@Column(name = "TestString", unique = true, nullable = false)
	private String TestString;
	
/*************************************************************************/
	
	public String getTestIdString() {
		return TestIdString;
	}
	public void setTestIdString(String testIdString) {
		TestIdString = testIdString;
	}
	public String getTestString() {
		return TestString;
	}
	public void setTestString(String testString) {
		TestString = testString;
	}
	
/*************************************************************************/
	
}
