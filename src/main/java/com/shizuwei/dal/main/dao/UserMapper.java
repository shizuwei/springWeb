package com.shizuwei.dal.main.dao;

import com.shizuwei.dal.common.dao.BaseMapper;
import com.shizuwei.dal.main.po.User;

public interface UserMapper extends BaseMapper<Integer, User> {
	User getByNumber(String number);
}
