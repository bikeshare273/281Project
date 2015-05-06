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

		System.out.println("Proj => " + projectid);
		
		String tenantid = tenantFieldProjectImpl.getTenantIdByProjectId(projectid);
		
		System.out.println("Tenantid => " + tenantid);
		
		List<String> tables = tenantFieldProjectImpl.getAllTablesForATenant(tenantid);
		
		System.out.println("Size => " + tables.size());
/*
 * 
 * 
		System.out.println("Retrieved Tenand => " + tenantid);

		System.out.println("Retrieved Tables => ");
		{
			for(String table : tables){

				System.out.println("Name :" + table);
			}
		}*/



		ArrayList<DisplayData> proj= new ArrayList<DisplayData>();
		DBCollection mc = mongoTemplate.getCollection("tenant_data");

		DBObject query = new BasicDBObject("Project_Id", projectid);

		DBObject dbo =	mc.findOne(query);

		DBObject td=(DBObject) dbo.get("Tenant_Data");
		String projectName=(String) dbo.get("Project_Name");

		// iterating over number of tables present in project

		for(int i=0; i<tables.size();i++){

			DisplayData d= new DisplayData();

			d.setProjectName(projectName);
			d.setTableName(tables.get(i));

			List<String> fields = tenantFieldProjectImpl.getAllFieldsForTable(tenantid, tables.get(i));

			//System.out.println("table name : "+tables.get(i));
			//System.out.println(" fields : "+fields);

			d.setCol_name(fields);

			
			//System.out.println("TD:"+td);
			//System.out.println("TABLE NAME FROM MONGO : "+td.get(tables.get(i)));

			
			BasicDBList bd=(BasicDBList) td.get(tables.get(i));
			if(bd == null){
				bd=(BasicDBList) td.get(tables.get(i).toLowerCase());
			}
			List<List<String>> entryList = new ArrayList<List<String>> ();

			System.out.println("BD size:"+bd.size());
			// iterating over number of entries in table
			for(int j=0; j<bd.size();j++){      

				List<String> entry=new ArrayList<String>();

				System.out.println("entry : "+j+"  " +bd.get(j));
				DBObject z=(DBObject) bd.get(j);

				System.out.println(" field count from SQL : "+fields.size());
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