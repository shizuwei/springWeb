package com.shizuwei.dal.main.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shizuwei.controller.common.dto.PageDto;
import com.shizuwei.dal.main.constants.GoodsStatus;
import com.shizuwei.dal.main.constants.OrderStatus;
import com.shizuwei.dal.main.dao.BrandDao;
import com.shizuwei.dal.main.dao.OrderDao;
import com.shizuwei.dal.main.dao.UserDao;
import com.shizuwei.dal.main.po.Brand;
import com.shizuwei.dal.main.po.DetailOrder;
import com.shizuwei.dal.main.po.Goods;
import com.shizuwei.dal.main.po.Order;
import com.shizuwei.dal.main.po.OrderStastics;
import com.shizuwei.dal.main.po.User;
import com.shizuwei.service.dto.request.OrderListReqestDto;

@Repository
public class OrderDaoImpl implements OrderDao {
	private static final Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);
	@Resource
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	@Resource
	private BrandDao brandDao;
	@Resource
	private UserDao userDao;

	@Override
	public Order getOrder(Integer id) {
		Preconditions.checkNotNull(id);
		String sql = "select * from webdata.order where id = :id";
		Order param = new Order();
		param.setId(1);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(param);
		return namedJdbcTemplate.queryForObject(sql, paramSource, new BeanPropertyRowMapper<Order>(Order.class));
	}

	@Override
	public List<DetailOrder> getDetailOrderList(OrderListReqestDto request) {

		Integer brandId = null;
		Integer userId = null;

		if (request.getBrand() != null) {
			brandDao.fillBrandId(request.getBrand());
			brandId = request.getBrand().getId();
		}

		if (request.getUser() != null) {
			userDao.fillUserId(request.getUser());
			userId = request.getUser().getId();
		}

		if (request.getUser() != null && StringUtils.isNoneBlank(request.getUser().getAccountNumber())
				&& userId == null) {
			return Lists.newArrayList();
		}

		OrderListSqlBuilder sqlBuilder = new OrderListSqlBuilder(request, brandId, userId, request.getPageDto());

		if (request.getPageDto() != null) {
			Integer totalCount = this.namedJdbcTemplate.queryForObject(sqlBuilder.getCountSql(),
					sqlBuilder.getParmMap(), Integer.class);
			logger.debug("totalCount = {}", totalCount);
			request.getPageDto().setCount(totalCount);
		}

		sqlBuilder = new OrderListSqlBuilder(request, brandId, userId, request.getPageDto());

		List<DetailOrder> list = Lists.newArrayList();
		this.namedJdbcTemplate.query(sqlBuilder.getSql(), sqlBuilder.getParmMap(), new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				DetailOrder detailOrder = new DetailOrder();
				/**
				 * build brand
				 */
				Brand brand = detailOrder.getBrand();
				brand.setId(rs.getInt("brand_id"));
				brand.setBrandName(rs.getString("brand_name"));
				/**
				 * build user
				 */
				User user = detailOrder.getUser();
				user.setId(rs.getInt("user_id"));
				user.setWxName(rs.getString("wx_name"));
				user.setAccountNumber(rs.getString("account_number"));

				/**
				 * build goods
				 */
				Goods goods = detailOrder.getGoods();
				goods.setBrandId(rs.getInt("brand_id"));
				goods.setGoodNumber(rs.getString("good_number"));
				goods.setId(rs.getInt("goods_id"));
				goods.setImgURL(rs.getString("img_url"));
				goods.setGoodsName(rs.getString("goods_name"));
				goods.setPrice(rs.getLong("price"));
				goods.setSize(rs.getString("size"));
				goods.setUpdateTime(rs.getDate("update_time"));

				/**
				 * build order
				 */
				Order order = detailOrder.getOrder();
				order.setGoodsStatus(rs.getInt("goods_status"));
				order.setCount(rs.getInt("count"));
				order.setExchangeRate(rs.getInt("exchange_rate"));
				order.setFreight(rs.getLong("freight"));
				order.setGoodsId(rs.getInt("goods_id"));
				order.setId(rs.getInt("order_id"));
				order.setOrderTime(rs.getDate("order_time"));
				order.setPayTime(rs.getDate("pay_time"));
				order.setOrderStatus(rs.getInt("order_status"));
				order.setTotalPrice(rs.getLong("total_price"));
				order.setUserId(rs.getInt("user_id"));
				order.setDiscription(rs.getString("discription"));
				list.add(detailOrder);
			}

		});
		logger.info(list.toString());
		return list;
	}

	@Override
	public OrderStastics getOrderStastics(Integer userId) {
		OrderStastics stastics = new OrderStastics();
		String orderStatusSql = "SELECT SUM(1) as sum, SUM(total_price) as total from webdata.`order` WHERE order_status = :orderStatus "
				+ (userId != null ? "and user_id = :userId" : "");
		String goodsStatusSql = "SELECT SUM(1) from webdata.`order` WHERE goods_status = :goodsStatus "
				+ (userId != null ? "and user_id = :userId" : "");
		logger.debug("orderStatusSql = {}", orderStatusSql);
		logger.debug("goodsStatusSql = {}", goodsStatusSql);
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("userId", userId);
		paramMap.put("orderStatus", OrderStatus.PAYED.getCode());
		Map<String, Object> orderStastic = this.namedJdbcTemplate.queryForMap(orderStatusSql, paramMap);
		stastics.setPayedCount(Integer.valueOf(orderStastic.get("sum").toString()));
		stastics.setPayedMoneySum(Long.valueOf(orderStastic.get("total").toString()));
		paramMap.put("orderStatus", OrderStatus.UNPAY.getCode());
		orderStastic = this.namedJdbcTemplate.queryForMap(orderStatusSql, paramMap);
		stastics.setUnPayedCount(Integer.valueOf(orderStastic.get("sum").toString()));
		stastics.setUnPayedMoneySum(Long.valueOf(orderStastic.get("total").toString()));
		paramMap.put("goodsStatus", GoodsStatus.ARRIVED.getCode());
		stastics.setArrivedCount(this.namedJdbcTemplate.queryForObject(goodsStatusSql, paramMap, Integer.class));
		paramMap.put("goodsStatus", GoodsStatus.NOT_ARRIVED.getCode());
		stastics.setNotArrivedCount(this.namedJdbcTemplate.queryForObject(goodsStatusSql, paramMap, Integer.class));
		paramMap.put("goodsStatus", GoodsStatus.SEND.getCode());
		stastics.setSendCount(this.namedJdbcTemplate.queryForObject(goodsStatusSql, paramMap, Integer.class));
		logger.debug("stastics = {}", stastics);
		return stastics;
	}

	@Override
	public OrderStastics getOrderStastics(OrderListReqestDto request) {
		Integer userId = null;
		if (request.getUser() != null) {
			userDao.fillUserId(request.getUser());
			userId = request.getUser().getId();
		}
		return getOrderStastics(userId);
	}

	private static class OrderListSqlBuilder {
		private Map<String, Object> paramMap = Maps.newHashMap();
		private static String selectParams = "SELECT *, o.id as order_id ";
		private static String countParams = "SELECT count(1) as cnt ";
		private StringBuilder sqlBuiler = new StringBuilder("FROM webdata.`order` o "
				+ "JOIN webdata.`user` u ON u.id = o.user_id " + "JOIN webdata.goods g ON g.id = o.goods_id "
				+ "JOIN webdata.brand b ON b.id = g.brand_id where 1 = 1 ");

		public OrderListSqlBuilder(OrderListReqestDto request, Integer brandId, Integer userId, PageDto pageDto) {

			if (brandId != null) {
				sqlBuiler.append("AND g.brand_id = :brandId ");
				paramMap.put("brandId", brandId);
			}

			if (userId != null) {
				sqlBuiler.append("AND o.user_id = :userId ");
				paramMap.put("userId", userId);
			}
			if (request.getPayTimeEnd() != null) {
				sqlBuiler.append("AND o.pay_time < :payTimeEnd ");
				paramMap.put("payTimeEnd", request.getPayTimeEnd());
			}

			if (request.getPayTimeStart() != null) {
				sqlBuiler.append("AND o.pay_time >= :payTimeStart ");
				paramMap.put("payTimeStart", request.getPayTimeStart());
			}

			if (request.getOrderStatus() != null) {
				sqlBuiler.append("AND o.order_status = :orderStatus ");
				paramMap.put("orderStatus", request.getOrderStatus().getCode());
			}

			if (request.getGoodsStatus() != null) {
				sqlBuiler.append("AND o.goods_status = :goodsStatus ");
				paramMap.put("goodsStatus", request.getGoodsStatus().getCode());
			}

			if (pageDto != null && pageDto.getCount().intValue() > 0) {
				sqlBuiler.append("limit :firstNum, :curPageCount ");
				paramMap.put("firstNum", pageDto.firstNum());
				paramMap.put("curPageCount", pageDto.getCurPageCount());
			}

		}

		public String getSql() {
			String sql = selectParams + this.sqlBuiler.toString();
			logger.debug(sql);
			return sql;
		}

		public String getCountSql() {
			String sql = countParams + this.sqlBuiler.toString();
			logger.debug(sql);
			return sql;
		}

		public Map<String, Object> getParmMap() {
			logger.debug("paramap = {}", this.paramMap);
			return this.paramMap;
		}

	}

	public static void main(String args[]) {
		OrderListReqestDto request = new OrderListReqestDto();
		request.setOrderStatus(OrderStatus.PAYED);
		OrderListSqlBuilder sqlBuilder = new OrderListSqlBuilder(request, 1, 1, null);
		System.out.println(sqlBuilder.getSql());
		System.out.println(sqlBuilder.getParmMap());
	}

}
