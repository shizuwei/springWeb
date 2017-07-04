package com.shizuwei.controller;

import java.io.FileInputStream;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shizuwei.controller.common.dto.PageDto;
import com.shizuwei.controller.common.response.Response;
import com.shizuwei.controller.common.response.ResponseBuilder;
import com.shizuwei.controller.common.response.ResponseStatus;
import com.shizuwei.dal.common.page.PageBean;
import com.shizuwei.dal.main.po.ImgInfo;
import com.shizuwei.service.main.ImgService;

@Controller
public class ImgController {
	private static final Logger logger = LoggerFactory.getLogger(ImgController.class);
	@Resource
	private ImgService imgService;

	@RequestMapping(value = "img/upload.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Response upload(@RequestParam(required = false) String folder, @RequestParam(required = false) Integer width,
			@RequestParam(required = false) Integer height, @RequestParam(required = false) MultipartFile file) {
		try {
			if (file != null) {
				logger.debug("upload file {}", file.getOriginalFilename());
				imgService.saveImg(folder, width == null ? 0 : width, height == null ? 0 : height, file);
			}
		} catch (Exception e) {
			logger.error("error file upload {} e = {}", file.getOriginalFilename(), e);
			return new ResponseBuilder().setStatus(ResponseStatus.BUSINESS_ERROR).build();
		}
		return new ResponseBuilder().build();
	}

	@RequestMapping(value = "img/list.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Response list(@RequestParam(required = false) String folder) {
		logger.debug("folder={}", folder);
		if (StringUtils.isBlank(folder)) {
			return new ResponseBuilder().setStatus(ResponseStatus.BUSINESS_ERROR).build();
		}
		PageBean<ImgInfo> list = this.imgService.list(folder);
		PageDto pageDto = new PageDto();
		pageDto.setCount(list.getTotal());
		pageDto.setCurPageCount(list.getSize());
		pageDto.setPageNum(list.getPageNum());
		pageDto.setPageSize(list.getPageSize());
		return new ResponseBuilder().setData(list.getList()).setPage(pageDto).build();
	}
	
	@RequestMapping(value = "img/edit.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Response edit(@RequestBody ImgInfo img) {
		logger.debug("img={}", img);
		this.imgService.update(img);
		return new ResponseBuilder().build();
	}
	
	@RequestMapping(value = "img/delete.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Response delete(@RequestBody ImgInfo img) {
		logger.debug("img={}", img);
		this.imgService.delete(img.getImgId());
		return new ResponseBuilder().build();
	}

	@RequestMapping(value = "img/{folder}/get.do", method = { RequestMethod.POST, RequestMethod.GET })
	public void getPic(@PathVariable String folder, @RequestParam(value = "file") String file,
			HttpServletResponse response) {
		logger.debug("getPic folder = {}, file = {}", folder, file);
		String destFilePath = "D://test/";
		destFilePath = destFilePath + folder + "/" + file;
		try {
			FileInputStream fInputStream = new FileInputStream(destFilePath);
			int len = fInputStream.available();
			byte[] buff = new byte[len];
			fInputStream.read(buff);
			fInputStream.close();
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(buff);
			response.setContentType("image/*");
			outputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
