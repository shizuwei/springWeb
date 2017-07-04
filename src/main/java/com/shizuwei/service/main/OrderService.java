package com.shizuwei.service.main;

import com.shizuwei.dal.main.po.OrderGoods;
import com.shizuwei.service.dto.request.OrderAddRequest;

public interface OrderService {

	void addOrder(OrderAddRequest order);
	
	void delOrderGoods(OrderGoods orderGoods);
	
	void editOrderGoods(OrderGoods orderGoods);
	
}
