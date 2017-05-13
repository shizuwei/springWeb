package com.shizuwei.dal.main.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.shizuwei.dal.main.dao.UserDao;
import com.shizuwei.dal.main.mapper.UserMapper;
import com.shizuwei.dal.main.po.User;

@Repository
public class UserDaoImpl implements UserDao {
	@Resource
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	@Resource
	private UserMapper userMapper;

	@Override
	public List<User> getUserList() {
		// String sql = "select * from webdata.user";
		// return namedJdbcTemplate.query(sql, new
		// BeanPropertyRowMapper<User>(User.class));
		return userMapper.listAll();
	}

	@Override
	public User getUserByAccountNumber(String accountNumber) {
		if (StringUtils.isEmpty(accountNumber)) {
			return null;
		}
		String sql = "select * from webdata.user where account_number = :accountNumber";
		User param = new User();
		param.setAccountNumber(accountNumber);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(param);
		return namedJdbcTemplate.queryForObject(sql, paramSource, new BeanPropertyRowMapper<User>(User.class));

	}

	@Override
	public void fillUserId(User user) {

		Preconditions.checkNotNull(user);

		if (user.getId() != null) {
			return;
		}

		String accountNumber = user.getAccountNumber();
		Preconditions.checkArgument(StringUtils.isNotBlank(accountNumber));
		Map<String, Object> paramMap = Maps.newHashMap();
		String sql = "select * from webdata.user where account_number = :accountNumber";
		paramMap.put("accountNumber", accountNumber);
		namedJdbcTemplate.query(sql, paramMap, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				if (user.getId() == null) {
					user.setId(rs.getInt("id"));
				}
			}
		});
	}
}
