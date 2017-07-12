package com.shizuwei.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.shizuwei.controller.common.AuthInfo;
import com.shizuwei.dal.main.po.User;

public class LoginFilter implements Filter {

	private String loginUrl;
	private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.loginUrl = filterConfig.getInitParameter("loginUrl");
		Preconditions.checkArgument(StringUtils.isNoneBlank(this.loginUrl));
		logger.debug("loginUrl = {}", this.loginUrl);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		User user = (User) httpServletRequest.getSession().getAttribute("user");
		String URI = httpServletRequest.getRequestURI();
		if (user == null && URI.indexOf(loginUrl) == -1 && URI.indexOf("login") == -1 && URI.indexOf("user/get.do") == -1) {
			// 没有登录，且不是登录页面
			StringBuilder contextPath = new StringBuilder(httpServletRequest.getContextPath());
			String loginPage = contextPath.append("/").append(loginUrl).append("?")
					.append(StringUtils.defaultString(httpServletRequest.getQueryString())).toString();
			logger.debug("redirect to {}", loginPage);
			// 跳转到登录页面
			httpServletResponse.sendRedirect(loginPage);
		} else {
			// 设置到ThreadLocal
			AuthInfo.setUser(user);
			chain.doFilter(request, response);
			// 及时删除
			AuthInfo.remove();
		}
	}

	@Override
	public void destroy() {

	}

}
