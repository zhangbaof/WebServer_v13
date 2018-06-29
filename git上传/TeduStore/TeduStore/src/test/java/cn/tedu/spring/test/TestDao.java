package cn.tedu.spring.test;

import java.util.Date;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import cn.tedu.store.bean.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.UserService;

public class TestDao {

	
	
	DispatcherServlet ds;
	CharacterEncodingFilter fs;
	InternalResourceViewResolver dss;
	DataSourceTransactionManager am;
	@Test 
	public void testDbcpConnection() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-dao.xml");
		BasicDataSource dsss = ctx.getBean("ds",BasicDataSource.class);
		System.out.println(dsss.getUsername());
	}
	@Test 
	public void testCreateUser() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-dao.xml");
		BasicDataSource dsss = ctx.getBean("ds",BasicDataSource.class);
		System.out.println(dsss.getUsername());
		UserMapper dao = ctx.getBean("userMapper",UserMapper.class);
		User user = new User(null,"zbfr","1234a","45678914a","aa@qq.com.cn",null,null,null,"nlr",new Date());
		dao.createUser(user);
	}
	
	@Test
	public void testFindUserByUsername() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-dao.xml");
		BasicDataSource dsss = ctx.getBean("ds",BasicDataSource.class);
		UserMapper dao = ctx.getBean("userMapper",UserMapper.class);
		User user = dao.findUserByUsername("zbf");
		System.out.println(user);
	}
	@Test
	public void testServiceFindUsername() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-dao.xml","spring-service.xml");
		UserService service = ctx.getBean("userService",UserService.class);
		User user = service.findUserByUsername("zbf");
		System.out.println(user);
	}
	
	
	@Test 
	public void testServiceCreateUser() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-dao.xml","spring-service.xml");
		UserService service = ctx.getBean("userService",UserService.class);
		User user = new User();
		user.setUsername("zbf&");
		user.setEmail("789@qq.com");
		user.setPhone("456789");
		user.setPassword("543121321");
		service.register(user);
	}
	
	@Test
	public void testFindAllUser() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-dao.xml","spring-service.xml");
		UserMapper mapper = ctx.getBean("userMapper",UserMapper.class);
		List<User> list = mapper.findAllUser();
		for(User user:list) {
			System.out.println(user);
		}
		
	}
	
	
	
	
	
	
	
	
}
