package com.shizuwei.dal.main.constants;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Maps;

@JsonFormat(shape= JsonFormat.Shape.OBJECT)
public enum OrderStatus {
	
	UNPAY(0, "未付款"),
	PAYED(1,"已付款")
	;
	
	private int code;
	private String note;
	private static Map<Integer, OrderStatus> map = Maps.newHashMap();
	private OrderStatus(int code, String note){
		this.code = code;
		this.note = note;
	}
	
	static {
        for (OrderStatus at : OrderStatus.values()) {
            map.put(at.code, at);
        }
    }
	 
	public String getNote() {
		return this.note;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public static OrderStatus byCode(int code){
		return map.get(code);
	}
}
