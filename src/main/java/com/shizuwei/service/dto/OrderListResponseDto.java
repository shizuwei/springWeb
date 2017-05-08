package com.shizuwei.service.dto;

import java.util.List;

import com.shizuwei.dal.main.constants.GoodsStatus;
import com.shizuwei.dal.main.constants.OrderStatus;
import com.shizuwei.dal.main.po.DetailOrder;
import com.shizuwei.dal.main.po.Order;

import lombok.Data;

@Data
public class OrderListResponseDto {
	/**
	 * 订单列表
	 */
	private List<DetailOrder> detailOrderList;
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
	
	
	int payCnt = 0;
	int unPayCnt = 0;
	long payMoney = 0;
	long unPayMoney = 0;
	int arriveCnt = 0;
	int notArriveCnt = 0;
	int sendCnt = 0;
	
	/**
	 * 统计信息
	 * @param detailOrder
	 */
	public void calcStatstic(DetailOrder detailOrder){
		
		Order order = detailOrder.getOrder();

		if(order.getOrderStatus().intValue() == OrderStatus.PAYED.getCode()){
			payCnt++;
			payMoney += order.getTotalPrice().longValue();
		}else if(order.getOrderStatus().intValue() == OrderStatus.UNPAY.getCode()){
			unPayCnt++;
			unPayMoney += order.getTotalPrice().longValue();
		}
		
		if(order.getGoodsStatus().intValue() == GoodsStatus.NOT_ARRIVED.getCode()){
			notArriveCnt++;
		}else if(order.getGoodsStatus().intValue() == GoodsStatus.ARRIVED.getCode()){
			arriveCnt++;
		}else if(order.getGoodsStatus().intValue() == GoodsStatus.SEND.getCode()){
			sendCnt++;
		}
		
		this.setPayedCount(payCnt);
		this.setUnPayedCount(unPayCnt);
		this.setPayedMoneySum(payMoney);
		this.setUnPayedMoneySum(unPayMoney);
		this.setArrivedCount(arriveCnt);
		this.setNotArrivedCount(notArriveCnt);
		this.setSendCount(sendCnt);
	}
}
