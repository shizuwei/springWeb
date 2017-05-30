package com.shizuwei.dal.main.dao;

import java.util.List;

import com.shizuwei.dal.main.po.User;

public interface UserMapper {
	void insert(User user);

	boolean update(User user);

	boolean delete(int id);

	User getById(int id);
	
	User getByNumber(String number);

	List<User> list();
}
