package com.shizuwei.dal.main.constants;

public enum UserLevel {
	ADMIN(1), NORMAL(0);

	private Integer code;

	public Integer getCode() {
		return code;
	}

	private UserLevel(int code) {
		this.code = code;
	}
}
