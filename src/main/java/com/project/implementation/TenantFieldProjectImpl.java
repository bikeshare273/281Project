package com.project.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.dao.IDaoInterfaceForFields;
import com.project.dao.IDaoInterfaceForTenant;
import com.project.dao.ProjectDao;
import com.project.entities.Project;
import com.project.entities.Tenant;

public class TenantFieldProjectImpl {

	@Autowired
	IDaoInterfaceForTenant tenantDao;

	@Autowired
	IDaoInterfaceForFields fieldDao;
	
	@Autowired
	ProjectDao projectDao;

	public List<String> getAllTablesForATenant(String tenantid) {
		List<Tenant> tenantEntries = tenantDao.getAllTablesForTenant(tenantid);

		if (tenantEntries == null) {
			return null;
		}

		List<String> listofTables = new ArrayList<String>();

		for (Tenant tenantEntry : tenantEntries) {
			String tableName = tenantEntry.getTablename();
			listofTables.add(tableName);
		}

		return listofTables;

	}

	public List<String> getAllFieldsForTable(String tenantid, String tablename) {
		List<String> fieldNames = fieldDao.getAllFieldNamesByTenantIdAndTableName(tenantid, tablename);

		if (fieldNames == null) {
			return null;
		}

		return fieldNames;

	}

	public List<String> getDistinctTenants() {

		List<Tenant> tenantEntries = tenantDao.getAllTenants();

		if (tenantEntries == null) {
			return null;
		}

		List<String> tenants = new ArrayList<String>();
		String tableName;

		for (Tenant tenantEntry : tenantEntries) {
			tableName = tenantEntry.getTablename();
			if (!tenants.contains(tableName)) {
				tenants.add(tableName);
			}

		}
		return tenants;
	}

	public String getTenantIdByProjectId(String projectid)
	{
		Project project = projectDao.getProjectByProjectId(projectid);
		
		if(project == null) { return null; }
		
		String tenantid = project.getTenantid();
		
		return tenantid;
	
	}
	
	
	
	
	
	
}
