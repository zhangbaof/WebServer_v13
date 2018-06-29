package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.bean.User;

public interface UserMapper {

	void createUser(User user);
	User findUserByUsername(String username);
	User findUserById(@Param("id")Integer id);
	Integer getRecordCountByEmail(@Param("email")String email);
	Integer getRecordCountByPhone(@Param("phone")String phone);
	List<User> findAllUser();
	void updateUserInfo(@Param("id")Integer id,@Param("username")String username,@Param("phone")String phone,@Param("email")String email);
	void updatePassword(@Param("id")Integer id,@Param("password") String newPassword);
	
}
