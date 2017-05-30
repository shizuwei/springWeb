package com.shizuwei.dal.main.dao;

import java.util.List;

import com.shizuwei.dal.main.po.Goods;

public interface GoodsMapper {

	void insert(Goods goods);

	boolean update(Goods goods);

	boolean delete(int id);

	Goods getById(int id);

	List<Goods> list(Goods goods);
}
