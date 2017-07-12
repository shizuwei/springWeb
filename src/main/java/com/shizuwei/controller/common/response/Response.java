package com.shizuwei.controller.common.response;

import java.io.Serializable;

import com.shizuwei.controller.common.dto.PageDto;

public class Response implements Serializable {
	public static final int OK = 200, ERROR = -1;

	private static final long serialVersionUID = -3458675766341550281L;
	private int status;
	private Object data;
	private String msg;
	private PageDto pageDto;

	public Response() {
		status = OK;
	}

	public static Response done() {
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
}
