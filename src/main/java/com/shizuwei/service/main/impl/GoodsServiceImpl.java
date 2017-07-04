package com.shizuwei.service.main.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.shizuwei.dal.main.dao.GoodsMapper;
import com.shizuwei.dal.main.po.Goods;
import com.shizuwei.service.main.GoodsService;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class GoodsServiceImpl implements GoodsService {
	@Resource
	private GoodsMapper goodsMapper;

	private static final String basePath = "D:\\PICS\\";

	@Override
	public Integer insert(Goods goods) {
		this.goodsMapper.insert(goods);
		return goods.getGoodsId();
	}

	@Override
	public String importGoods(String path) {
		String realPath = basePath + path;
		File file = new File(realPath);
		Preconditions.checkArgument(file.exists() && file.isDirectory());
		File[] files = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(".png") || name.endsWith("jpg")) {
					return true;
				}
				return false;
			}
		});

		for (int i = 0; i < files.length; i++) {
			Goods goods = new Goods();
			goods.setGoodsName(files[i].getAbsolutePath());
			goods.setGoodsNumber("NONE");
			goods.setGoodsPrice(BigDecimal.ZERO);
			goods.setSize("NONE");
			goodsMapper.insert(goods);
		}

		return path;
	}

	@Override
	public void delete(Integer id) {
		Preconditions.checkNotNull(id);
		this.goodsMapper.delById(id);
	}

	@Override
	public void edit(Goods goods) {
		// TODO Auto-generated method stub
		Preconditions.checkNotNull(goods.getGoodsId());
		this.goodsMapper.update(goods);
		
	}

}
