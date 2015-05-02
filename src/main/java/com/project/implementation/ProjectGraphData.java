package com.project.implementation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.project.dto.GraphData;
import com.project.dto.GraphPlotValueDTO;
import com.project.util.NoSqlConnection;

public class ProjectGraphData {

	@Autowired
	TenantFieldProjectImpl tenantFieldProjectImpl;

	MongoTemplate mongoTemplate = NoSqlConnection.getConnection();

	public List<GraphData> getGraphData(String project_Id){
		List<GraphData> graphData = new ArrayList<GraphData>();

		String tenant_Id = tenantFieldProjectImpl.getTenantIdByProjectId(project_Id);

		if(tenant_Id.equalsIgnoreCase("GANTTER")){
			graphData = getGantterGraphData(project_Id);

		}else if(tenant_Id.equalsIgnoreCase("KANBAN")){
			graphData = getKanbanGraphData(project_Id);

		}else if(tenant_Id.equalsIgnoreCase("SCRUM")){

			graphData=getScrumGraphData(project_Id);
		}

		return graphData;
	}

	public List<GraphData> getGantterGraphData(String project_Id){
		List<GraphData> graphDataList = new ArrayList<GraphData>();
		
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

		//xData.put("Completed", String.valueOf(completed_tasks));
		//graphData.setxData(xData);
		GraphPlotValueDTO graphPlotValueDTO = new GraphPlotValueDTO();
		graphPlotValueDTO.setRowName("Completed");
		graphPlotValueDTO.setRowValue(completed_tasks);
		List<GraphPlotValueDTO> rows = new ArrayList<GraphPlotValueDTO>();
		rows.add(graphPlotValueDTO);
		graphData.setxData(rows);

		yData.put("Completed", "Completed");

		//graphData.setChartType("LineChart");
		graphData.setChartType("BarChart");
		System.out.println("Graph:"+graphData.toString());

		graphDataList.add(graphData);
		return graphDataList;
	}


	public List<GraphData> getKanbanGraphData(String project_Id){
		List<GraphData> graphDataList = new ArrayList<GraphData>();
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

		HashMap<String, Integer> xData = new HashMap<String, Integer>();
		HashMap<String, String> yData = new HashMap<String, String>();

		xData.put("Completed", completed_tasks);
		xData.put("In-Progress", inProgress_tasks);
		xData.put("Requested", requested_tasks);
		List<GraphPlotValueDTO> graphPlotValueDTOs = covertMapToGraphPlotValueDTOList(xData);
		graphData.setxData(graphPlotValueDTOs);

		/*yData.put("Completed", "Completed");
		yData.put("In-Progress", "In-Progress");
		yData.put("Requested", "Requested");
		graphData.setyData(yData);*/

		//graphData.setChartType("BarChart");
		graphData.setChartType("PieChart");
		System.out.println("Graph:"+graphData.toString());

		graphDataList.add(graphData);
		return graphDataList;
	}

	private List<GraphPlotValueDTO> covertMapToGraphPlotValueDTOList(HashMap<String, Integer> map){
		List<GraphPlotValueDTO> list = new ArrayList<GraphPlotValueDTO>();
		for(Entry<String, Integer> entry : map.entrySet()){
			GraphPlotValueDTO graphPlotValueDTO = new GraphPlotValueDTO();
			graphPlotValueDTO.setRowName(entry.getKey());
			graphPlotValueDTO.setRowValue(entry.getValue());
			list.add(graphPlotValueDTO);
		}
		return list;
	}




	public List<GraphData> getScrumGraphData(String project_Id){


		List<GraphData> graphDataList=new ArrayList<GraphData>();

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


		BasicDBList sprintList=(BasicDBList) dbObject_tenantData.get("SPRINT");

		Iterator sprintIterator= sprintList.iterator();

		while(sprintIterator.hasNext())
		{
            System.out.println("----------------sprint 1----------");
			GraphData graphData = new GraphData();
			graphData.setxName("Date");
			graphData.setyName("Remaining Hours");
			graphData.setChartType("LineChart");

			List<GraphPlotValueDTO> graphValues=new ArrayList<GraphPlotValueDTO>();


			int totalRemainingHours=0;



			DBObject dbObject_sprint=(DBObject) sprintIterator.next();

			String sprintId=(String) dbObject_sprint.get("Sprint_Id");
			graphData.setSprint_Id(sprintId);
			int sprintVelocity=Integer.parseInt((String)dbObject_sprint.get("Velocity"));

			String sprintStartDate=(String) dbObject_sprint.get("Start_Date");
			DateFormat formatter= new SimpleDateFormat("dd-MM-yy");
			Date sprintDate=null;
			try {
				sprintDate=formatter.parse(sprintStartDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(sprintDate);
 	    //   cal.add(Calendar.DATE, 1); 
			//cal.getTime();

			BasicDBList backlogList=(BasicDBList) dbObject_tenantData.get("BACKLOG");

			Iterator backlogIterator=backlogList.iterator();


			// calculating total remaining hours by adding all backlog remaining hours for particular sprint
			while(backlogIterator.hasNext()){

				DBObject backlogObject=(DBObject) backlogIterator.next();

				if(backlogObject.get("Sprint").equals(sprintId)){

					totalRemainingHours+=Integer.parseInt((String) backlogObject.get("Remaining_Hours"));

				}

			}

			//System.out.println("total remaining hours  :  "+totalRemainingHours);
			
			GraphPlotValueDTO gplot=new GraphPlotValueDTO();
			gplot.setRowName(cal.getTime().toString());
			
			//System.out.println("date  : "+cal.getTime());
			gplot.setRowValue(totalRemainingHours);

			graphValues.add(gplot);

			// Ideal remaining hours calculation based on velocity
			while(totalRemainingHours>0){

				totalRemainingHours -= sprintVelocity;

				GraphPlotValueDTO gp=new GraphPlotValueDTO();
				
				cal.add(Calendar.DATE, 1);
				gp.setRowName(cal.getTime().toString());
				gp.setRowValue(totalRemainingHours);

				graphValues.add(gp);
				
			}
		
			graphData.setxData(graphValues);

			
			//System.out.println("GraphValues:"+ graphValues.toString());
			
			graphDataList.add(graphData);   

		}




		return graphDataList;
	}

}



