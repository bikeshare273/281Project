package com.project.util;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;

import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.MongoClientURI;

public class NoSqlConnection {

	public static MongoTemplate getConnection() {
		MongoClientURI uri = null;
		MongoClient mongoclient = null;
		MongoTemplate mongoConnection = null;

		try {
			uri = new MongoClientURI("mongodb://user:user@ds061757.mongolab.com:61757/cmpe281");
			mongoclient = new MongoClient(uri);
			mongoConnection = new MongoTemplate(mongoclient, "cmpe281");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mongoConnection;
	}

}
