package com.project.controller;

import java.util.ArrayList;
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
import com.project.dto.DisplayData;
import com.project.dto.LoginDTO;
import com.project.entities.Test;
import com.project.implementation.IAuthInterfaceForLogin;
import com.project.implementation.MultiTenantImpl;
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

								/* REST APIs */

/***********************************************************************************/
	
@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/getAllTenants", method = RequestMethod.GET)
@ResponseBody
public List<String> getAllTenants() {

	return tenantAndFieldImpl.getDistinctTenants();
}


@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/getproject", method = RequestMethod.GET)
@ResponseBody
public ArrayList<DisplayData> getProject() {

	return multiTenantImpl.getProject("P1");


}













/*
@ResponseStatus(HttpStatus.CREATED)
@RequestMapping(value = "/createproject", method = RequestMethod.POST)
@ResponseBody
public String createProject(@RequestBody ProjectData projectData, @CookieValue("userid") int userid ) {	
	String tenantid = "GANTTER";
	String username = loginDao.getUserNameByUserId(userid);
	
	return apiImpl.createProject(tenantid, username);	
}


@ResponseStatus(HttpStatus.CREATED)
@RequestMapping(value = "/saveproject", method = RequestMethod.POST)
@ResponseBody
//public boolean saveProject(@PathVariable ProjectData projectData) {
public boolean Project(@RequestBody ProjectData projectData, @CookieValue("userid") int userid) {	
	
	String username = loginDao.getUserNameByUserId(userid);
	
	ProjectData projectData = new ProjectData();
	ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String,String>>();	
	HashMap<String, String> hashMap = new HashMap<String, String>();
	
	projectData.setProjectId("P1");
	projectData.setProjectName("Project 1");
	projectData.setTenantId("G");
	projectData.setTableName("Task");
	
	//data 1
	hashMap.put("task_id", "T1");
	hashMap.put("start_date", "01/10/2015");
	hashMap.put("end_date", "01/11/2015");
	hashMap.put("worker", "Gaurav");
	arrayList.add(hashMap);
	
	//data 2 
	hashMap.put("task_id", "T2");
	hashMap.put("start_date", "01/10/2016");
	hashMap.put("end_date", "01/11/2016");
	hashMap.put("worker", "Vaibhav");
	arrayList.add(hashMap);

	
	return apiImpl.saveProject(projectData);	
}

*/




























	
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
