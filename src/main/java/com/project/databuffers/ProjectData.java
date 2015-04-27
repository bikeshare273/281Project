package com.project.databuffers;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectData {
	
	String projectId;
	String projectName;
	String tableName;
	String tenantId; 
	ArrayList<HashMap<String,String>> rows;
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public ArrayList<HashMap<String, String>> getRows() {
		return rows;
	}
	public void setRows(ArrayList<HashMap<String, String>> rows) {
		this.rows = rows;
	}
	
}