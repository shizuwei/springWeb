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

import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.shizuwei.dal.common.page.PageBean;
import com.shizuwei.dal.common.page.PaginationContext;
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
import com.shizuwei.service.dto.request.OrderListRequestDto;
import com.shizuwei.service.dto.response.OrderInfoResponseDto;
import com.shizuwei.service.main.OrderService;
import com.shizuwei.utils.DebugUtils;
import com.shizuwei.utils.PriceUtil;

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
	public Order addOrder(OrderAddRequest orderAddRequest) {
		DebugUtils.transactionRequired("check trasaction.");
		Preconditions.checkNotNull(orderAddRequest.getGoodsCnt(), "必须填写商品数量！");
		//Preconditions.checkNotNull(orderAddRequest.getOrderStatus(), "必须填写订单状态！");
		Preconditions.checkNotNull(orderAddRequest.getGoodsStatus(), "必须填写商品状态！");
		Preconditions.checkNotNull(orderAddRequest.getGoodsId(), "必须填写商品ID！");
		Preconditions.checkNotNull(orderAddRequest.getUserId(), "必须填写用户ID！");
		User user = this.userMapper.getById(orderAddRequest.getUserId());
		Preconditions.checkNotNull(user, "用户不存在！");
		Goods goods = this.goodsMapper.getById(orderAddRequest.getGoodsId());
		Preconditions.checkNotNull(goods, "商品不存在!");
		BigDecimal goodsPrice = goods.getGoodsPrice();
		Preconditions.checkNotNull(goodsPrice, "商品价格为空！");
		Preconditions.checkNotNull(goods.getImgId(), "商品图片ID为空！");

		Img img = this.imgMapper.getById(goods.getImgId());
		Preconditions.checkNotNull(img, "商品图片不存在！");
		Preconditions.checkNotNull(img.getFolder(), "商品图片FOLDER错误！");

		// 寻找order,一个用户一次folder只需一个order
		List<Order> orders = this.orderMapper.getByFolder(orderAddRequest.getUserId(), img.getFolder());
		Order order = null;
		BigDecimal orderGoodsTotalPrice = PriceUtil.calOrderTotalPrice(goods, orderAddRequest.getGoodsCnt(),
				user.getRatio());
		logger.debug("addOrder orderGoodsTotalPrice = {} ", orderGoodsTotalPrice);
		if (CollectionUtils.isEmpty(orders)) {
			// 如果没有order，则直接生成一个order
			order = new Order();
			order.setOrderTime(new Date());

			order.setOrderTotalPrice(orderGoodsTotalPrice);
			order.setOrderFolder(img.getFolder());
			order.setUserId(orderAddRequest.getUserId());
			logger.debug("create new Order = {}", order);
			this.orderMapper.insert(order);
		} else {
			// 已有一个Order
			Preconditions.checkArgument(orders.size() == 1, "一个用户一次促销有且只能有一个订单!");
			order = orders.get(0);
			Preconditions.checkArgument(order.getUserId().equals(orderAddRequest.getUserId()), "userId not equal");
			BigDecimal orderTotalPrice = order.getOrderTotalPrice().add(orderGoodsTotalPrice);
			order.setOrderTotalPrice(orderTotalPrice);
			logger.debug("update Order = {}", order);
			this.orderMapper.update(order);
		}

		// orderGoods加入
		OrderGoods orderGoods = new OrderGoods();
		orderGoods.setOrderGoodsId(orderAddRequest.getOrderGoodsId());
		orderGoods.setGoodsCnt(orderAddRequest.getGoodsCnt());
		orderGoods.setGoodsId(orderAddRequest.getGoodsId());
		orderGoods.setGoodsStatus(orderAddRequest.getGoodsStatus());
		orderGoods.setOrderStatus(orderAddRequest.getOrderStatus());
		orderGoods.setOrderGoodsPrice(orderGoodsTotalPrice);
		orderGoods.setOrderId(order.getOrderId());
		orderGoods.setUserId(orderAddRequest.getUserId());
		logger.debug("add orderGoods={}", orderGoods);
		this.orderGoodsMapper.insert(orderGoods);

		orderAddRequest.setOrderGoodsId(orderGoods.getOrderGoodsId());

		return order;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delOrderGoods(OrderGoods orderGoods) {
		DebugUtils.transactionRequired("check trasaction.");
		Preconditions.checkNotNull(orderGoods.getOrderGoodsId());
		Preconditions.checkNotNull(orderGoods.getGoodsId());
		Preconditions.checkNotNull(orderGoods.getOrderId());
		Preconditions.checkNotNull(orderGoods.getUserId());

		OrderGoods toDelOrderGoods = this.orderGoodsMapper.getById(orderGoods.getOrderGoodsId());
		Preconditions.checkArgument(orderGoods.getGoodsId().equals(toDelOrderGoods.getGoodsId()));
		Preconditions.checkArgument(orderGoods.getOrderId().equals(toDelOrderGoods.getOrderId()));
		Preconditions.checkArgument(orderGoods.getUserId().equals(toDelOrderGoods.getUserId()));

		Order toDelOrder = this.orderMapper.getById(orderGoods.getOrderId());
		Preconditions.checkArgument(toDelOrder != null && toDelOrder.getOrderTotalPrice() != null);
		OrderGoods orderGoodsParam = new OrderGoods();
		orderGoodsParam.setOrderId(toDelOrderGoods.getOrderId());
		
		List<OrderGoods> orderGoodsList = this.orderGoodsMapper.list(orderGoodsParam);
		if (CollectionUtils.isEmpty(orderGoodsList)) {
			this.orderMapper.delById(orderGoods.getOrderId());
			Preconditions.checkArgument(toDelOrder.getOrderTotalPrice().compareTo(toDelOrderGoods.getOrderGoodsPrice()) <= 0, "金额错误，订单删完后，订单总额不能归0！");
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
		DebugUtils.transactionRequired("check trasaction.");
		Preconditions.checkNotNull(orderGoods.getOrderGoodsId());
		Preconditions.checkNotNull(orderGoods.getGoodsId());
		Preconditions.checkNotNull(orderGoods.getOrderId());
		Preconditions.checkNotNull(orderGoods.getUserId());
		OrderGoods toEditOrderGoods = this.orderGoodsMapper.getById(orderGoods.getOrderGoodsId());
		Preconditions.checkNotNull(toEditOrderGoods);
		Goods goods = this.goodsMapper.getById(orderGoods.getGoodsId());
		Preconditions.checkNotNull(goods);

		if (orderGoods.getUserId().equals(toEditOrderGoods.getUserId())) {
			// 没有修改用户
			if (orderGoods.getGoodsCnt() != null && !toEditOrderGoods.getGoodsCnt().equals(orderGoods.getGoodsCnt())) {
				// 修改了商品数量
				orderGoods.setOrderGoodsPrice(
						goods.getGoodsPrice().multiply(BigDecimal.valueOf(toEditOrderGoods.getGoodsCnt())));
			}
			this.orderGoodsMapper.update(orderGoods);

		} else {
			// 修改用户
			delOrderGoods(toEditOrderGoods);
			OrderAddRequest orderAddRequest = new OrderAddRequest();
			orderAddRequest.setGoodsCnt(orderGoods.getGoodsCnt());
			orderAddRequest.setGoodsId(orderGoods.getGoodsId());
			orderAddRequest.setGoodsStatus(orderGoods.getGoodsStatus() == null ? toEditOrderGoods.getGoodsStatus()
					: orderGoods.getGoodsStatus());
			orderAddRequest.setOrderStatus(orderGoods.getOrderStatus() == null ? toEditOrderGoods.getOrderStatus()
					: orderGoods.getOrderStatus());
			orderAddRequest.setUserId(orderGoods.getUserId());
			orderAddRequest.setOrderGoodsId(toEditOrderGoods.getOrderGoodsId());
			this.addOrder(orderAddRequest);
		}
	}

	@Override
	public PageBean<OrderInfoResponseDto> getOrders(OrderListRequestDto orderListRequestDto) {

		Integer pageNum = PaginationContext.getPageNum() == null ? 1 : PaginationContext.getPageNum();
		Integer pageSize = PaginationContext.getPageSize();
		logger.debug("pageNum={},pageSize={}", pageNum, pageSize);
		PageHelper.startPage(pageNum, pageSize);
		List<OrderInfoResponseDto> list = this.orderMapper.listOrders(orderListRequestDto);
		return new PageBean<>(list);
	}
}
