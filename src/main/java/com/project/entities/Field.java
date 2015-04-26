package com.project.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "fields")
public class Field {
	
	/*
			CREATE TABLE fields (
			
			tenantid			VARCHAR(20),
			tablename			VARCHAR(100),
			fieldname			VARCHAR(100),
			fieldtype			VARCHAR(100),

			PRIMARY KEY (tenantid, tablename, fieldname),
			FOREIGN KEY(tenantid) REFERENCES tenants(tenantid) ON DELETE CASCADE);
	
	
	*/
	
		@Id
		@Column(name = "tenantid", unique = true, nullable = false)
		private String tenantid;
		
		@Id
		@Column(name = "tablename", unique = true, nullable = false)
		private String tablename;
		
		@Id
		@Column(name = "fieldname", unique = true, nullable = false)
		private String fieldname;
		
		@Column(name = "fieldtype", unique = false, nullable = false)
		private String fieldtype;

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

		public String getFieldname() {
			return fieldname;
		}

		public void setFieldname(String fieldname) {
			this.fieldname = fieldname;
		}

		public String getFieldtype() {
			return fieldtype;
		}

		public void setFieldtype(String fieldtype) {
			this.fieldtype = fieldtype;
		}
	
}
