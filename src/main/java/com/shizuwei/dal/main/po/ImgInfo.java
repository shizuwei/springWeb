package com.shizuwei.dal.main.po;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ImgInfo extends Img {
	private List<GoodsInfo> goods;

	@Override
	public String toString() {
		return super.toString();
	}

	private Boolean checked = false;
}
