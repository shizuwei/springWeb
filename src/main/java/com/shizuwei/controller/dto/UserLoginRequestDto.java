package com.shizuwei.controller.dto;

import lombok.Data;

@Data
public class UserLoginRequestDto{
	private String userCode;
	private String password;
}
