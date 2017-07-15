package com.shizuwei.service.dto.response;

import java.util.List;

import com.shizuwei.dal.main.po.Order;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderInfoResponseDto extends Order {
	List<OrderGoodsResponseDto> orderGoods;
}
