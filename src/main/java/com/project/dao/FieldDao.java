package com.project.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.project.entities.Field;
import com.project.entities.Tenant;

@Transactional
public class FieldDao implements IDaoInterfaceForFields{
	
	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	public void save(Field field) {

		hibernateTemplate.save(field);
		
	}

	@Override
	public void update(Field field) {
		
		hibernateTemplate.update(field);
		
	}

	@Override
	public void delete(Field field) {

		hibernateTemplate.delete(field);
		
	}

	@Override
	public List<String> getAllFieldNamesByTenantIdAndTableName(String tenantid,	String tablename) {
	
		List<String> fields = new ArrayList<String>();
		String fieldName;
		
		String query = "from Field f where f.tenantid = ? and f.tablename = ? ";
		@SuppressWarnings("unchecked")
		List<Field> fieldEntries = (List<Field>) hibernateTemplate.find(query, tenantid, tablename);

		if (fieldEntries.isEmpty()) {
			return null;
		} else {
			
			for(Field fieldEntry :  fieldEntries )
			{
				fieldName = fieldEntry.getFieldname();
				fields.add(fieldName);
			}
		}
		
		return fields;
	}

	@Override
	public String getFieldTypeByFieldName(String tenantid,	String tablename, String fieldname) {
		
		String fieldType;
		
		String query = "from Field f where f.tenantid = ? and f.tablename = ? and f.fieldname = ?";
		@SuppressWarnings("unchecked")
		List<Field> fieldEntries = (List<Field>) hibernateTemplate.find(query, tenantid, tablename);

		if (fieldEntries.isEmpty()) {
			return null;
		} else {
			
			fieldType = fieldEntries.get(0).getFieldtype();
			
			return fieldType;
		}
			
	}

}
