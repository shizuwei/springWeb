package com.shizuwei.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.shizuwei.controller.dto.OrderInfoListRequestDto;
import com.shizuwei.dal.common.page.PageBean;
import com.shizuwei.dal.main.constants.GoodsStatus;
import com.shizuwei.dal.main.constants.OrderStatus;
import com.shizuwei.dal.main.po.Order;
import com.shizuwei.service.main.MainService;

@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	@Resource
	private MainService mainService;

	@RequestMapping(value = "main/list.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String list(@ModelAttribute("param") OrderInfoListRequestDto infoRequst, Model model) {
		logger.debug("infoRquest = {}", infoRequst);
		PageBean<Order> orders = mainService.listOrder(infoRequst);
		logger.debug("orders = {}", orders);
		model.addAttribute("orders", orders.getList());
		model.addAttribute("page", orders);
		model.addAttribute("arriveStatus", GoodsStatus.values());
		model.addAttribute("payStatus", OrderStatus.values());
		return "list";
	}
	
	@RequestMapping(value = "main/edit.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String edit(@ModelAttribute("param") OrderInfoListRequestDto infoRequst, Model model) {
		logger.debug("infoRquest = {}", infoRequst);
		PageBean<Order> orders = mainService.listOrder(infoRequst);
		logger.debug("orders = {}", orders);
		model.addAttribute("orders", orders.getList());
		model.addAttribute("page", orders);
		model.addAttribute("arriveStatus", GoodsStatus.values());
		model.addAttribute("payStatus", OrderStatus.values());
		return "edit";
	}
}
