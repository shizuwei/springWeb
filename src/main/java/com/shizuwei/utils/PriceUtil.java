package com.shizuwei.utils;

import java.math.BigDecimal;

import com.google.common.base.Preconditions;
import com.shizuwei.dal.main.po.Goods;

/**
 * 价格计算
 * 
 * @author sszzw
 *
 */
public class PriceUtil {
	// (goodsCnt * goodsPrice* 汇率 + goodsFreight)
	public static BigDecimal calOrderTotalPrice(Goods goods, Integer goodsCnt, BigDecimal ratio) {
		Preconditions.checkNotNull(goods.getGoodsPrice());
		Preconditions.checkNotNull(goods.getGoodsFreight());
		Preconditions.checkNotNull(ratio);
		Preconditions.checkNotNull(goodsCnt);
		BigDecimal orderTotalPrice = goods.getGoodsPrice().multiply(new BigDecimal(goodsCnt)).multiply(ratio)
				.add(goods.getGoodsFreight());
		return orderTotalPrice;
	}
}
