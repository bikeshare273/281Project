package com.project.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.project.dao.ProjectDao;
import com.project.databuffers.ProjectData;
import com.project.entities.Project;
import com.project.util.AppUtils;
import com.project.util.NoSqlConnection;


public class MultiTenantAPIImpl {
	
	@Autowired 
	AppUtils apputils;
	
	@Autowired
	TenantFieldProjectImpl tenantFieldProjectImpl;
	
	@Autowired
	ProjectDao projectDao;
	
	public static MongoTemplate mongoTemplate = NoSqlConnection.getConnection();
	
	private ArrayList<HashMap<String, String>> arrayList;
	private HashMap<String, String> projectdata_hashMap;
	private BasicDBObject basicDBObject;
	private String value;
	private String key;
	private StringBuilder json;
	
	public boolean updateProject(ProjectData projectData){
		
		/*
		ArrayList<String> arrayList_tableFields = new ArrayList<String>();
		arrayList_tableFields.add("task_id");
		arrayList_tableFields.add("start_date");
		arrayList_tableFields.add("end_date");
		arrayList_tableFields.add("worker");
		Iterator<String> itr_arrayList_tableFields = arrayList_tableFields.iterator();
		*/
				
		boolean insert = false;
		
		//arrayList = new ArrayList<HashMap<String,String>>();
		
		String projectId = projectData.getProjectId();
		
		String tableName = projectData.getTableName();
		arrayList = projectData.getRows();
		
		Iterator<HashMap<String,String>> iterator = arrayList.iterator();	 //rows in table	
		DBCollection dbCollection =  mongoTemplate.getCollection("tenant_data");
		BasicDBObject query = new BasicDBObject("Project_Id",projectId);
		
		BasicDBList basicDBList = new BasicDBList();
		
		
		//clear array
		BasicDBObject clear = new BasicDBObject("$set",new BasicDBObject("Tenant_Data."+tableName,new BasicDBList()));
		dbCollection.update(query, clear, true, false);
		
		
		while(iterator.hasNext()){
			projectdata_hashMap = iterator.next();	//data of 1 row		
			basicDBObject = new BasicDBObject();

			for (Entry<String, String> entry : projectdata_hashMap.entrySet()) {
			    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			    basicDBObject.append(entry.getKey(), entry.getValue());
			}
			
			BasicDBObject set1 = new BasicDBObject("Tenant_Data."+tableName,basicDBObject);
			BasicDBObject set = new BasicDBObject("$push",set1);
			
			dbCollection.update(query, set, true, false);
			basicDBObject.clear();
		}
		
		return insert;
	}
	
	
	public String createProject(String Tenant_Id, Integer user_Id, String project_name){		
		
		Integer id = apputils.generateIdValue(2000);
		
		String prefix = "P";
				
		
		String project_Id = prefix + id ;
		
		
		basicDBObject = new BasicDBObject();
		basicDBObject.put("Project_Id", project_Id);
		basicDBObject.put("Project_Name", project_name);
		
		basicDBObject.put("User_Id", user_Id);
		basicDBObject.put("Tenant_Id", Tenant_Id);
		
		BasicDBObject dbObject = new BasicDBObject();
		List<String> tablesForTenant = tenantFieldProjectImpl.getAllTablesForATenant(Tenant_Id);
		for(String table_name : tablesForTenant){
			System.out.println("Table Name -"+table_name+" for tenant-"+Tenant_Id);
			dbObject.append(table_name, new BasicDBList());
		}
		
		
		BasicDBList basicDBList1 = new BasicDBList();
		BasicDBList basicDBList2 = new BasicDBList();
		
		dbObject.append("Tasks", basicDBList1);
		dbObject.append("Resources", basicDBList2);
		
		basicDBObject.put("Tenant_Data", dbObject);
		
		mongoTemplate.save(basicDBObject, "tenant_data");

		Project project = new Project();
		
		project.setProj_entry_id(apputils.generateIdValue(3000));
		project.setProjectid(project_Id);
		project.setProjectname(project_name);
		project.setUserid(user_Id);
		project.setTenantid(Tenant_Id);
		project.setUsername("TESTUSER");
		
		projectDao.save(project);
		
		
		basicDBObject.clear();
		
		return project_Id;
	}
	
	
}
