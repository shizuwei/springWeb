package com.shizuwei.dal.main.dao;

import java.util.List;

import com.shizuwei.dal.main.po.Brand;

public interface BrandMapper {
	
	List<Brand> list(Brand brand);

	void insert(Brand brand);
	
	Brand getById(Integer id);
	
	void update(Brand brand);
}
