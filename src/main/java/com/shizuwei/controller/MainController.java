package com.shizuwei.controller;

import java.sql.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.shizuwei.controller.common.dto.PageDto;
import com.shizuwei.controller.dto.OrderInfoListRequestDto;
import com.shizuwei.dal.main.constants.GoodsStatus;
import com.shizuwei.dal.main.constants.OrderStatus;
import com.shizuwei.dal.main.po.Brand;
import com.shizuwei.dal.main.po.User;
import com.shizuwei.service.dto.OrderListResponseDto;
import com.shizuwei.service.dto.request.OrderListReqestDto;
import com.shizuwei.service.main.MainService;

@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	@Resource
	private MainService mainService;
	 
	@RequestMapping(value = "main/list.do", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView list(@ModelAttribute("infoRequst") OrderInfoListRequestDto infoRequst) {
	    logger.debug("infoRquest = {}", infoRequst);
	    if(infoRequst.getUserNumber() == null){
	    	
	    }
		
	    /**
	     * 构造查询参数
	     */
	   	OrderListReqestDto requestDto = new OrderListReqestDto();
	   	PageDto pageDto = new PageDto();
	   	pageDto.setPageNum(infoRequst.getPage());
	   	pageDto.setPageSize(6);
	   	requestDto.setPageDto(pageDto);
	   	if(infoRequst.getArriveStatus() != null){
	   		requestDto.setGoodsStatus(GoodsStatus.byCode(infoRequst.getArriveStatus().intValue()));
	   	}
	   	if(infoRequst.getPayStatus() != null){
	   		requestDto.setOrderStatus(OrderStatus.byCode(infoRequst.getPayStatus().intValue()));
	   	}
	   	
	   	if(StringUtils.isNoneBlank(infoRequst.getBegin()))
	   	{
	   		requestDto.setPayTimeStart(Date.valueOf(infoRequst.getBegin()));
	   	}
		if(StringUtils.isNoneBlank(infoRequst.getEnd())){
			requestDto.setPayTimeEnd(Date.valueOf(infoRequst.getEnd()));
		}
	   
		if (StringUtils.isNoneBlank(infoRequst.getUserNumber())) {
			User user = new User();
			user.setAccountNumber(infoRequst.getUserNumber());
			requestDto.setUser(user);
		}

		if (StringUtils.isNoneBlank(infoRequst.getBrand())) {
			Brand brand = new Brand();
			brand.setBrandName(infoRequst.getBrand());
			requestDto.setBrand(brand);
		}
		
	   	OrderListResponseDto responseDto = mainService.getOrderListInfo(requestDto);
	   	logger.debug("responserDto = {}", responseDto);
	   	
	   	/**
	   	 * 构造ModelAndView
	   	 */
	   	ModelAndView modelAndView = new ModelAndView("/main_list");
		modelAndView.addObject("list", responseDto.getDetailOrderList());
	   	modelAndView.addObject("info", responseDto.getStastics());
	   	logger.debug("page = {}", pageDto);
	   	modelAndView.addObject("page", pageDto);
	   	modelAndView.addObject("param", infoRequst);
	   	modelAndView.addObject("arriveStatus",GoodsStatus.values());
		modelAndView.addObject("payStatus",OrderStatus.values());
	   	
	   	logger.debug("model = {}", modelAndView.getModelMap());
        return modelAndView;
    }
}
