package com.shizuwei.dal.main.po;

import java.sql.Date;

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
	 * 购买的商品
	 */
	private Integer goodsId;
	/**
	 * 数量 单位：个
	 */
	private Integer count;
	/**
	 * 运费 单位：分
	 */
	private Long freight;
	/**
	 * 汇率 %
	 */
	private Integer exchangeRate;
	/**
	 * 总价 单位： 分
	 */
	private Long totalPrice;
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
	
	public void setOrderStatus(Integer orderStatus){
		this.orderStatus = orderStatus;
		this.orderStatusStr = OrderStatus.byCode(orderStatus.intValue()).getNote();
	}
	
	private Integer goodsStatus;
	private String goodsStatusStr;
	public void setGoodsStatus(Integer goodsStatus){
		this.goodsStatus = goodsStatus;
		this.goodsStatusStr = GoodsStatus.byCode(goodsStatus.intValue()).getNote();
	}

}
