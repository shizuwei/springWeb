package com.shizuwei.dal.main.constants;

import java.util.Map;

import com.google.common.collect.Maps;

public enum GoodsStatus {
	
	NOT_ARRIVED(0, "未到货"),
	ARRIVED(1,"已到货"),
	SEND(2,"已发货")
	;
	
	private int code;
	private String note;
	private static Map<Integer, GoodsStatus> map = Maps.newHashMap();
	private GoodsStatus(int code, String note){
		this.code = code;
		this.note = note;
	}
	
	static {
        for (GoodsStatus at : GoodsStatus.values()) {
            map.put(at.code, at);
        }
    }
	 
	public String getNote() {
		return this.note;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public static GoodsStatus byCode(int code){
		return map.get(code);
	}
}
