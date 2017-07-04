package com.shizuwei.service.main;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.shizuwei.dal.common.page.PageBean;
import com.shizuwei.dal.main.po.Img;
import com.shizuwei.dal.main.po.ImgInfo;

public interface ImgService {
	

	String saveImg(String folder, int width, int height, MultipartFile from) throws IOException;
	
	PageBean<ImgInfo> list(String folder);
	
	void delete(Integer id);

	void update(Img img);
	
	
	
	
}
