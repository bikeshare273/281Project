package com.project.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
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
import com.project.dto.LoginDTO;
import com.project.entities.Test;
import com.project.implementation.IAuthInterfaceForLogin;
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
