package com.shizuwei.dal.main.mapper;

import java.util.List;

import com.shizuwei.dal.main.po.User;

public interface UserMapper {
    void save(User user);  
    boolean update(User user);  
    boolean delete(int id);  
    User getById(int id);  
    List<User> listAll(); 
}
