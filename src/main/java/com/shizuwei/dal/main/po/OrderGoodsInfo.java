package com.shizuwei.dal.main.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderGoodsInfo extends OrderGoods{
	private Goods goods;
}
