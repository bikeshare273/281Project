package com.project.configuration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;

import com.project.dao.IDaoInterfaceForLogin;
import com.project.dao.IDaoInterfaceForUser;
import com.project.dao.ITestDao;
import com.project.dao.LoginDao;
import com.project.dao.TestDao;
import com.project.dao.UserDao;
import com.project.implementation.AuthImplementation;
import com.project.implementation.IAuthInterfaceForLogin;
import com.project.implementation.UserImpl;
import com.project.interceptor.SessionValidatorInterceptor;
import com.project.util.AppUtils;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class AppConfiguration {

	/********************************************************************************************************************/

	/* Implementation Beans */

	/********************************************************************************************************************/

	@Bean
	public UserImpl getUserImpl() {
		return new UserImpl();
	}

	/********************************************************************************************************************/

	/* DAO Beans */

	/********************************************************************************************************************/

	@Bean
	public IDaoInterfaceForLogin getLoginDao() {
		return new LoginDao();
	}

	@Bean
	public IDaoInterfaceForUser getUserDao() {
		return new UserDao();
	}

	@Bean
	public ITestDao getTestDao() {
		return new TestDao();
	}

	/********************************************************************************************************************/

	/* Intercepter and Util Beans */

	/********************************************************************************************************************/

	@Bean
	public SessionValidatorInterceptor sessionValidatorInterceptor() {
		return new SessionValidatorInterceptor();
	}

	@Bean
	public IAuthInterfaceForLogin getAuthInterfaceForLogin() {
		return new AuthImplementation();
	}

	@Bean
	public AppUtils getAppUtils() {
		return new AppUtils();
	}

	/********************************************************************************************************************/

	/* Configuration Beans */

	/********************************************************************************************************************/

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
		dataSource
		.setUrl("jdbc:mysql://cmpe.cv3rwgqlacfe.us-east-1.rds.amazonaws.com:3306/cmpe");
		dataSource.setUsername("cmpeadmin");
		dataSource.setPassword("cmpeadmin");
		dataSource.setInitialSize(2);
		dataSource.setMaxTotal(5);
		return dataSource;
	}

	/********************************************************************************************************************/

	/**
	 * @return HibernateTemplate() This is bean creation method for
	 *         HibernateTemplate.
	 */
	@Bean
	public HibernateTemplate hibernateTemplate() {
		return new HibernateTemplate(sessionFactory());

	}

	/********************************************************************************************************************/

	/**
	 * @return SessionFactory() This is bean creation method for SessionFactory.
	 */
	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(
				dataSource());
		builder.scanPackages("com.project.*");
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect",
				"org.hibernate.dialect.MySQL5Dialect");
		builder.addProperties(hibernateProperties);
		return builder.buildSessionFactory();
	}

	/********************************************************************************************************************/

	/**
	 * @return HibernateTransactionManager() This is bean creation method for
	 *         HibernateTransactionManager.
	 */
	@Bean
	@Primary
	public HibernateTransactionManager hibTransMan() {
		return new HibernateTransactionManager(sessionFactory());
	}

	/********************************************************************************************************************/

}
