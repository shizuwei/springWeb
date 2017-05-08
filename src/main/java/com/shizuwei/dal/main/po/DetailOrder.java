package com.shizuwei.dal.main.po;

import lombok.Data;

@Data
public class DetailOrder {
	
	private Order order = new Order();
	
	private Goods goods = new Goods();
	
	private Brand brand = new Brand();
	
	private User user = new User();
}
