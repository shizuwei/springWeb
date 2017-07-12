package com.shizuwei.dal.main.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shizuwei.dal.common.dao.BaseMapper;
import com.shizuwei.dal.main.po.Order;
import com.shizuwei.dal.main.po.OrderInfo;
import com.shizuwei.service.dto.request.OrderListRequestDto;
import com.shizuwei.service.dto.response.OrderInfoResponseDto;

public interface OrderMapper extends BaseMapper<Integer, Order> {

	List<OrderInfo> listWithGoods(OrderListRequestDto order);

	List<OrderInfo> listWithGoodsByIds(List<Integer> ids);

	List<Integer> listOrderIds(OrderListRequestDto order);

	List<Order> getByFolder(@Param("userId") Integer userId, @Param("folder") String folder);
	
	void updateOrderTotalPriceById(Integer id);
	
	List<OrderInfoResponseDto> listOrders(OrderListRequestDto orderListRequestDto);

}
