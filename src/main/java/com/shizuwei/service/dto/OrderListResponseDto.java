package com.shizuwei.service.dto;

import java.util.List;

import com.shizuwei.dal.main.po.DetailOrder;
import com.shizuwei.dal.main.po.OrderStastics;

import lombok.Data;

@Data
public class OrderListResponseDto {
	/**
	 * 订单列表
	 */
	private List<DetailOrder> detailOrderList;
	
	
	private OrderStastics stastics;
	
}
