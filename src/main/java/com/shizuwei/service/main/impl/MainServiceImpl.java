package com.shizuwei.service.main.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shizuwei.dal.main.dao.OrderDao;
import com.shizuwei.dal.main.po.DetailOrder;
import com.shizuwei.service.dto.OrderListResponseDto;
import com.shizuwei.service.dto.request.OrderListReqestDto;
import com.shizuwei.service.main.MainService;

@Service("mainService")
public class MainServiceImpl implements MainService {
	@Resource
	private OrderDao orderDao;
	@Override
	public OrderListResponseDto getOrderListInfo(OrderListReqestDto request) {
		OrderListResponseDto responseDto = new OrderListResponseDto();
		List<DetailOrder> orderList = orderDao.getDetailOrderList(request);
		orderList.forEach( item -> responseDto.calcStatstic(item));
		responseDto.setDetailOrderList(orderList);
		return responseDto;
	}
}
