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
		String userCode = (String) httpServletRequest.getSession().getAttribute("userCode");
		String URI = httpServletRequest.getRequestURI();
		if (userCode == null && URI.indexOf(loginUrl) == -1) {
			String contextPath = httpServletRequest.getContextPath();
			String location = contextPath + "/" + loginUrl + "?"
					+ StringUtils.defaultString(httpServletRequest.getQueryString());
			logger.debug("redirect to {}", location);
			httpServletResponse.sendRedirect(location);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}

}
