package com.shizuwei.controller.dto;

import java.util.List;

import lombok.Data;

@Data
public class ImgEditDto {
	Integer id;
	String name;
	List<Integer> ids;
}
