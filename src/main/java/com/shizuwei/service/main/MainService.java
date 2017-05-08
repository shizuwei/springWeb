package com.shizuwei.service.main;

import com.shizuwei.service.dto.OrderListResponseDto;
import com.shizuwei.service.dto.request.OrderListReqestDto;

public interface MainService {
	OrderListResponseDto getOrderListInfo(OrderListReqestDto request);
}
