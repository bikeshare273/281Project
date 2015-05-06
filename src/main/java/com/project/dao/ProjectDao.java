package com.project.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.project.entities.Project;

@Transactional
public class ProjectDao {

	
	@Autowired
	HibernateTemplate hibernateTemplate;
	
	public void save(Project project)
	{
			
		hibernateTemplate.save(project);
		
	}
	
	
	public void update(Project project)
	{
			
		hibernateTemplate.update(project);
		
	}
	
	
	public void delete(Project project)
	{
			
		hibernateTemplate.delete(project);
		
	}
	
	public Project getProjectByProjectId(String projectid)
	
	{
		
		String query = "from Project p where p.projectid = ?";
		
		@SuppressWarnings("unchecked")
		List<Project> projects = (List<Project>) hibernateTemplate.find(query, projectid);
		
		if(projects.isEmpty()) { return null; }
		
		return projects.get(0);
	}
	
	
	public List<Project> getListOfProjectsByUserId(Integer userid)
	
	{
		
		String query = "from Project p where p.userid = ?";
		
		@SuppressWarnings("unchecked")
		List<Project> projects = (List<Project>) hibernateTemplate.find(query, userid);
		
		if(projects.isEmpty()) { return null; }
		
		return projects;
	}
	
	
	public List<Project> getListOfProjectsByTenantId(String tenantid)
	
	{		
		String query = "from Project p where p.tenantid = ?";
		
		@SuppressWarnings("unchecked")
		List<Project> projects = (List<Project>) hibernateTemplate.find(query, tenantid);
		
		if(projects.isEmpty()) { return null; }
		
		return projects;
	}
	
	
	
	
	
	
	
}
