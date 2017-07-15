package com.shizuwei.dal.main.po;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class User {
	private Integer userId;
	private String wxName;
	private String accountNumber;
	private String password;
	private Integer level;
	// 汇率
	private BigDecimal ratio;
	
	public User() {
		
	}

	public User(String wxName, String accountNumber) {
		this.wxName = wxName;
		this.accountNumber = accountNumber;
	}

	public User(Integer id, String wxName, String accountNumber) {
		this.userId = id;
		this.wxName = wxName;
		this.accountNumber = accountNumber;
	}
}
