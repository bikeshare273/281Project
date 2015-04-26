package com.project.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.project.entities.Login;


@Transactional
public class LoginDao implements IDaoInterfaceForLogin {

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	public Login save(Login login) {

		hibernateTemplate.save(login);

		return null;
	}

	@Override
	public Login update(Login login) {

		hibernateTemplate.update(login);

		return null;
	}

	@Override
	public void delete(Login login) {

		hibernateTemplate.delete(login);

	}

	@Override
	public List<Login> getAllLogins() {

		String query = "from Login";
		@SuppressWarnings("unchecked")
		List<Login> allLogins = (List<Login>) hibernateTemplate.find(query);

		if (allLogins.isEmpty()) {
			return null;
		} else {
			return allLogins;
		}
	}

	@Override
	public Login getLoginByUserId(Integer userid) {

		String query = "from Login l where l.userid = ?";
		@SuppressWarnings("unchecked")
		List<Login> logins = (List<Login>) hibernateTemplate
		.find(query, userid);

		if (logins.isEmpty()) {
			return null;
		} else {
			return logins.get(0);
		}
	}

	@Override
	public Login getLoginByUserName(String username) {

		String query = "from Login l where l.username = ?";
		@SuppressWarnings("unchecked")
		List<Login> logins = (List<Login>) hibernateTemplate.find(query,
				username);

		if (logins.isEmpty()) {
			return null;
		} else {
			return logins.get(0);
		}
	}

	@Override
	public Login getLoginByUserNameAndPassword(String username, String password) {

		String query = "from Login l where l.username = ? and l.password = ?";
		@SuppressWarnings("unchecked")
		List<Login> logins = (List<Login>) hibernateTemplate.find(query,
				username, password);

		if (logins.isEmpty()) {
			return null;
		} else {
			return logins.get(0);
		}

	}

	@Override
	public Login getLoginByUserNameAndSessionId(String username, Integer sessionid) {

		String query = "from Login l where l.username = ? and l.sessionid = ?";
		@SuppressWarnings("unchecked")
		List<Login> logins = (List<Login>) hibernateTemplate.find(query, username, sessionid);

		if (logins.isEmpty()) {
			return null;
		} else {
			return logins.get(0);
		}

	}

}
