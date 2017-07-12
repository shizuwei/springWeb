package com.shizuwei.service.dto.request;

import lombok.Data;

@Data
public class ImgListRequestDto {
	private String folder;
	private Integer userId;
	private Integer goodsStatus;
	private Integer orderStatus;
}
