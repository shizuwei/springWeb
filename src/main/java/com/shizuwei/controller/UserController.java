package com.shizuwei.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shizuwei.controller.common.response.Response;
import com.shizuwei.controller.common.response.ResponseBuilder;
import com.shizuwei.controller.dto.UserLoginRequestDto;
import com.shizuwei.dal.main.dao.UserMapper;
import com.shizuwei.dal.main.po.User;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	@Resource
	private UserMapper userMapper;

	@RequestMapping(value = "user/login.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Response login(@RequestBody UserLoginRequestDto request, HttpServletRequest httpServletRequest) {
		logger.debug("request = {}", request);
		httpServletRequest.getSession().setAttribute("userCode", request.getUserCode());
		return new ResponseBuilder().build();
	}

	@RequestMapping(value = "user/list.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Response login(HttpServletRequest httpServletRequest) {
		String name = httpServletRequest.getParameter("name");
		User user = new User();
		user.setAccountNumber(name);
		List<User> userList = userMapper.list(user);
		return new ResponseBuilder().setData(userList).build();
	}

}
