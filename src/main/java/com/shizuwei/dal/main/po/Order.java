package com.shizuwei.dal.main.po;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class Order {
	/**
	 * key
	 */
	private Integer orderId;
	/**
	 * 用户
	 */
	private Integer userId;

	/**
	 * 付钱时间
	 */
	private Date orderPayTime;
	/**
	 * 下单时间
	 */
	private Date orderTime;
	
	/**
	 * 总价 单位： 分
	 */
	private BigDecimal orderTotalPrice;
	
	/**
	 * 描述
	 */
	private String orderDescription;
	
	private Date orderUpdateTime;
	
	private String orderFolder;
}
