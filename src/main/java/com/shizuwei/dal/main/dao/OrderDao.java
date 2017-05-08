package com.shizuwei.dal.main.dao;

import java.util.List;

import com.shizuwei.dal.main.po.DetailOrder;
import com.shizuwei.dal.main.po.Order;
import com.shizuwei.service.dto.request.OrderListReqestDto;

public interface OrderDao {
	Order getOrder(Integer id);
	
	List<DetailOrder> getDetailOrderList(OrderListReqestDto request);
}
