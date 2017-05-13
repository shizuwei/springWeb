package com.shizuwei.controller.dto;


import lombok.Data;

@Data
public class OrderInfoListRequestDto {
	private String userNumber; ///http://localhost:8080/SpringWeb/main/list.do?userNumber=&brand=&payStatus=1&begin=&end=
	private String begin;
	private String end;
	private String brand;
	private Integer payStatus;
	private Integer arriveStatus;
	private Integer page;
}
