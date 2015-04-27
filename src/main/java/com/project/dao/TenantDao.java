package com.project.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.project.entities.Tenant;

@Transactional
public class TenantDao implements IDaoInterfaceForTenant{

	@Autowired
	HibernateTemplate hibernateTemplate;
		
	@Override
	public void save(Tenant tenant) {

		hibernateTemplate.save(tenant);
	}

	@Override
	public void update(Tenant tenant) {

		hibernateTemplate.update(tenant);
		
	}

	@Override
	public void delete(Tenant tenant) {

		hibernateTemplate.delete(tenant);
	}

	@Override
	public List<Tenant>getAllTablesForTenant(String tenantid) {

		String query = "from Tenant t where t.tenantid = ?";
		@SuppressWarnings("unchecked")
		List<Tenant> tenantEntries = (List<Tenant>) hibernateTemplate.find(query, tenantid);

		if (tenantEntries.isEmpty()) {
			return null;
		} else {
			return tenantEntries;
		}
	}


	@Override
	public List<Tenant> getTenantIdByTableName(String tablename) {
		
		String query = "from Tenant t where t.tablename = ?";
		@SuppressWarnings("unchecked")
		List<Tenant> tenantEntries = (List<Tenant>) hibernateTemplate.find(query, tablename);

		if (tenantEntries.isEmpty()) {
			return null;
		} else {
			return tenantEntries;
		}
	}

	@Override
	public Tenant getSingleTenantAndTable(String tenantid, String tablename) {
		
		String query = "from Tenant t where t.tenantid = ? and t.tablename = ?";
		@SuppressWarnings("unchecked")
		List<Tenant> tenantEntries = (List<Tenant>) hibernateTemplate.find(query, tenantid, tablename);

		if (tenantEntries.isEmpty()) {
			return null;
		} else {
			return tenantEntries.get(0);
		}
		
		
		
	}

	@Override
	public List<Tenant> getAllTenants() {
		
		String query = "from Tenant";
		@SuppressWarnings("unchecked")
		List<Tenant> tenants = (List<Tenant>) hibernateTemplate.find(query);

		if (tenants.isEmpty()) {
			return null;
		} else {
			return tenants;
		}
		
		
	}

}
