package com.shizuwei.service.dto.request;

import java.sql.Date;

import com.shizuwei.controller.common.dto.PageDto;
import com.shizuwei.dal.main.constants.GoodsStatus;
import com.shizuwei.dal.main.constants.OrderStatus;
import com.shizuwei.dal.main.po.Brand;
import com.shizuwei.dal.main.po.User;

import lombok.Data;

@Data
public class OrderListReqestDto {
	private User user;
	private Brand brand; 
	private OrderStatus orderStatus;
	private GoodsStatus goodsStatus;
	private Date payTimeStart;
	private Date payTimeEnd;
	private PageDto pageDto;
	
}
