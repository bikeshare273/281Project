package com.project.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.project.entities.User;


@Transactional
public class UserDao implements IDaoInterfaceForUser {
	
	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	public User save(User user) {

		hibernateTemplate.save(user);
		
		return null;
	}

	@Override
	public User update(User user) {

		hibernateTemplate.update(user);
		
		return null;
	}

	@Override
	public void delete(User user) {

		hibernateTemplate.delete(user);
		
	}

	@Override
	public User getUserById(Integer userid) {

		String query = "from User u where u.userid = ?";
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) hibernateTemplate.find(query, userid);

		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
		
	}

	@Override
	public User getUserByName(String name)  {

		String query = "from User u where u.name = ?";
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) hibernateTemplate.find(query, name);

		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
		
	}
	
	@Override
	public User getUserByIdAndUserName(Integer userid, String username) {

		String query = "from User u where u.userid = ? and u.username = ?";
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) hibernateTemplate.find(query, userid, username);

		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
		
	}
	

	@Override
	public List<User> getAllUsers() {
		
		String query = "from User";
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) hibernateTemplate.find(query);

		if (users.isEmpty()) {
			return null;
		} else {
			return users;
		}
	}

}
