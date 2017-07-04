package com.shizuwei.service.main.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.shizuwei.dal.common.page.PageBean;
import com.shizuwei.dal.common.page.PaginationContext;
import com.shizuwei.dal.main.dao.ImgMapper;
import com.shizuwei.dal.main.po.Img;
import com.shizuwei.dal.main.po.ImgInfo;
import com.shizuwei.service.main.ImgService;

import net.coobird.thumbnailator.Thumbnails;

@Service("imgService")
public class ImgSeriviceImpl implements ImgService {
	private static final Logger logger = LoggerFactory.getLogger(ImgSeriviceImpl.class);
	@Resource
	private ImgMapper imgMapper;

	@Override
	public String saveImg(String folder, int width, int height, MultipartFile from) throws IOException {
		logger.debug("folder = {}, width = {}, height = {}", folder, width, height);
		String destFilePath = "D://test/";
		String fileName = from.getOriginalFilename();
		String pathname = destFilePath + folder + "/";
		destFilePath = pathname + fileName;
		logger.debug("pathname = {}", pathname);
		File path = new File(pathname);
		if (!path.exists()) {
			path.mkdir();
		}

		File dest = new File(destFilePath);
		if (dest.exists()) {
			return destFilePath;
		}

		if (width > 0 && height > 0) {
			Thumbnails.of(from.getInputStream()).sourceRegion(0, 0, width, height).size(300, 300).toFile(destFilePath);
		} else {
			Thumbnails.of(from.getInputStream()).size(300, 300).toFile(destFilePath);
		}
		Img img = new Img();
		img.setImgUrl("./img/" + folder + "/get.do?file=" + fileName);
		img.setFolder(folder);
		img.setImgName(fileName);
		imgMapper.insert(img);

		return destFilePath;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public PageBean<ImgInfo> list(String folder) {
		Img img = new Img();
		img.setFolder(folder);
		Integer pageNum = PaginationContext.getPageNum() == null ? 1 : PaginationContext.getPageNum();
		Integer pageSize = 20;
		PageHelper.startPage(pageNum, pageSize);
		List<ImgInfo> list = imgMapper.listDetail(img);

		PageBean<ImgInfo> imgInfos = new PageBean<>(list);
		return imgInfos;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Integer id) {
		this.imgMapper.delById(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(Img img) {

		this.imgMapper.update(img);
	}
}
