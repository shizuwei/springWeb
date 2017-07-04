package com.shizuwei.service.main.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Preconditions;
import com.shizuwei.dal.main.dao.GoodsMapper;
import com.shizuwei.dal.main.dao.ImgMapper;
import com.shizuwei.dal.main.dao.OrderGoodsMapper;
import com.shizuwei.dal.main.dao.OrderMapper;
import com.shizuwei.dal.main.dao.UserMapper;
import com.shizuwei.dal.main.po.Goods;
import com.shizuwei.dal.main.po.Img;
import com.shizuwei.dal.main.po.Order;
import com.shizuwei.dal.main.po.OrderGoods;
import com.shizuwei.dal.main.po.User;
import com.shizuwei.service.dto.request.OrderAddRequest;
import com.shizuwei.service.main.OrderService;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private GoodsMapper goodsMapper;
	@Resource
	private ImgMapper imgMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private OrderGoodsMapper orderGoodsMapper;
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void addOrder(OrderAddRequest orderAddRequest) {
		Preconditions.checkNotNull(orderAddRequest.getGoodsCnt());
		Preconditions.checkNotNull(orderAddRequest.getOrderStatus());
		Preconditions.checkNotNull(orderAddRequest.getGoodsStatus());
		Preconditions.checkNotNull(orderAddRequest.getGoodsId());
		Preconditions.checkNotNull(orderAddRequest.getUserId());
		User user = this.userMapper.getById(orderAddRequest.getUserId());
		Preconditions.checkNotNull(user);
		Goods goods = this.goodsMapper.getById(orderAddRequest.getGoodsId());
		Preconditions.checkNotNull(goods);
		BigDecimal goodsPrice = goods.getGoodsPrice();
		Preconditions.checkNotNull(goodsPrice);
		Preconditions.checkNotNull(goods.getImgId());
		/** 寻找order,一个用户一次folder只需一个order **/
		Img img = this.imgMapper.getById(goods.getImgId());
		Preconditions.checkNotNull(img);
		Preconditions.checkNotNull(img.getFolder());

		List<Order> orders = this.orderMapper.getByFolder(orderAddRequest.getUserId(), img.getFolder());
		Order order = null;
		if (CollectionUtils.isEmpty(orders)) {
			order = new Order();
			order.setOrderTime(new Date());
			order.setOrderTotalPrice(goods.getGoodsPrice().multiply(new BigDecimal(orderAddRequest.getGoodsCnt())));
			order.setOrderFolder(img.getFolder());
			order.setUserId(orderAddRequest.getUserId());
			logger.debug("add Order={}", order);
			this.orderMapper.insert(order);
		} else {
			Preconditions.checkArgument(orders.size() == 1);
			order = orders.get(0);
			order.setOrderTotalPrice(order.getOrderTotalPrice()
					.add(goods.getGoodsPrice().multiply(new BigDecimal(orderAddRequest.getGoodsCnt()))));
			logger.debug("update Order={}", order);
			this.orderMapper.update(order);
		}
		OrderGoods orderGoods = new OrderGoods();
		orderGoods.setGoodsCnt(orderAddRequest.getGoodsCnt());
		orderGoods.setGoodsId(orderAddRequest.getGoodsId());
		orderGoods.setGoodsStatus(orderAddRequest.getGoodsStatus());
		orderGoods.setOrderStatus(orderAddRequest.getOrderStatus());
		orderGoods.setOrderGoodsPrice(goods.getGoodsPrice().multiply(new BigDecimal(orderAddRequest.getGoodsCnt())));
		orderGoods.setOrderId(order.getOrderId());
		orderGoods.setUserId(orderAddRequest.getUserId());
		logger.debug("add orderGoods={}", orderGoods);
		this.orderGoodsMapper.insert(orderGoods);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delOrderGoods(OrderGoods orderGoods) {
		Preconditions.checkNotNull(orderGoods.getOrderGoodsId());
		Preconditions.checkNotNull(orderGoods.getGoodsId());
		Preconditions.checkNotNull(orderGoods.getOrderId());
		Preconditions.checkNotNull(orderGoods.getUserId());

		OrderGoods toDelOrderGoods = this.orderGoodsMapper.getById(orderGoods.getOrderGoodsId());
		Preconditions.checkArgument(orderGoods.getGoodsId().equals(toDelOrderGoods.getGoodsId()));
		Preconditions.checkArgument(orderGoods.getOrderId().equals(toDelOrderGoods.getOrderId()));
		Preconditions.checkArgument(orderGoods.getUserId().equals(toDelOrderGoods.getUserId()));

		Order toDelOrder = this.orderMapper.getById(orderGoods.getOrderId());
		Preconditions.checkNotNull(toDelOrder);
		if (toDelOrder.getOrderTotalPrice().compareTo(toDelOrderGoods.getOrderGoodsPrice()) <= 0) {
			this.orderMapper.delById(orderGoods.getOrderId());
		} else {
			BigDecimal price = toDelOrder.getOrderTotalPrice().subtract(toDelOrderGoods.getOrderGoodsPrice());
			toDelOrder.setOrderTotalPrice(price);
			this.orderMapper.update(toDelOrder);
		}

		this.orderGoodsMapper.delById(orderGoods.getOrderGoodsId());

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void editOrderGoods(OrderGoods orderGoods) {
		Preconditions.checkNotNull(orderGoods.getOrderGoodsId());
		Preconditions.checkNotNull(orderGoods.getGoodsId());
		Preconditions.checkNotNull(orderGoods.getOrderId());
		Preconditions.checkNotNull(orderGoods.getUserId());
		OrderGoods toEditOrderGoods = this.orderGoodsMapper.getById(orderGoods.getOrderGoodsId());
		Preconditions.checkNotNull(toEditOrderGoods);
		if (orderGoods.getUserId().equals(toEditOrderGoods.getUserId())) {
			this.orderGoodsMapper.update(orderGoods);
		} else {
			delOrderGoods(toEditOrderGoods);
			OrderAddRequest orderAddRequest = new OrderAddRequest();
			orderAddRequest.setGoodsCnt(orderGoods.getGoodsCnt());
			orderAddRequest.setGoodsId(orderGoods.getGoodsId());
			orderAddRequest.setGoodsStatus(orderGoods.getGoodsStatus());
			orderAddRequest.setOrderStatus(orderGoods.getGoodsStatus());
			orderAddRequest.setUserId(orderGoods.getUserId());
			this.addOrder(orderAddRequest);
		}
	}
}
