package com.shizuwei.test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shizuwei.dal.common.page.PageBean;
import com.shizuwei.dal.common.page.PaginationContext;
import com.shizuwei.dal.main.dao.BrandMapper;
import com.shizuwei.dal.main.dao.GoodsMapper;
import com.shizuwei.dal.main.dao.OrderMapper;
import com.shizuwei.dal.main.dao.UserMapper;
import com.shizuwei.dal.main.po.Goods;
import com.shizuwei.dal.main.po.Order;
import com.shizuwei.dal.main.po.User;
import com.shizuwei.service.main.GoodsService;

import junit.framework.Assert;

import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true) 
@Transactional  
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestSpring extends AbstractTransactionalJUnit4SpringContextTests{
	private static final Logger logger = LoggerFactory.getLogger(TestSpring.class);
	@Resource
	private GoodsMapper goodsMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private BrandMapper brandMapper;
	@Resource 
	private GoodsService goodsService;
	
	@Test
	public void testGoodsService(){
		goodsService.importGoods("2017-5-21");
		logger.debug("goods = {}", goodsMapper.list(new Goods()));
	}
	 
	public void test() {
		PaginationContext.setPageNum(1);
		PaginationContext.setPageSize(5);
		PageHelper.startPage(PaginationContext.getPageNum(), PaginationContext.getPageSize());
		List<Goods> list = goodsMapper.list(new Goods());
		logger.info("list={}", list);
		PageBean<Goods> bean = new PageBean<>(list);
		logger.info("bean={}", bean);
		Assert.assertTrue(!list.isEmpty());
	}
	
	 
	public void testUser(){
		List<User> users = userMapper.list();
		logger.info("users = {}", users);
		 
		userMapper.insert(new User("abcd", "00024"));
		userMapper.insert(new User("xxx", "00025"));
		userMapper.insert(new User("yyy", "00026"));
		userMapper.insert(new User("ddd", "00027"));
		
		users = userMapper.list();
		logger.info("users2 = {}", users);
	}
	
	 
	public void testOrder(){
		List<Order> orders = orderMapper.listWithGoods(new Order());
		logger.info("orders = {}", orders);
		
		PaginationContext.setPageNum(2);
		PaginationContext.setPageSize(5);
		PageHelper.startPage(PaginationContext.getPageNum(), PaginationContext.getPageSize());
		List<Integer> ids = orderMapper.listOrderIds(new Order());
		logger.info("ids = {}", ids);
		
		PageBean<Integer> bean = new PageBean<>(ids);
		logger.info("bean={}", bean);
		
		orders = orderMapper.listWithGoodsByIds(ids);
		logger.info("orders2 = {}", orders);
	
	}

	public static void main(String[] args) throws Exception {
		InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
		SqlSessionFactoryBuilder sessionFactoryBuilder = new SqlSessionFactoryBuilder();
		SqlSessionFactory sessionFactory = sessionFactoryBuilder.build(inputStream);
		SqlSession sqlSession = sessionFactory.openSession();
		PageHelper.startPage(1, 10);
		PageInfo<String> pageInfo = new PageInfo<>();
		pageInfo.getTotal();
		sqlSession.close();

	}

}
