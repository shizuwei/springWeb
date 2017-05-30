package com.shizuwei.dal.main.dao;

import java.util.List;

import com.shizuwei.dal.main.po.Order;

public interface OrderMapper {
	
	/**
	 * 保存订单
	 * @param order
	 */
	void insert(Order order);
	
	/**
	 * 修改订单
	 * @param order
	 * @return
	 */
	boolean update(Order order);
	
	/**
	 * 删除订单
	 * @param id
	 * @return
	 */
	boolean delete(Integer id);
	
	/**
	 * 获取订单
	 * @param id
	 * @return
	 */
	Order getById(Integer id);
	
	/**
	 * list
	 * @param order
	 * @return
	 */
	List<Order> listWithGoods(Order order);
	
	List<Order> listWithGoodsByIds(List<Integer> ids);
	
	List<Integer> listOrderIds(Order order);
	

}
