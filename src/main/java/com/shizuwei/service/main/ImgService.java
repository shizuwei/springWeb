package com.shizuwei.service.main;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shizuwei.dal.common.page.PageBean;
import com.shizuwei.dal.main.po.Img;
import com.shizuwei.dal.main.po.ImgInfo;
import com.shizuwei.service.dto.request.ImgListRequestDto;

public interface ImgService {

	String saveImg(String folder, int width, int height, MultipartFile from) throws IOException;

	PageBean<ImgInfo> list(ImgListRequestDto imgListRequsetDto);

	void delete(Integer id);

	void update(Img img);

	List<String> getFolders();

}
