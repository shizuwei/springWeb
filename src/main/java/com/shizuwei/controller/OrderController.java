package com.shizuwei.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.shizuwei.controller.common.AuthInfo;
import com.shizuwei.controller.common.response.Response;
import com.shizuwei.dal.common.page.PageBean;
import com.shizuwei.dal.main.po.Order;
import com.shizuwei.dal.main.po.OrderGoods;
import com.shizuwei.service.dto.request.OrderAddRequest;
import com.shizuwei.service.dto.request.OrderListRequestDto;
import com.shizuwei.service.dto.response.OrderInfoResponseDto;
import com.shizuwei.service.main.OrderService;

@Controller
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Resource
	private OrderService orderService;

	@RequestMapping(value = "order/add.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Response addOrder(@RequestBody OrderAddRequest order) {
		try {
			logger.debug("order = {}", order);
			Order orderOf = this.orderService.addOrder(order);
			Map<String, Object> data = Maps.newHashMap();
			data.put("order", orderOf);
			data.put("orderGoodsId", order.getOrderGoodsId());
			return Response.data(data);
		} catch (Exception ex) {
			return Response.builder().error(ex.getMessage()).create();
		}
	}

	@RequestMapping(value = "order/editOrderGoods.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Response editOrderGoods(@RequestBody OrderGoods orderGoods) {
		try {
			logger.debug("orderGoods = {}", orderGoods);
			this.orderService.editOrderGoods(orderGoods);
			return Response.create();
		} catch (Exception ex) {
			return Response.builder().error(ex.getMessage()).create();
		}
	}

	@RequestMapping(value = "order/delOrderGoods.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Response delOrderGoods(@RequestBody OrderGoods orderGoods) {
		try {
			logger.debug("orderGoods = {}", orderGoods);
			this.orderService.delOrderGoods(orderGoods);
			return Response.create();
		} catch (Exception ex) {
			return Response.builder().error(ex.getMessage()).create();
		}
	}

	@RequestMapping(value = "order/getOrders.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Response getOrders(@RequestBody OrderListRequestDto orderListRequestDto) {
		try {
			if (orderListRequestDto.getUserId() == null) {
				orderListRequestDto.setUserId(AuthInfo.getUser().getUserId());
			}
			logger.debug("orderListRequestDto = {}", orderListRequestDto);
			PageBean<OrderInfoResponseDto> page = this.orderService.getOrders(orderListRequestDto);
			return Response.data(page.getList()).setPage(page);
		} catch (Exception ex) {
			return Response.builder().error(ex.getMessage()).create();
		}
	}
}
