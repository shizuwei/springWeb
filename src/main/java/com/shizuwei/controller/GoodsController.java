package com.shizuwei.controller;

import java.io.OutputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.shizuwei.service.main.GoodsService;

@Controller
public class GoodsController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	@Resource
	private GoodsService goodsService;

	@RequestMapping(value = "goods/importGoods.json", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Map<String, Object> importGoods(@RequestParam(value = "path") String path, Model model) {
		logger.debug("path = {}", path);
		
		Map<String, Object> response = Maps.newHashMap();
		path = goodsService.importGoods(path);
		response.put("path", path);
		return response;
	}
	
	@RequestMapping(value = "goods/getPic.do", method = { RequestMethod.POST, RequestMethod.GET })
	public void getPic(@RequestParam(value = "goodsId")  Integer goodsId, HttpServletResponse response) {
		logger.debug("goodsId = {}", goodsId);
		byte[] buff = goodsService.getImgBuff(goodsId);
		try{
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(buff);
			response.setContentType("image/*");
			outputStream.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
