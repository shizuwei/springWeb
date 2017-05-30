package com.shizuwei.service.main.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.mysql.jdbc.log.Log;
import com.shizuwei.controller.MainController;
import com.shizuwei.controller.dto.OrderInfoListRequestDto;
import com.shizuwei.dal.common.page.PageBean;
import com.shizuwei.dal.common.page.PaginationContext;
import com.shizuwei.dal.main.dao.OrderMapper;
import com.shizuwei.dal.main.dao.UserMapper;
import com.shizuwei.dal.main.po.Order;
import com.shizuwei.dal.main.po.User;
import com.shizuwei.service.main.MainService;

@Service("mainService")
public class MainServiceImpl implements MainService {
	
	private static final Logger logger = LoggerFactory.getLogger(MainServiceImpl.class);
	
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private UserMapper userMapper;
	
	@Override
	public PageBean<Order> listOrder(OrderInfoListRequestDto infoRequst) {

	
		Order order = new Order();
		if (infoRequst != null) {
			order.setOrderStatus(infoRequst.getPayStatus());
			order.setGoodsStatus(infoRequst.getArriveStatus());
			if (StringUtils.isNoneBlank(infoRequst.getUserNumber())) {
				User user = userMapper.getByNumber(infoRequst.getUserNumber());
				if (user != null) {
					order.setUserId(user.getId());
				}
			}
		}
		Integer pageNum = PaginationContext.getPageNum() == null ? 1 : PaginationContext.getPageNum();
		Integer pageSize = 5;
		PageHelper.startPage(pageNum, pageSize);
		List<Integer> ids = orderMapper.listOrderIds(order);
		logger.debug("ids={}",ids);
		PageBean<Integer> idsBean = new PageBean<>(ids);
		List<Order> orders = orderMapper.listWithGoodsByIds(ids);
		PageBean<Order> orderBean = new PageBean<>();
		orderBean.setList(orders);
		orderBean.setPageNum(idsBean.getPageNum());
		orderBean.setTotal(idsBean.getTotal());
		orderBean.setSize(idsBean.getSize());
		orderBean.setPages(idsBean.getPages());
		orderBean.setPageSize(idsBean.getPageSize());
		return orderBean;
	}

}
