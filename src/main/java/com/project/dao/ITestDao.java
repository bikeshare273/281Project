package com.project.dao;

import com.project.entities.Test;


public interface ITestDao {

	public void saveTest(Test test);
	
	public Test getTest(Test test);
	
}
