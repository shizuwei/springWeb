package com.shizuwei.controller.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class OrderInfoListRequestDto {
	private String userNumber;
	private Date begin;
	private Date end;
	private String brand;
	private String payStatus;
	private String arriveStatus;
}
