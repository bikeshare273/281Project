package com.project.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.project.dto.DisplayData;
import com.project.util.NoSqlConnection;


public class MultiTenantImpl{
	
	MongoTemplate mongoTemplate = NoSqlConnection.getConnection();
	
	@Autowired
	TenantFieldProjectImpl tenantFieldProjectImpl;
	
	
public ArrayList<DisplayData> getProject(String projectid){
    
	 String tenantid = tenantFieldProjectImpl.getTenantIdByProjectId(projectid);
	
	 
	 // test data
	  List<String> tables = tenantFieldProjectImpl.getAllTablesForATenant(tenantid);
	  //table.add("Task");
	  /*
	   ArrayList<String> fields= new ArrayList<String>();
	   fields.add("Task_Id");
	   fields.add("Name");
	  */
	
	  System.out.println("Retrieved Tenand => " + tenantid);
	  
	  System.out.println("Retrieved Tables => ");
	  {
		  for(String table : tables){
			  
			  System.out.println("Name :" + table);
		  }
	  }
	  
	  
	  
	ArrayList<DisplayData> proj= new ArrayList<DisplayData>();
	DBCollection mc = mongoTemplate.getCollection("tenant_data");

	DBObject query = new BasicDBObject("Project_Id", projectid);

	DBObject dbo =	mc.findOne(query);
	
	DBObject td=(DBObject) dbo.get("Tenant_Data");
	
	// following code will come in loop over table name list recevied from viresh api
	
	for(int i=0; i<tables.size();i++){
	
		DisplayData d= new DisplayData();
		
		d.setTableName(tables.get(i));
		
		// get all the fields list for table name
		// call to the api from viresh
		
		
		List<String> fields = tenantFieldProjectImpl.getAllFieldsForTable(tenantid, tables.get(i));
		
		System.out.println(" fields : "+fields);
		
		d.setCol_name(fields);
		
		System.out.println("table name : "+tables.get(i));
	    System.out.println(td.get(tables.get(i)));
	    
	    BasicDBList bd=(BasicDBList) td.get(tables.get(i));
	    List<List<String>> entryList = new ArrayList<List<String>> ();
	 
	    // iterating over number of entries in table
	    for(int j=0; j<bd.size();j++){      
	    	
	  	List<String> entry=new ArrayList<String>();
	    	
	    System.out.println(bd.get(j));
	    DBObject z=(DBObject) bd.get(j);
	    
	    for(int k=0;k<fields.size();k++){
	    	
	    	entry.add((String) z.get(fields.get(k)));
	        
	    	System.out.println(z.get(fields.get(k)));
	    	
	    }
	    
	    System.out.println(" each entry :  "+ entry);
	    
	    entryList.add((List<String>) entry);
	    d.setRow(entryList);
	    
	    }
	    
	    
	proj.add(d);
	    
	
	}
	

	return proj;
	
}

}