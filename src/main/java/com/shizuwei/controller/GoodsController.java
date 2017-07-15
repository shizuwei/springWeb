package com.shizuwei.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.shizuwei.controller.common.response.Response;
import com.shizuwei.dal.main.constants.GoodsStatus;
import com.shizuwei.dal.main.constants.OrderStatus;
import com.shizuwei.dal.main.po.Goods;
import com.shizuwei.dal.main.po.GoodsInfo;
import com.shizuwei.service.main.GoodsService;

@Controller
public class GoodsController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	@Resource
	private GoodsService goodsService;

	@RequestMapping(value = "goods/importGoods.json", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Map<String, Object> importGoods(@RequestParam(value = "path") String path) {
		logger.debug("path = {}", path);
		Map<String, Object> response = Maps.newHashMap();
		path = goodsService.importGoods(path);
		response.put("path", path);
		return response;
	}

	@RequestMapping(value = "goods/add.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Response addGoods(@RequestBody Goods goods) {
		logger.debug("add goods = {}", goods);
		Integer id = this.goodsService.insert(goods);
		Map<String, Object> data = Maps.newHashMap();
		data.put("id", id);
		return Response.builder().data(data).create();
	}

	@RequestMapping(value = "goods/editSize.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Response editSize(@RequestBody Goods goods) {
		logger.debug("add goods = {}", goods);
		this.goodsService.edit(goods);
		return Response.builder().create();
	}

	@RequestMapping(value = "goods/delete.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Response delete(@RequestBody GoodsInfo goods) {
		try {
			logger.debug("goods = {}", goods);
			this.goodsService.delete(goods.getGoodsId());
			return Response.data(true);
		} catch (Exception e) {
			return Response.error(e.getMessage());
		}
	}

	@RequestMapping(value = "goods/status.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Response goodsStatus() {
		logger.debug("{}", Arrays.asList(GoodsStatus.values()));
		return Response.data(Arrays.asList(GoodsStatus.values()));
	}

	@RequestMapping(value = "order/status.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Response orderStatus() {
		ArrayList<OrderStatus> arrayList = new ArrayList<OrderStatus>(Arrays.asList(OrderStatus.values()));
		return Response.data(arrayList);
	}

	@RequestMapping(value = "goods/getPic.do", method = { RequestMethod.POST, RequestMethod.GET })
	public void getPic(@RequestParam(value = "goodsId") Integer goodsId, HttpServletResponse response) {
		logger.debug("goodsId = {}", goodsId);
		byte[] buff = null;// goodsService.getImgBuff(goodsId);
		try {
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(buff);
			response.setContentType("image/*");
			outputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
