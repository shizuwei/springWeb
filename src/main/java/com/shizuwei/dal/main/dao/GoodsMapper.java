package com.shizuwei.dal.main.dao;

import com.shizuwei.dal.common.dao.BaseMapper;
import com.shizuwei.dal.main.po.Goods;

public interface GoodsMapper extends BaseMapper<Integer, Goods> {

	Integer getGoodsCountOfImg(Integer imgId);

}
