package com.project.implementation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.project.util.NoSqlConnection;

public class ProjectStatus {
	
	MongoTemplate mongoTemplate = NoSqlConnection.getConnection();
	
	public ArrayList<String> getProjectCompletionStatus(String project_Id, String tenant_Id){
		ArrayList<String> completionStatus = new ArrayList<String>();
		
		if(tenant_Id.equalsIgnoreCase("GANTTER")){
			completionStatus = getGantterStatus(project_Id);
			
		}else if(tenant_Id.equalsIgnoreCase("KANBAN")){		
			completionStatus = getKanbanStatus(project_Id);
			
		}else if (tenant_Id.equalsIgnoreCase("SCRUM")) {
			
		}
		
		return completionStatus;
	}
	
	public ArrayList<String> getGantterStatus(String project_Id){
		
		ArrayList<String> arrayList_projectStatus = new ArrayList<String>();
		int num_tasks_completed = 0;
		int total_tasks = 0;
		
		DBCollection dbCollection = mongoTemplate.getCollection("tenant_data");
			
		BasicDBObject query = new BasicDBObject("Project_Id",project_Id);
		DBCursor dbCursor = dbCollection.find(query); 
		
		DBObject dbObject = null;
		DBObject dbObject_tenantData;
		
		if(dbCursor.hasNext()){
			dbObject = dbCursor.next();			
		}
		dbCursor.close();
		
		dbObject_tenantData = (DBObject)dbObject.get("Tenant_Data");
		BasicDBList basicDBList = (BasicDBList)dbObject_tenantData.get("TASK");
		
		total_tasks = basicDBList.size();
		
		BasicDBObject basicDBObject;
		Iterator iterator = basicDBList.iterator();
		while(iterator.hasNext()){
			basicDBObject = (BasicDBObject) iterator.next();
			if(basicDBObject.get("Task_Status").equals("Completed")){
				num_tasks_completed++;
			}
		}
		
		Double calculate = ((double)num_tasks_completed /total_tasks) * 100;
					
		DecimalFormat onePointPrecision = new DecimalFormat("#.#");
		calculate = Double.valueOf(onePointPrecision.format(calculate));
		
		arrayList_projectStatus.add("Project Complete: "+ calculate.toString() + "%");
		
		return arrayList_projectStatus;
	}
	
	
	public ArrayList<String> getKanbanStatus(String project_Id){
	
		ArrayList<String> arrayList_projectStatus = new ArrayList<String>();
		
		int requested_tasks = 0;
		int completed_tasks = 0;
		int inprogress_tasks = 0;
		
		DBCollection dbCollection = mongoTemplate.getCollection("tenant_data");
		
		BasicDBObject query = new BasicDBObject("Project_Id",project_Id);
		DBCursor dbCursor = dbCollection.find(query); 
		
		DBObject dbObject = null;
		DBObject dbObject_tenantData;
		
		if(dbCursor.hasNext()){
			dbObject = dbCursor.next();			
		}
		dbCursor.close();
		
		dbObject_tenantData = (DBObject)dbObject.get("Tenant_Data");
		BasicDBList basicDBList = (BasicDBList)dbObject_tenantData.get("TASK");
		
		BasicDBObject basicDBObject;
		Iterator iterator = basicDBList.iterator();
		
		while(iterator.hasNext()){
			basicDBObject = (BasicDBObject) iterator.next();
			if(basicDBObject.get("Task_Status").equals("Completed")){
				completed_tasks++;
			}else if(basicDBObject.get("Task_Status").equals("Requested")){
				requested_tasks++;
			}else if(basicDBObject.get("Task_Status").equals("In Progress")){
				inprogress_tasks++;
			}
		}
		
		arrayList_projectStatus.add("Requested Tasks:"+requested_tasks);
		arrayList_projectStatus.add("In-Progress Tasks:"+inprogress_tasks);
		arrayList_projectStatus.add("Completed Tasks:"+completed_tasks);
		
		return arrayList_projectStatus;
	}
}
