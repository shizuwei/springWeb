package com.shizuwei.service.main.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.shizuwei.controller.common.constants.WebConstantsUtil;
import com.shizuwei.dal.common.page.PageBean;
import com.shizuwei.dal.common.page.PaginationContext;
import com.shizuwei.dal.main.dao.GoodsMapper;
import com.shizuwei.dal.main.dao.ImgMapper;
import com.shizuwei.dal.main.dao.OrderGoodsMapper;
import com.shizuwei.dal.main.dao.OrderMapper;
import com.shizuwei.dal.main.po.Img;
import com.shizuwei.dal.main.po.ImgInfo;
import com.shizuwei.service.dto.request.ImgListRequestDto;
import com.shizuwei.service.main.ImgService;

import net.coobird.thumbnailator.Thumbnails;

@Service("imgService")
public class ImgSeriviceImpl implements ImgService {
	private static final Logger logger = LoggerFactory.getLogger(ImgSeriviceImpl.class);
	@Resource
	private ImgMapper imgMapper;
	@Resource
	private GoodsMapper goodsMapper;
	@Resource
	private OrderGoodsMapper orderGoodsMapper;
	@Resource
	private OrderMapper orderMapper;

	@Override
	public List<String> getFolders() {
		List<String> folders = Lists.newArrayList();
		File path = new File(WebConstantsUtil.getDestFilePath());
		Preconditions.checkArgument(path.exists());
		Preconditions.checkArgument(path.isDirectory());
		File[] tempList = path.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isDirectory()) {
				folders.add(tempList[i].getName());
			}
		}
		return folders;
	}

	@Override
	public String saveImg(String folder, int width, int height, MultipartFile from) throws IOException {
		logger.debug("folder = {}, width = {}, height = {}", folder, width, height);

		String fileName = from.getOriginalFilename();
		String pathname = WebConstantsUtil.getDestFilePath() + folder + "/";
		String destFilePath = pathname + fileName;
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
		img.setFileName(fileName);
		imgMapper.insert(img);

		return destFilePath;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public PageBean<ImgInfo> list(ImgListRequestDto imgListRequsetDto) {
		Integer pageNum = PaginationContext.getPageNum() == null ? 1 : PaginationContext.getPageNum();
		Integer pageSize = PaginationContext.getPageSize();
		logger.debug("pageNum={},pageSize={}", pageNum, pageSize);
		PageHelper.startPage(pageNum, pageSize);
		List<ImgInfo> list = imgMapper.listDetail(imgListRequsetDto);
		PageBean<ImgInfo> imgInfos = new PageBean<>(list);
		return imgInfos;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Integer id) {
		Preconditions.checkNotNull(id);
		// 查看是否被goods引用
		Integer countOfImg = this.goodsMapper.getGoodsCountOfImg(id);
		Preconditions.checkArgument(countOfImg != null && countOfImg.equals(0), "图片已经被引用，不能删除！");
		this.imgMapper.delById(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(Img img) {

		this.imgMapper.update(img);
	}

	@Override
	public List<Integer> delete(List<Integer> ids) {
		List<Integer> delIds = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(ids)) {
			for (Integer id : ids) {
				Integer countOfImg = this.goodsMapper.getGoodsCountOfImg(id);
				if (countOfImg != null && countOfImg.equals(0)) {
					delIds.add(id);
					Img img = this.imgMapper.getById(id);
					if (img != null) {
						this.imgMapper.delById(id);
						File path = new File(
								WebConstantsUtil.getDestFilePath() + img.getFolder() + "/" + img.getFileName());
						if (path.exists()) {
							path.delete();
						}
					}

				}
			}
		}
		return delIds;
	}
}
