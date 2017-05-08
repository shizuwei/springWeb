package com.shizuwei.dal.test.dao.impl;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.shizuwei.dal.test.dao.TestDao;
import com.shizuwei.dal.test.po.TestPo;
@Repository
public class TestDaoImpl implements TestDao {	
	
	@Resource
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	public TestPo getTestPo(Integer id) {
		String sql = "select * from webdata.test_table where id = :id";
		TestPo param = new TestPo();
		param.setId(1);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(param);
		return namedJdbcTemplate.queryForObject(sql, paramSource, new BeanPropertyRowMapper<TestPo>(TestPo.class));
	}
}
