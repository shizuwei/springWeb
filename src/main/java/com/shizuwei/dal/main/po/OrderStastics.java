package com.shizuwei.dal.main.po;

import lombok.ToString;

@ToString
public class OrderStastics {

	public Integer getUnPayedCount() {
		return unPayedCount;
	}
	public void setUnPayedCount(Integer unPayedCount) {
		this.unPayedCount = unPayedCount == null ? 0 : unPayedCount;
	}
	public Long getUnPayedMoneySum() {
		return unPayedMoneySum;
	}
	public void setUnPayedMoneySum(Long unPayedMoneySum) {
		this.unPayedMoneySum = unPayedMoneySum == null ? 0 : unPayedMoneySum;
	}
	public Integer getPayedCount() {
		return payedCount;
	}
	public void setPayedCount(Integer payedCount) {
		this.payedCount = payedCount == null ? 0 : payedCount;
	}
	public Long getPayedMoneySum() {
		return payedMoneySum;
	}
	public void setPayedMoneySum(Long payedMoneySum) {
		this.payedMoneySum = payedMoneySum == null ? 0 : payedMoneySum;
	}
	public Integer getNotArrivedCount() {
		return notArrivedCount;
	}
	public void setNotArrivedCount(Integer notArrivedCount) {
		this.notArrivedCount = notArrivedCount == null ? 0 : notArrivedCount;
	}
	public Integer getArrivedCount() {
		return arrivedCount;
	}
	public void setArrivedCount(Integer arrivedCount) {
		this.arrivedCount = arrivedCount == null ? 0 : arrivedCount;
	}
	public Integer getSendCount() {
		return sendCount;
	}
	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount == null ? 0 : sendCount;
	}
	
	/**
	 * 未经付款数目
	 */
	private Integer unPayedCount;
	
	/**
	 * 未付款总金额
	 */
	private Long unPayedMoneySum;
	/**
	 * 已经付款总数目
	 */
	private Integer payedCount;
	/**
	 * 已经付款金额
	 */
	private Long payedMoneySum;
	/**
	 * 未经到货数目
	 */
	private Integer notArrivedCount;
	
	/**
	 * 已经到货数目
	 */
	private Integer arrivedCount;
	/**
	 * 已经发货数目
	 */
	private Integer sendCount;
	
}
