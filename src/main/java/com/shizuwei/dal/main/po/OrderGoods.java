package com.shizuwei.dal.main.po;

import lombok.Data;

@Data
public class OrderGoods {
	private Integer orderGoodsId;
	private Integer orderId;
	private Integer goodsId;
	private Integer goodsCnt;
	private Long goodsPrice;
	private Integer goodsFeight;
	private Long totalPrice;
	private Goods goods;
	private String description;
}
