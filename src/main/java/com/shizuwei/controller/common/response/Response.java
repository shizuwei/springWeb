package com.shizuwei.controller.common.response;

import java.io.Serializable;

import com.shizuwei.controller.common.dto.PageDto;
import com.shizuwei.dal.common.page.PageBean;

public class Response implements Serializable {
	public static final int OK = 200, ERROR = -1, NOT_LGOIN = 400;

	private static final long serialVersionUID = -3458675766341550281L;
	private int status;
	private Object data;
	private String msg;
	private PageDto pageDto;

	public Response() {
		status = OK;
	}
	
	public static class ResponseBuilder {
		private Object data;
		private String msg;
		private PageDto pageDto;
		private int status;
		public ResponseBuilder data(Object data) {
			this.data = data;
			return this;
		}

		public ResponseBuilder error(String msg) {
			return error(msg, ERROR);
		}
		
		public ResponseBuilder error(String msg, int status) {
			this.msg = msg;
			this.status = status;
			return this;
		}

		public ResponseBuilder page(PageDto pageDto) {
			this.pageDto = pageDto;
			return this;
		}

		public ResponseBuilder setPage(PageBean<?> pageBean) {

			PageDto pageDto = new PageDto();
			pageDto.setCount(pageBean.getTotal());
			pageDto.setCurPageCount(pageBean.getSize());
			pageDto.setPageNum(pageBean.getPageNum());
			pageDto.setPageSize(pageBean.getPageSize());
			pageDto.setTotalPages(pageBean.getPages());
			this.pageDto = pageDto;
			return this;

		}

		public Response create() {
			Response response = new Response();
			response.setData(this.data);
			response.setMsg(msg);
			response.setPage(pageDto);
			response.setStatus(this.status);
			return response;
		}
	}

	public static ResponseBuilder builder() {
		return new ResponseBuilder();
	}

	public static Response create() {
		return new Response();
	}

	public static Response data(Object data) {
		return new Response(OK, data);
	}

	public static Response error(String msg) {
		return new Response(ERROR, msg);
	}

	public Response(int status, Object data) {
		super();
		this.status = status;
		this.data = data;
	}

	public Response(int status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public Response setStatus(int status) {
		this.status = status;
		return this;
	}

	public Object getData() {
		return data;
	}

	public Response setData(Object data) {
		this.data = data;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public Response setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	/**
	 * @return the pageDto
	 */

	public PageDto getPage() {

		return pageDto;

	}

	/**
	 * @param pageDto
	 *            the pageDto to set
	 */

	public Response setPage(PageDto pageDto) {

		this.pageDto = pageDto;
		return this;

	}

	public Response setPage(PageBean<?> pageBean) {

		PageDto pageDto = new PageDto();
		pageDto.setCount(pageBean.getTotal());
		pageDto.setCurPageCount(pageBean.getSize());
		pageDto.setPageNum(pageBean.getPageNum());
		pageDto.setPageSize(pageBean.getPageSize());
		pageDto.setTotalPages(pageBean.getPages());
		this.pageDto = pageDto;
		return this;

	}

}
