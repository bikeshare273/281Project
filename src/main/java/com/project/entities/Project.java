package com.project.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "projects")
public class Project {

	@Id
	@Column(name = "proj_entry_id", unique = true, nullable = false)
	private Integer proj_entry_id;
		
	@Column(name = "userid", unique = false, nullable = false)
	private Integer userid;
	
	@Column(name = "username", unique = false, nullable = false)
	private String username;
	
	@Column(name = "projectid", unique = false, nullable = false)
	private String projectid;
	
	@Column(name = "projectname", unique = false, nullable = false)
	private String projectname;
	
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	@Column(name = "tenantid", unique = false, nullable = false)
	private String tenantid;

	public Integer getProj_entry_id() {
		return proj_entry_id;
	}

	public void setProj_entry_id(Integer proj_entry_id) {
		this.proj_entry_id = proj_entry_id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}
	
	
	
}
