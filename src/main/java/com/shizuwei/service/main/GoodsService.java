package com.shizuwei.service.main;

import com.shizuwei.dal.main.po.Goods;

public interface GoodsService {
	String importGoods(String path);

	Integer insert(Goods goods);
	
	void edit(Goods goods);
	
	void delete(Integer id);
	
}
