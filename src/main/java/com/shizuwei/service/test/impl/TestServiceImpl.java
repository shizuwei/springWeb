package com.shizuwei.service.test.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shizuwei.dal.test.dao.TestDao;
import com.shizuwei.dal.test.po.TestPo;
import com.shizuwei.service.test.TestService;

@Service("testService")
public class TestServiceImpl implements TestService {

	@Resource
	private TestDao testDao;
	public TestPo getTestPo(Integer id) {
		return testDao.getTestPo(id);
	}

}
