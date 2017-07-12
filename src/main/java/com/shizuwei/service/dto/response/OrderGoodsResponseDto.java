package com.shizuwei.service.dto.response;

import com.shizuwei.dal.main.po.OrderGoods;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderGoodsResponseDto extends OrderGoods{
	private String imgURL;
	
}
