package com.shizuwei.service.dto.request;

import lombok.Data;

@Data
public class OrderAddRequest {
	private Integer userId;
	private Integer goodsId;
	private Integer goodsCnt;
	private Integer goodsStatus;
	private Integer orderStatus;
	private Integer orderGoodsId;
}
