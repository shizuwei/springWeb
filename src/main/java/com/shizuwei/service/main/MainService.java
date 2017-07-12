package com.shizuwei.service.main;

import com.shizuwei.controller.dto.OrderInfoListRequestDto;
import com.shizuwei.dal.common.page.PageBean;
import com.shizuwei.dal.main.po.OrderInfo;

public interface MainService {
	 
	PageBean<OrderInfo> listOrder(OrderInfoListRequestDto infoRequst);
	
	
}
