package com.shizuwei.controller.dto;

import lombok.Data;

@Data
public class OrderAddRequest {
	private Integer userId;
	private Integer goodsCnt;
	private Integer goodsStatus;
	private Integer orderStatus;
}
