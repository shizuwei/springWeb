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
import com.shizuwei.dal.main.dao.BrandDao;
import com.shizuwei.dal.main.po.Brand;

@Repository
public class BrandDaoImpl implements BrandDao {
	@Resource
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	@Override
	public List<Brand> getBrandList() {
		String sql = "select * from webdata.brand";
		return namedJdbcTemplate.query(sql, new BeanPropertyRowMapper<Brand>(Brand.class));
	}

	@Override
	public Brand getBrandByName(String brandName){
		if(StringUtils.isEmpty(brandName)){
			return null;
		}
		String sql = "select * from webdata.brand where brand_name = :brandName";
		Brand param = new Brand();
		param.setBrandName(brandName);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(param);
		return namedJdbcTemplate.queryForObject(sql, paramSource, new BeanPropertyRowMapper<Brand>(Brand.class));

	}
	
	@Override
	public void fillBrandId(Brand brand) {

		Preconditions.checkNotNull(brand);

		if (brand.getId() != null) {
			return;
		}
		String brandName = brand.getBrandName();
		Preconditions.checkArgument(StringUtils.isNotBlank(brandName));

		Map<String, Object> paramMap = Maps.newHashMap();
		String sql = "select * from webdata.brand where brand_name = :brandName";
		paramMap.put("brandName", brandName);
		namedJdbcTemplate.query(sql, paramMap, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				if (brand.getId() == null) {
					brand.setId(rs.getInt("id"));
				}
			}
		});
	}
}
