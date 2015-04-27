package com.project.dao;

import java.util.List;

import com.project.entities.Tenant;

public interface IDaoInterfaceForTenant {

		
	/*
		CREATE TABLE tenants (

	tenantid			VARCHAR(20),
	tablename			VARCHAR(100),
	tabledesc			VARCHAR(200),

	PRIMARY KEY(tenantid, tablename));

*/
	
	
	public void save(Tenant tenant);
	public void update(Tenant tenant);
	public void delete(Tenant tenant);
	
	
	public List<Tenant> getAllTablesForTenant(String tenantid);
	public List<Tenant> getTenantIdByTableName(String tablename);
	public List<Tenant> getAllTenants();
	public Tenant getSingleTenantAndTable(String tenantid, String tablename);

	
}
