package com.project.dao;

import java.util.List;

import com.project.entities.Field;

public interface IDaoInterfaceForFields {
	
	
	/*
	CREATE TABLE fields (
	
	tenantid			VARCHAR(20),
	tablename			VARCHAR(100),
	fieldname			VARCHAR(100),
	fieldtype			VARCHAR(100),

	PRIMARY KEY (tenantid, tablename, fieldname),
	FOREIGN KEY(tenantid) REFERENCES tenants(tenantid) ON DELETE CASCADE);


*/
	
	public void save(Field field);
	public void update(Field field);
	public void delete(Field field);
	
	public List<String> getAllFieldNamesByTenantIdAndTableName(String tenantid, String tablename);
	public String getFieldTypeByFieldName(String tenantid, String tablename, String fieldname);

}
