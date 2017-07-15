package com.shizuwei.service.main.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.shizuwei.dal.main.dao.GoodsMapper;
import com.shizuwei.dal.main.dao.OrderGoodsMapper;
import com.shizuwei.dal.main.dao.OrderMapper;
import com.shizuwei.dal.main.po.Goods;
import com.shizuwei.dal.main.po.OrderGoods;
import com.shizuwei.service.main.GoodsService;
import com.shizuwei.utils.DebugUtils;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class GoodsServiceImpl implements GoodsService {
	@Resource
	private GoodsMapper goodsMapper;

	@Resource
	private OrderGoodsMapper orderGoodsMapper;

	@Resource
	private OrderMapper orderMapper;

	private static final String basePath = "D:\\PICS\\";

	@Override
	public Integer insert(Goods goods) {
		this.goodsMapper.insert(goods);
		return goods.getGoodsId();
	}

	@Override
	public String importGoods(String path) {
		String realPath = basePath + path;
		File file = new File(realPath);
		Preconditions.checkArgument(file.exists() && file.isDirectory());
		File[] files = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(".png") || name.endsWith("jpg")) {
					return true;
				}
				return false;
			}
		});

		for (int i = 0; i < files.length; i++) {
			Goods goods = new Goods();
			goods.setGoodsName(files[i].getAbsolutePath());
			goods.setGoodsNumber("NONE");
			goods.setGoodsPrice(BigDecimal.ZERO);
			goods.setSize("NONE");
			goodsMapper.insert(goods);
		}

		return path;
	}

	@Override
	public void delete(Integer id) {
		Preconditions.checkNotNull(id);
		OrderGoods orderGoods = new OrderGoods();
		orderGoods.setGoodsId(id);
		List<OrderGoods> orderGoodsList = this.orderGoodsMapper.list(orderGoods);
		Preconditions.checkArgument(CollectionUtils.isEmpty(orderGoodsList), "这个尺寸已经关联到了订单无法删除！");
		this.goodsMapper.delById(id);
	}

	private static boolean isGoodsPriceChanged(Goods newer, Goods old) {
		return newer.getGoodsPrice() != null && !newer.getGoodsPrice().equals(old.getGoodsPrice());
	}

	private static boolean isGoodsFreightChanged(Goods newer, Goods old) {
		return newer.getGoodsFreight() != null && !newer.getGoodsFreight().equals(old.getGoodsFreight());
	}

	@Override
	public void edit(Goods goods) {
		DebugUtils.transactionRequired("check trasaction.");
		Preconditions.checkNotNull(goods.getGoodsId());

		Goods oldGoods = this.goodsMapper.getById(goods.getGoodsId());

		Preconditions.checkNotNull(oldGoods, "编辑的产品 id=%d不存在！", goods.getGoodsId());

		// 先更新，以免会用到
		this.goodsMapper.update(goods);

		if (isGoodsPriceChanged(goods, oldGoods)) {
			// 如果修改的产品的价格，则需要重新计算订单价格
			OrderGoods orderGoodsQuery = new OrderGoods();
			orderGoodsQuery.setGoodsId(goods.getGoodsId());
			// 查找产品对应所有的OrderGoods
			List<OrderGoods> orderGoodsList = this.orderGoodsMapper.list(orderGoodsQuery);

			if (CollectionUtils.isNotEmpty(orderGoodsList)) {
				// 如果有关联订单, 则全部更新
				for (OrderGoods og : orderGoodsList) {
					// 从新计算单个产品的价格
					// 如果运费变了直接用新的运费计算
					BigDecimal freight = null;
					if (isGoodsFreightChanged(goods, oldGoods)) {
						freight = goods.getGoodsFreight();
					}
					// cnt * (goodsPrice + freight) ?? 还有汇率？？
					BigDecimal orderGoodsPrice = BigDecimal.valueOf(og.getGoodsCnt())
							.multiply((goods.getGoodsPrice().add(freight)));
					og.setOrderGoodsPrice(orderGoodsPrice);
					// 更新orderGoodsPrice
					this.orderGoodsMapper.update(og);
					// 更新对应的Order
					this.orderMapper.updateOrderTotalPriceById(og.getOrderId());
				}
			}

			/*
			 * if (CollectionUtils.isNotEmpty(orderIds)) { orderIds.forEach(id
			 * -> { this.orderMapper.updateOrderTotalPriceById(id); }); }
			 */

			// Map<Integer, BigDecimal> orderIdsPriceMap = Maps.newHashMap();

			// 更新order总价
			/*
			 * orderIdsPriceMap.forEach((k, v) -> { Order order = new Order();
			 * order.setOrderId(k); order.setOrderTotalPrice(v);
			 * this.orderMapper.update(order); });
			 */
		}

	}

}
