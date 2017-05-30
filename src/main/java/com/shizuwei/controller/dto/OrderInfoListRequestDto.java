package com.shizuwei.controller.dto;


import lombok.Data;

@Data
public class OrderInfoListRequestDto {
	private String userNumber;
	private Integer payStatus;
	private Integer arriveStatus;
}
