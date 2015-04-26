package com.project.implementation;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;







import com.project.dao.IDaoInterfaceForLogin;
import com.project.dao.IDaoInterfaceForUser;
import com.project.dto.UserDTO;
import com.project.entities.Login;
import com.project.entities.User;
import com.project.util.AppUtils;

import org.apache.commons.beanutils.BeanUtils;

public class UserImpl {
	
	@Autowired
	IDaoInterfaceForUser usersDao;
	
	@Autowired
	IDaoInterfaceForLogin loginDao;

	@Autowired
	AppUtils appUtils;
	
	
/***********************************************************************************/
	
	public UserDTO createUser(UserDTO user)
	
	{
		
		User userObject = new User();
		Login loginObject = new Login();

		try { BeanUtils.copyProperties(userObject, user);} 
		catch (IllegalAccessException e) { e.printStackTrace(); } 
		catch (InvocationTargetException e) { e.printStackTrace(); }
		
		Integer userid = appUtils.generateIdValue(1000);
		userObject.setUserid(userid);
		//userObject.setEmail(user.getUsername());

		loginObject.setUserid(userid);
		loginObject.setUsername(user.getUsername());
		loginObject.setPassword(appUtils.passwordEncrypter(user.getPassword()));
	
		usersDao.save(userObject);
		loginDao.save(loginObject);
		
		user.setUserid(userid);
		
		return user;
		
	}
	
/************************************************************************************/
	
public UserDTO updateUser(UserDTO user)
			
{
		User userObject = usersDao.getUserById(user.getUserid());
		
		if(userObject == null) {return null;}
		
		try { BeanUtils.copyProperties(userObject, user);} 
		catch (IllegalAccessException e) { e.printStackTrace(); } 
		catch (InvocationTargetException e) { e.printStackTrace(); }
				
		usersDao.update(userObject);
		
		return user;
}
	
/************************************************************************************/
		
public UserDTO getUser(Integer userid)

{
		UserDTO userDTO = new UserDTO();

		User user = usersDao.getUserById(userid);
		Login login = loginDao.getLoginByUserId(userid);
		
		try { BeanUtils.copyProperties(userDTO, user);} 
		catch (IllegalAccessException e) { e.printStackTrace(); } 
		catch (InvocationTargetException e) { e.printStackTrace(); }
				
		userDTO.setUsername(login.getUsername());
		
		return userDTO;
}

/************************************************************************************/

	public List<User> getAllUsers()
	{
		List<User> allUsers = usersDao.getAllUsers();

		if (allUsers == null) {
			return null;
		}

		return allUsers;
	}

	
/************************************************************************************/
	
public boolean deleteUser(Integer userid)

{
		User userObject = usersDao.getUserById(userid);
		Login loginObject = loginDao.getLoginByUserId(userid);
		
		if(userObject == null) {return false;}
		if(loginObject == null) {return false;}
		
		usersDao.delete(userObject);
		loginDao.delete(loginObject);
		
		return true;
}	


/************************************************************************************/

public boolean checkUniqueUsername(String username)

{
		Login login = loginDao.getLoginByUserName(username);
			
		if(login == null) {return true;}
		else			 {return false;}

}

/************************************************************************************/
	
public Integer getUserIdByUsername(String username)

{
		Login login = loginDao.getLoginByUserName(username);
				
		if(login == null ) {return null;}
		
		Integer userid = login.getUserid(); 
		
		return userid;
}

/************************************************************************************/

}
