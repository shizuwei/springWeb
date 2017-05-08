package com.shizuwei.dal.main.dao;

import java.util.List;

import com.shizuwei.dal.main.po.Brand;

public interface BrandDao {
	
	List<Brand> getBrandList();

	Brand getBrandByName(String name);

	void fillBrandId(Brand brand);
}
