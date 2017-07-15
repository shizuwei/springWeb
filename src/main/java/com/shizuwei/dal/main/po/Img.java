package com.shizuwei.dal.main.po;

import java.sql.Date;

import lombok.Data;

@Data
public class Img {
	private Integer imgId;
	private String imgName;
	private String imgUrl;
	private Date updateTime;
	private String folder;
	private String fileName;
}
