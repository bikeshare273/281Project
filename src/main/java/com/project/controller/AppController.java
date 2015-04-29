package com.project.controller;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.project.configuration.AppConfiguration;
import com.project.dao.IDaoInterfaceForLogin;
import com.project.dao.ITestDao;
import com.project.databuffers.NewProjectDTO;
import com.project.databuffers.ProjectData;
import com.project.dto.DisplayData;
import com.project.dto.GraphData;
import com.project.dto.LoginDTO;
import com.project.dto.ProjectIdAndNameDTO;
import com.project.dto.ProjectStatusDTO;
import com.project.dto.SearchDTO;
import com.project.dto.UserDTO;
import com.project.entities.Test;
import com.project.implementation.IAuthInterfaceForLogin;
import com.project.implementation.MultiTenantAPIImpl;
import com.project.implementation.MultiTenantImpl;
import com.project.implementation.ProjectGraphData;
import com.project.implementation.ProjectStatus;
import com.project.implementation.TenantFieldProjectImpl;
import com.project.implementation.UserImpl;
import com.project.interceptor.SessionValidatorInterceptor;


@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping("/api/v1/*")
@Import(AppConfiguration.class)
public class AppController extends WebMvcConfigurerAdapter{

	@Autowired
	IAuthInterfaceForLogin authInterfaceForLogin;
	
	@Autowired
	SessionValidatorInterceptor sessionValidatorInterceptor;
	
	@Autowired
	IDaoInterfaceForLogin loginDao;
		
	@Autowired
	UserImpl userImpl;
	
	@Autowired
	TenantFieldProjectImpl tenantAndFieldImpl;
	
	@Autowired
	MultiTenantImpl multiTenantImpl;

	@Autowired
	ITestDao testDao;
	
	@Autowired
	MultiTenantAPIImpl apiImpl;
	
	@Autowired
	ProjectStatus projectStatus;
	
	@Autowired
	ProjectGraphData projectGraphData;
	
/***********************************************************************************************/

						/* Login, Logout and Session Management */

/***********************************************************************************************/

@Override
public void addInterceptors(InterceptorRegistry registry) {
registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/api/v1/loggedin");
}

@RequestMapping(value="/loggedin", method = RequestMethod.GET)
@ResponseBody
private boolean logeedin() {
return true;
}


@RequestMapping("/login")
@ResponseBody
private LoginDTO login(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
loginDTO = authInterfaceForLogin.login(loginDTO);
response.addCookie(new Cookie("sessionid", Integer.toString(loginDTO.getSessionId())));
response.addCookie(new Cookie("username", loginDTO.getUsername()));
response.addCookie(new Cookie("userid", Integer.toString(loginDTO.getUserid())));
return loginDTO;
}

@RequestMapping("/logout")
@ResponseBody
private boolean logout(HttpServletResponse response) {

response.addCookie(new Cookie("sessionid", ""));
response.addCookie(new Cookie("username", ""));
response.addCookie(new Cookie("userid", ""));

return true;
}


@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/verifyUniqueUsername/{username}", method = RequestMethod.GET)
@ResponseBody
public boolean checkUniqueUsername(@PathVariable String username) {
return userImpl.checkUniqueUsername(username);
}
	
/***********************************************************************************/

								/* User Management APIs */

/***********************************************************************************/
	

@ResponseStatus(HttpStatus.CREATED)
@RequestMapping(value = "/createuser", method = RequestMethod.POST)
@ResponseBody
public UserDTO createUser(@Valid @RequestBody UserDTO user) {
	return userImpl.createUser(user);
}

@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/fetchuser", method = RequestMethod.GET)
@ResponseBody
public UserDTO fetchUser(@CookieValue("userid") int userid) {

	return userImpl.getUser(userid);		

}

@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
@ResponseBody
public void deleteUser(@RequestBody SearchDTO searchDTO) {

	Integer userid = Integer.parseInt(searchDTO.getSearchString());
	
	userImpl.deleteUser(userid);
}

/***********************************************************************************/

							/* REST APIs */

/***********************************************************************************/

@ResponseStatus(HttpStatus.CREATED)
@RequestMapping(value = "/createproject", method = RequestMethod.POST)
@ResponseBody
//public boolean createProject(@PathVariable String Tenant_Id) {
public ProjectData createProject(@RequestBody NewProjectDTO newProjectDTO, @CookieValue ("userid") int userid) {	
	
	String tenantid = newProjectDTO.getTenantid();
	String projectname = newProjectDTO.getProjectname();
	String projectId = apiImpl.createProject(tenantid, userid, projectname);
	System.out.println("createProject "+projectId);
	ProjectData projectData = new ProjectData();
	projectData.setProjectId(projectId);
	return projectData;	
}


@ResponseStatus(HttpStatus.CREATED)
@RequestMapping(value = "/updateproject", method = RequestMethod.POST)
@ResponseBody
public boolean Project(@RequestBody ProjectData projectData) {	
	
/*	ProjectData projectData = new ProjectData();
	ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String,String>>();	
	HashMap<String, String> hashMap = new HashMap<String, String>();
	HashMap<String, String> hashMap1 = new HashMap<String, String>();
	
	projectData.setProjectId("P84557795");
	projectData.setProjectName("Project 1");
	projectData.setTenantId("G");
	projectData.setTableName("Resources");
	
	//data 1
	hashMap.put("task_id", "T6");
	hashMap.put("start_date", "01/10/2015");
	hashMap.put("end_date", "01/11/2015");
	hashMap.put("worker", "Gaurav");
	arrayList.add(hashMap);
	
	//data 2 
	hashMap1.put("task_id", "T5");
	hashMap1.put("start_date", "01/10/2016");
	hashMap1.put("end_date", "01/11/2016");
	hashMap1.put("worker", "Vaibhav");
	arrayList.add(hashMap1);

	projectData.setRows(arrayList);*/
	
	return apiImpl.updateProject(projectData);	
}


@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/getproject", method = RequestMethod.POST)
@ResponseBody
public ArrayList<DisplayData> getProject(@RequestBody SearchDTO searchDTO) {

	String projectid = searchDTO.getSearchString(); 

	return multiTenantImpl.getProject(projectid);

}


@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/getAllTenants", method = RequestMethod.GET)
@ResponseBody
public List<String> getAllTenants() {

	return tenantAndFieldImpl.getDistinctTenants();
}

/*
@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/getprojectsbyuserid", method = RequestMethod.GET)
@ResponseBody
public List<String> getAllProjectIdsByuserId(@CookieValue ("userid") int userid ) {

	return tenantAndFieldImpl.getProjectIdsByuserId(userid);
}
*/


@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/getprojectsbyuserid", method = RequestMethod.GET)
@ResponseBody
public List<ProjectIdAndNameDTO> getAllProjectIdsByuserId(@CookieValue ("userid") int userid ) {

	return tenantAndFieldImpl.getProjectsByuserId(userid);
}


@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/getProjectStatus", method = RequestMethod.POST)
@ResponseBody
public List<String> getProjectStatus(@RequestBody ProjectStatusDTO projectStatusDTO){
	
	List<String> projectStatus_list = new ArrayList<String>();
	projectStatus_list = projectStatus.getProjectCompletionStatus(projectStatusDTO.getProject_Id());

	return projectStatus_list;
}


@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/getProjectGraphData", method = RequestMethod.POST)
@ResponseBody
public GraphData getProjectGraphData(@RequestBody ProjectStatusDTO projectStatusDTO){
	
	String project_Id = projectStatusDTO.getProject_Id();
	GraphData graphData = new GraphData();
	graphData = projectGraphData.getGraphData(project_Id);

	return graphData;
}

/***********************************************************************************/























/***********************************************************************************/
		
								/* Test APIs */

/***********************************************************************************/
	@RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Test dbtest(@PathVariable int id) {

		Test test = new Test();

		DateTime dt = new DateTime();

		test.setTestIdString(dt.toString());
		test.setTestString("Last loaded record => " + id);
		testDao.saveTest(test);
		Test savedTest = testDao.getTest(test);

		return savedTest;

	}

/***********************************************************************************/

}
