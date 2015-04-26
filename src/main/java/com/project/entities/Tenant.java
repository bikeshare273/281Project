package com.project.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "tenants" )
public class Tenant {

/*
	 			CREATE TABLE tenants (

				tenantid			VARCHAR(20),
				tablename			VARCHAR(100),
				tabledesc			VARCHAR(200),

				PRIMARY KEY(tenantid, tablename));
  
 */
	
	@Id
	@Column(name = "tenantid", unique = true, nullable = false)
	private String tenantid;
	
	@Id
	@Column(name = "tablename", unique = true, nullable = false)
	private String tablename;
	
	@Column(name = "tabledesc", unique = false, nullable = true)
	private String tabledesc;

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getTabledesc() {
		return tabledesc;
	}

	public void setTabledesc(String tabledesc) {
		this.tabledesc = tabledesc;
	}
	
	
	
	
	
}
