package cn.tedu.store.service;

import cn.tedu.store.bean.User;

public interface UserService {

	User findUserByUsername(String username);
	User findUserById(Integer id);
	void register(User user);
	boolean checkPhoneExists(String phone);
	boolean checkEmailExists(String email);
	void register(String username,String password,String phone
			,String email);
	User login(String username,String password);
	void updateUserInfo(String username,String phone,String email,Integer id);
	int updatePassword(Integer id,String oldPassword,String newPassword);
	
	
	
}
