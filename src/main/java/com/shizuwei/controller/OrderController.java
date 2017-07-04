package com.shizuwei.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shizuwei.controller.common.response.Response;
import com.shizuwei.controller.common.response.ResponseBuilder;
import com.shizuwei.dal.main.po.OrderGoods;
import com.shizuwei.service.dto.request.OrderAddRequest;
import com.shizuwei.service.main.OrderService;

@Controller
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Resource
	private OrderService orderService;

	@RequestMapping(value = "order/add.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Response addOrder(@RequestBody OrderAddRequest order) {
		logger.debug("order = {}", order);
		this.orderService.addOrder(order);
		// Map<String, Object> data = Maps.newHashMap();
		// data.put("id", order.getOrderId());
		return new ResponseBuilder().setData(null).build();
	}
	
	
	@RequestMapping(value = "order/editOrderGoods.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Response editOrderGoods(@RequestBody OrderGoods orderGoods) {
		logger.debug("orderGoods = {}", orderGoods);
		this.orderService.editOrderGoods(orderGoods);
		return new ResponseBuilder().setData(null).build();
	}
	
	@RequestMapping(value = "order/delOrderGoods.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Response delOrderGoods(@RequestBody OrderGoods orderGoods) {
		logger.debug("orderGoods = {}", orderGoods);
		this.orderService.delOrderGoods(orderGoods);
		return new ResponseBuilder().setData(null).build();
	}
}
