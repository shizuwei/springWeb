package com.shizuwei.dal.main.dao;

import org.apache.ibatis.annotations.Param;

import com.shizuwei.dal.common.dao.BaseMapper;
import com.shizuwei.dal.main.po.OrderGoods;

public interface OrderGoodsMapper extends BaseMapper<Integer, OrderGoods> {
	OrderGoods getByParams(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);
}
