package com.project.implementation;



import org.springframework.beans.factory.annotation.Autowired;

import com.project.dao.IDaoInterfaceForLogin;
import com.project.dto.LoginDTO;
import com.project.entities.Login;
import com.project.util.AppUtils;



public class AuthImplementation implements IAuthInterfaceForLogin{

	@Autowired
	IDaoInterfaceForLogin loginDao;

	@Autowired
	AppUtils appUtils;
	

	@Override
	public LoginDTO login(LoginDTO loginDTO) {

		String username = loginDTO.getUsername();
		String password = loginDTO.getPassword();
		System.out.println("password-> "+password);
		password = appUtils.passwordEncrypter(password);
		System.out.println("password en -> "+password);
		try{

			Login login = loginDao.getLoginByUserNameAndPassword(username, password);
			
			if(login == null){

			loginDTO.setMessage("Invalid Username/Password");
			loginDTO.setLoginValidationStatus(false);
			}
	
			else
	
			{
				/* Generate New SessionId */
				
				Integer sessionid = appUtils.generateIdValue(0);
				
				login.setSessionid(sessionid);
				
				loginDao.update(login);
				
				//set session id in header
				loginDTO.setSessionId(sessionid);
				loginDTO.setUserid(login.getUserid());
				loginDTO.setMessage("Login Sucessful");
				loginDTO.setLoginValidationStatus(true);
			}
	}catch(Exception e){
		e.printStackTrace();
	}
	return loginDTO;
	}
}

