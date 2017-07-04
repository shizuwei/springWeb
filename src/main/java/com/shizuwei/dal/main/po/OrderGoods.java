package com.shizuwei.dal.main.po;

import java.math.BigDecimal;

import com.shizuwei.dal.main.constants.GoodsStatus;
import com.shizuwei.dal.main.constants.OrderStatus;

import lombok.Data;

@Data
public class OrderGoods {
	private Integer orderGoodsId;
	private Integer orderId;
	private Integer goodsId;
	private Integer goodsCnt;
	private Integer userId;
	/**
	 * 当前订单，针对一个商品的总价格
	 */
	private BigDecimal orderGoodsPrice;
	private String orderGoodsDescription;
	
	private Integer goodsStatus;
	private String goodsStatusStr;

	public void setGoodsStatus(Integer goodsStatus) {
		this.goodsStatus = goodsStatus;
		if (goodsStatus != null) {
			this.goodsStatusStr = GoodsStatus.byCode(goodsStatus.intValue()).getNote();
		}
	}
	
	/**
	 * 状态 已经付款：1， 未付款：0
	 */
	private Integer orderStatus;

	private String orderStatusStr;

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
		if (orderStatus != null) {
			this.orderStatusStr = OrderStatus.byCode(orderStatus.intValue()).getNote();
		}
	}

}
