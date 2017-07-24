package com.shizuwei.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Preconditions;
import com.shizuwei.controller.common.AuthInfo;
import com.shizuwei.controller.common.response.Response;
import com.shizuwei.controller.dto.UserLoginRequestDto;
import com.shizuwei.dal.main.constants.UserLevel;
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
		String userNumber = request.getUserCode();
		String password = request.getPassword();
		try {
			Preconditions.checkNotNull(userNumber, "未填用户名");
			Preconditions.checkNotNull(password, "未填密码");
			// 查询user数据库
			User user = this.userMapper.getByNumber(userNumber);
			Preconditions.checkNotNull(user, "用户名不存在");
			// 管理员
			if (user.getLevel().equals(UserLevel.ADMIN.getCode())) {
				if (StringUtils.isNotBlank(user.getPassword())) {
					Preconditions.checkArgument(user.getPassword().equals(password), "密码错误！");
				}
				// 更新登录时间
				user.setLastLogin(new Date());
				this.userMapper.update(user);
			}
			// 设置用户到session变量
			httpServletRequest.getSession().setAttribute("user", user);
			return Response.create();
		} catch (Exception ex) {
			return Response.error(ex.getMessage());
		}
	}

	@RequestMapping(value = "user/list.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Response list(HttpServletRequest httpServletRequest) {
		try {
			String name = httpServletRequest.getParameter("name");
			User user = new User();
			user.setAccountNumber(name);
			List<User> userList = userMapper.list(user);
			// 把密码删除
			userList.forEach(u -> u.setPassword(null));
			return Response.data(userList);
		} catch (Exception ex) {
			return Response.builder().error(ex.getMessage()).create();
		}
	}

	@RequestMapping(value = "user/get.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Response get(HttpServletRequest httpServletRequest) {
		String name = httpServletRequest.getParameter("name");
		User user = userMapper.getByNumber(name);
		logger.debug("user = {} number={}", user, name);
		if (user != null) {
			user.setLastLogin(null);
			user.setPassword(null);
			return Response.data(user);
		}
		return Response.error("用户不存在！");
	}

	@RequestMapping(value = "user/logout.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Response logout(HttpServletRequest httpServletRequest) {
		try {
			httpServletRequest.getSession().removeAttribute("user");
			return Response.create();
		} catch (Exception ex) {
			return Response.builder().error(ex.getMessage()).create();
		}

	}

	@RequestMapping(value = "user/cur.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Response getCurUser(HttpServletRequest httpServletRequest) {
		try {
			return Response.data(AuthInfo.getUser());
		} catch (Exception ex) {
			return Response.builder().error(ex.getMessage()).create();
		}
	}

}
