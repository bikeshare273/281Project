package com.project.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.project.entities.Test;



@Transactional
public class TestDao implements ITestDao {

	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public void saveTest(Test test) {
	
		hibernateTemplate.save(test);
	}

	@Override
	public Test getTest(Test test) {
		
		String query = "from Test t";
		@SuppressWarnings("unchecked")
		List<Test> records = (List<Test>) hibernateTemplate.find(query);
		
		if (records.isEmpty()) {
		return null;
		} else {
		return records.get(records.size()-1);
		}

	}
}

