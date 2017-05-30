package com.shizuwei.service.main.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.shizuwei.dal.main.dao.GoodsMapper;
import com.shizuwei.dal.main.po.Goods;
import com.shizuwei.service.main.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {
	@Resource
	private GoodsMapper goodsMapper;

	private static final String basePath = "D:\\PICS\\";

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
			goods.setImgURL(files[i].getAbsolutePath());
			goods.setGoodsName(files[i].getAbsolutePath());
			goods.setBrandId(0);
			goods.setGoodNumber("");
			goods.setPrice(0L);
			goods.setSize("");
			goodsMapper.insert(goods);
		}

		return path;
	}

	@Override
	public byte[] getImgBuff(Integer goodsId) {
		Goods goods = goodsMapper.getById(goodsId);
		Preconditions.checkNotNull(goods);
		try{
			
			FileInputStream fInputStream = new FileInputStream(goods.getImgURL());
			int len = fInputStream.available();
			byte[] buff = new byte[len];
			fInputStream.read(buff);
			fInputStream.close();
			return buff;
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	

}
