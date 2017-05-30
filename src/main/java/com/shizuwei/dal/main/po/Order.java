package com.shizuwei.dal.main.po;

import java.sql.Date;
import java.util.List;

import com.shizuwei.dal.main.constants.GoodsStatus;
import com.shizuwei.dal.main.constants.OrderStatus;

import lombok.Data;

@Data
public class Order {
	/**
	 * key
	 */
	private Integer id;
	/**
	 * 用户
	 */
	private Integer userId;

	/**
	 * 总价 单位： 分
	 */
	private Long orderTotalPrice;
	/**
	 * 付钱时间
	 */
	private Date payTime;
	/**
	 * 下单时间
	 */
	private Date orderTime;
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

	private Integer goodsStatus;
	private String goodsStatusStr;

	public void setGoodsStatus(Integer goodsStatus) {
		this.goodsStatus = goodsStatus;
		if (goodsStatus != null) {
			this.goodsStatusStr = GoodsStatus.byCode(goodsStatus.intValue()).getNote();
		}
	}

	/**
	 * 描述
	 */
	private String discription;

	private List<OrderGoods> goodsList;

}
