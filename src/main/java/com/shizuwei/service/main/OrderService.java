package com.shizuwei.service.main;

import com.shizuwei.dal.common.page.PageBean;
import com.shizuwei.dal.main.po.Order;
import com.shizuwei.dal.main.po.OrderGoods;
import com.shizuwei.service.dto.request.OrderAddRequest;
import com.shizuwei.service.dto.request.OrderListRequestDto;
import com.shizuwei.service.dto.response.OrderInfoResponseDto;

public interface OrderService {

	Order addOrder(OrderAddRequest order);

	void delOrderGoods(OrderGoods orderGoods);

	void editOrderGoods(OrderGoods orderGoods);

	PageBean<OrderInfoResponseDto> getOrders(OrderListRequestDto orderListRequestDto);

}
