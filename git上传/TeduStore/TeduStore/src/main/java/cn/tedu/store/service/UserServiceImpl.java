package cn.tedu.store.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.tedu.store.bean.User;
import cn.tedu.store.mapper.UserMapper;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserMapper userMapper;
	//从Spring中找到一个BeanID为config的bean,获取
	//其salt属性的值，注入到salt变量中
	@Value("#{config.salt}")
	private String salt = "你吃了吗";
	
	public void register(User user) {
		User userTest = findUserByUsername(user.getUsername());
		if(userTest!=null) {
			return ;
		}
		userMapper.createUser(user);
	}
	
	public User findUserByUsername(String username) {
		User user = userMapper.findUserByUsername(username);
		return user;
	}
	public User findUserById(Integer id) {
		User user = userMapper.findUserById(id);
		return user;
	}
	public boolean checkPhoneExists(String phone) {
		return userMapper.getRecordCountByPhone(phone)>0;
	}

	public boolean checkEmailExists(String email) {
		
		return userMapper.getRecordCountByEmail(email)>0;
	}

	public void register(String username, String password, String phone, String email) {
		User user = new User();
		Date date = new Date();
		user.setUsername(username);
		//密码摘要加密

		System.out.println(salt);
		String pwd = DigestUtils.md5Hex(password+salt);
		System.out.println(pwd);
		
		user.setPassword(pwd);
		user.setPhone(phone);
		user.setEmail(email);
		user.setDisabled(0);
		user.setCreatedTime(date);
		user.setCreatedUser("System");
		user.setModifiedUser("System");
		user.setModifiedTime(date);
		System.out.println(user);
		register(user);
		
		
	}

	public User login(String username, String password) {
		
		
		//为了测试AOP功能添加的测试代码
		System.out.println("登录功能");
		
		if(username.equals("nlrandzbf1")) {
			System.out.println("异常");
			throw new RuntimeException("出异常了");
		}
		
		User u = userMapper.findUserByUsername(username);
		if(u==null) {
			return null;
		}else {
			//比较摘要加密以后的密码
			System.out.println("------------------------password:"+password);
			String pwd = DigestUtils.md5Hex(password+salt);
			if(u.getPassword().equals(pwd)) {
				return u;
			}else {
				return null;
			}
		}
	}

	public void updateUserInfo(String username, String phone, String email, Integer id) {
		User user = findUserById(id);
		if(username==null||"".equals(username)) {
			username = user.getUsername();
		}
		if(email==null||"".equals(email)) {
			email = user.getEmail();
		}
		if(phone==null||"".equals(phone)) {
			phone = user.getPhone();
		}
		
		
		userMapper.updateUserInfo(id, username, phone, email);
	}

	public int updatePassword(Integer uid, String oldPassword, String newPassword) {
		
		User user = userMapper.findUserById(uid);
	    // 判断oldPassword和刚才获取的数据中的密码是否一致
		String pwd = DigestUtils.md5Hex(newPassword+salt);
	    if (user.getPassword().equals(pwd)) {
	        // 如果一致，则通过持久层修改密码
	        userMapper.updatePassword(uid, newPassword);
	        // 返回“成功”
	        return 1;
	    } else {
	        // 如果不一致，不允许修改密码，返回“失败”
	        return -1;
	    }
		
	}
	
	
}
