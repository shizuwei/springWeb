package com.shizuwei.dal.main.po;

import java.sql.Date;

import com.shizuwei.dal.main.constants.GoodsStatus;

import lombok.Data;

@Data
public class Goods {
	private Integer id;
	private String goodsName;
	private Long price;
	private Integer brandId;
	private String imgURL;
	private String goodNumber;
	private String size;
	private Date updateTime;
}
