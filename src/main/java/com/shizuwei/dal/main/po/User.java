package com.shizuwei.dal.main.po;

import lombok.Data;

@Data
public class User {
	private Integer userId;
	private String wxName;
	private String accountNumber;
	private String password;
	private Integer level;
	
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
