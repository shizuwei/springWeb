package com.shizuwei.service.main.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shizuwei.dal.main.dao.GoodsMapper;
import com.shizuwei.dal.main.dao.ImgMapper;
import com.shizuwei.dal.main.dao.OrderGoodsMapper;
import com.shizuwei.dal.main.dao.OrderMapper;
import com.shizuwei.dal.main.dao.UserMapper;
import com.shizuwei.service.main.ExcelExportService;

@Service("excelExportService")
public class ExcelExportServiceImpl implements ExcelExportService {
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private GoodsMapper goodsMapper;
	@Resource
	private ImgMapper imgMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private OrderGoodsMapper orderGoodsMapper;

	@Override
	public void exportExcel() {
		// TODO Auto-generated method stub

	}

}
