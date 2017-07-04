package com.shizuwei.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import com.shizuwei.dal.main.dao.GoodsMapper;
import com.shizuwei.dal.main.dao.OrderMapper;
import com.shizuwei.dal.main.dao.UserMapper;
import com.shizuwei.service.main.GoodsService;
import com.shizuwei.service.main.ImgService;

import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestSpring extends AbstractTransactionalJUnit4SpringContextTests {
	private static final Logger logger = LoggerFactory.getLogger(TestSpring.class);
	@Resource
	private GoodsMapper goodsMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private GoodsService goodsService;
	@Resource
	private ImgService ImgService;

	@Test
	public void test() {
		logger.debug("test");
	}

	public static void main(String[] args) throws Exception {

	}

}
