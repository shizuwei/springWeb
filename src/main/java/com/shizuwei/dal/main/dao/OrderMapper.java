package com.shizuwei.dal.main.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shizuwei.dal.common.dao.BaseMapper;
import com.shizuwei.dal.main.po.Order;
import com.shizuwei.service.dto.request.OrderListReqestDto;

public interface OrderMapper extends BaseMapper<Integer, Order> {

	List<Order> listWithGoods(OrderListReqestDto order);

	List<Order> listWithGoodsByIds(List<Integer> ids);

	List<Integer> listOrderIds(OrderListReqestDto order);

	List<Order> getByFolder(@Param("userId") Integer userId, @Param("folder") String folder);

}
