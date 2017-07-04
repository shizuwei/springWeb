package com.shizuwei.dal.main.po;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderInfo extends Order {
	private User user;
	private List<OrderGoods> orderGoods;
	
}
