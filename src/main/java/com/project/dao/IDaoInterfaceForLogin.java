package com.project.dao;

import java.util.List;

import com.project.entities.Login;


public interface IDaoInterfaceForLogin {
	
	public Login save(Login login);
	public Login update(Login login);
	public void   delete(Login login);
	public List<Login> getAllLogins();
	public Login getLoginByUserId(Integer userid);
	public Login getLoginByUserName(String username);
	public String getUserNameByUserId(Integer userid);
	public Login getLoginByUserNameAndPassword(String username, String password);
	public Login getLoginByUserNameAndSessionId(String username, Integer sessionid);
	
}
