package com.shizuwei.service.dto.request;

import lombok.Data;

@Data
public class OrderListReqestDto {
	private Integer userId;
	private Integer orderStatus;
	private Integer goodsStatus;
	private String folder;
	private String description;
}
