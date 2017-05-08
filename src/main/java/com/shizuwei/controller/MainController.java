package com.shizuwei.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.shizuwei.controller.common.dto.PageDto;
import com.shizuwei.controller.dto.OrderInfoListRequestDto;
import com.shizuwei.service.dto.OrderListResponseDto;
import com.shizuwei.service.dto.request.OrderListReqestDto;
import com.shizuwei.service.main.MainService;

@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	@Resource
	private MainService mainService;
	
   @RequestMapping("main/list.do")
    public ModelAndView list(OrderInfoListRequestDto infoRequst) {
	    logger.debug("infoRquest = {}", infoRequst);
		ModelAndView modelAndView = new ModelAndView("html/list");
	   	OrderListReqestDto requestDto = new OrderListReqestDto();
	   	PageDto pageDto = new PageDto();
	   	pageDto.setPageNum(1);
	   	pageDto.setPageSize(10);
	   	requestDto.setPageDto(pageDto);
	   	OrderListResponseDto responseDto = mainService.getOrderListInfo(requestDto);
	   	logger.debug("responserDto = {}", responseDto);
	   	modelAndView.addObject("list", responseDto.getDetailOrderList());
	   	Map<String, Object> info = Maps.newHashMap();
	   	info.put("arriveCnt", responseDto.getArrivedCount());
	   	info.put("notArriveCnt", responseDto.getNotArrivedCount());
	   	info.put("payCnt", responseDto.getPayedCount());
	   	info.put("payMoney", responseDto.getPayedMoneySum() / 100.0);
	   	info.put("sendCnt", responseDto.getSendCount());
	   	info.put("unPayedCnt", responseDto.getUnPayedCount());
	   	info.put("unPayedMoney", responseDto.getUnPayedMoneySum() / 100.0);
	   	info.put("totalCnt", pageDto.getCount());
	   	modelAndView.addObject("info", info);
	   	logger.debug("model = {}", modelAndView.getModelMap());
        return modelAndView;
    }
}
