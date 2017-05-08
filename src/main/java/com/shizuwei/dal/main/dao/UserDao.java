package com.shizuwei.dal.main.dao;

import java.util.List;

import com.shizuwei.dal.main.po.User;

public interface UserDao {
	List<User> getUserList();

	User getUserByAccountNumber(String accountNumber);

	void fillUserId(User user);
	
}
