package com.shizuwei.dal.common.page;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;;

public class PageFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		PaginationContext.setPageNum(getPageNum(httpRequest));
		PaginationContext.setPageSize(getPageSize(httpRequest));
		
		chain.doFilter(request, response);
	}

	/**
	 * 获得pager.offset参数的值
	 * 
	 * @param request
	 * @return
	 */
	protected int getPageNum(HttpServletRequest request) {
		int pageNum = 1;
		try {
			String pageNums = request.getParameter("pageNum");
			if (pageNums != null && StringUtils.isNumeric(pageNums)) {
				pageNum = Integer.parseInt(pageNums);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return pageNum;
	}

	/**
	 * 设置默认每页大小
	 * 
	 * @return
	 */
	protected int getPageSize(HttpServletRequest request) {
		int pageSize = 10; // 默认每页10条记录
		try {
			String pageSizes = request.getParameter("pageSize");
			if (pageSizes != null && StringUtils.isNumeric(pageSizes)) {
				pageSize = Integer.parseInt(pageSizes);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return pageSize;
	}

	@Override
	public void destroy() {

	}

}
