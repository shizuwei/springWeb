package com.shizuwei.controller.common;

import com.shizuwei.dal.main.po.User;

public class AuthInfo {
	private static ThreadLocal<User> curAuthInfo = new ThreadLocal<User>();

	public static User getUser() {
		return curAuthInfo.get();
	}

	public static void setUser(User user) {
		if (user != null) {
			curAuthInfo.set(user);
		}
	}

	public static void remove() {
		curAuthInfo.remove();
	}

}
