package com.shizuwei.service.dto.response;

import com.shizuwei.dal.main.po.OrderGoodsInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderGoodsResponseDto extends OrderGoodsInfo{
	private String imgURL;
	private String imgName;
}
