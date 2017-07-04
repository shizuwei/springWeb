package com.shizuwei.dal.main.dao;

import java.util.List;

import com.shizuwei.dal.common.dao.BaseMapper;
import com.shizuwei.dal.main.po.Img;
import com.shizuwei.dal.main.po.ImgInfo;

public interface ImgMapper extends BaseMapper<Integer, Img>{
	
	List<ImgInfo> listDetail(Img img);
}
