package com.shizuwei.dal.main.po;

import lombok.Data;

@Data
public class User {
	private Integer id;
	private String wxName;
	private String accountNumber;
	
	public User() {
		
	}

	public User(String wxName, String accountNumber) {
		this.wxName = wxName;
		this.accountNumber = accountNumber;
	}

	public User(Integer id, String wxName, String accountNumber) {
		this.id = id;
		this.wxName = wxName;
		this.accountNumber = accountNumber;
	}
}
