package com.project.dao;

import java.util.List;

import com.project.entities.User;

public interface IDaoInterfaceForUser {

	public void save(User user);
	public void update(User user);
	public void delete(User user);
	public User getUserById(Integer userid);
	public User getUserByName(String name);
	public User getUserByIdAndUserName(Integer userid, String username);
	public List<User> getAllUsers();

}
