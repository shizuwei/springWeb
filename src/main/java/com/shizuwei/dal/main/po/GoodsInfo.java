package com.shizuwei.dal.main.po;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GoodsInfo extends Goods {
	private List<OrderInfo> orders;
	private String imgURL;
	@Override
	public String toString(){
		return super.toString() + this.orders.toString();		
	}
}
