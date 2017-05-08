package com.shizuwei.dal.main.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.shizuwei.dal.main.dao.GoodsDao;
import com.shizuwei.dal.main.po.Goods;

@Repository
public class GoodsDaoImpl implements GoodsDao {
	@Resource
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	@Override
	public List<Goods> getGoodsList() {
		String sql = "select * from webdata.goods";
		return namedJdbcTemplate.query(sql, new BeanPropertyRowMapper<Goods>(Goods.class));
	}

}
