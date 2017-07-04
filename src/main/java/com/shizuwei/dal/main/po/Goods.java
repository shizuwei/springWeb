package com.shizuwei.dal.main.po;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class Goods {
	private Integer goodsId;
	private String goodsName;
	private BigDecimal goodsPrice;
	private Integer imgId;
	private String goodsNumber;
	private String size;
	private Date updateTime;
	private String brandName;
	private Long goodsFreight;
}
