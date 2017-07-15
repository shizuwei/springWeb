package com.shizuwei.service.main.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.shizuwei.controller.dto.OrderInfoListRequestDto;
import com.shizuwei.dal.common.page.PageBean;
import com.shizuwei.dal.common.page.PaginationContext;
import com.shizuwei.dal.main.dao.OrderMapper;
import com.shizuwei.dal.main.dao.UserMapper;
import com.shizuwei.dal.main.po.OrderInfo;
import com.shizuwei.dal.main.po.User;
import com.shizuwei.service.dto.request.OrderListRequestDto;
import com.shizuwei.service.main.MainService;
import com.shizuwei.utils.DebugUtils;
@Service("mainService")
public class MainServiceImpl implements MainService {

	private static final Logger logger = LoggerFactory.getLogger(MainServiceImpl.class);

	@Resource
	private OrderMapper orderMapper;
	@Resource
	private UserMapper userMapper;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public PageBean<OrderInfo> listOrder(OrderInfoListRequestDto infoRequst) {
		DebugUtils.transactionRequired("check trasaction.");
		OrderListRequestDto order = new OrderListRequestDto();
		if (infoRequst != null) {
			order.setOrderStatus(infoRequst.getPayStatus());
			order.setGoodsStatus(infoRequst.getArriveStatus());
			if (StringUtils.isNoneBlank(infoRequst.getUserNumber())) {
				User user = userMapper.getByNumber(infoRequst.getUserNumber());
				if (user != null) {
					order.setUserId(user.getUserId());
				}
			}
		}
		Integer pageNum = PaginationContext.getPageNum() == null ? 1 : PaginationContext.getPageNum();
		Integer pageSize = 5;
		PageHelper.startPage(pageNum, pageSize);
		List<Integer> ids = orderMapper.listOrderIds(order);
		logger.debug("ids={}", ids);
		PageBean<Integer> idsBean = new PageBean<>(ids);
		List<OrderInfo> orders = orderMapper.listWithGoodsByIds(ids);
		PageBean<OrderInfo> orderBean = new PageBean<>();
		orderBean.setList(orders);
		orderBean.setPageNum(idsBean.getPageNum());
		orderBean.setTotal(idsBean.getTotal());
		orderBean.setSize(idsBean.getSize());
		orderBean.setPages(idsBean.getPages());
		orderBean.setPageSize(idsBean.getPageSize());
		return orderBean;
	}

}
