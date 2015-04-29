package com.project.implementation;

import java.util.HashMap;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.project.dto.GraphData;
import com.project.util.NoSqlConnection;

public class ProjectGraphData {
	
	@Autowired
	TenantFieldProjectImpl tenantFieldProjectImpl;
	
	MongoTemplate mongoTemplate = NoSqlConnection.getConnection();
	
	public GraphData getGraphData(String project_Id){
		GraphData graphData = new GraphData();
		
		String tenant_Id = tenantFieldProjectImpl.getTenantIdByProjectId(project_Id);
		
		if(tenant_Id.equalsIgnoreCase("GANTTER")){
			graphData = getGantterGraphData(project_Id);
			
		}else if(tenant_Id.equalsIgnoreCase("KANBAN")){
			graphData = getKanbanGraphData(project_Id);
			
		}else if(tenant_Id.equalsIgnoreCase("SCRUM")){
			
		}
		
		return graphData;
	}

	public GraphData getGantterGraphData(String project_Id){
		GraphData graphData = new GraphData();
		
		int completed_tasks = 0;
		
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
			}
		}
		
		graphData.setxName("Number of tasks");
		graphData.setyName("Task Status");
		
		HashMap<String, String> xData = new HashMap<String, String>();
		HashMap<String, String> yData = new HashMap<String, String>();
		
		xData.put("Completed", String.valueOf(completed_tasks));
		graphData.setxData(xData);
		
		yData.put("Completed", "Completed");
		graphData.setyData(yData);
		
		System.out.println("Graph:"+graphData.toString());
		
		return graphData;
	}
	
	
	public GraphData getKanbanGraphData(String project_Id){
		GraphData graphData = new GraphData();
		int requested_tasks = 0;
		int inProgress_tasks = 0;
		int completed_tasks = 0;
		
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
				inProgress_tasks++;	
			}
		}
			
		graphData.setxName("Number of tasks");
		graphData.setyName("Task Status");
		
		HashMap<String, String> xData = new HashMap<String, String>();
		HashMap<String, String> yData = new HashMap<String, String>();
		
		xData.put("Completed", String.valueOf(completed_tasks));
		xData.put("In-Progress", String.valueOf(inProgress_tasks));
		xData.put("Requested", String.valueOf(requested_tasks));
		graphData.setxData(xData);
		
		yData.put("Completed", "Completed");
		yData.put("In-Progress", "In-Progress");
		yData.put("Requested", "Requested");
		graphData.setyData(yData);
		
		System.out.println("Graph:"+graphData.toString());
		
		return graphData;
	}
	
}



