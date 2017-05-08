package com.shizuwei.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shizuwei.controller.common.response.Response;
import com.shizuwei.controller.common.response.ResponseBuilder;
import com.shizuwei.controller.common.response.ResponseStatus;
import com.shizuwei.dal.test.po.TestPo;
import com.shizuwei.service.constant.ErrorCode;
import com.shizuwei.service.test.TestService;

@Controller
public class TestController {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	@Resource
	private TestService testService;
   @RequestMapping("test/test1.do")
    public ModelAndView courseSeat(HttpServletResponse response, HttpServletRequest request,
        @RequestParam("id") Integer id) {
	   	ModelAndView modelAndView = new ModelAndView("index");
	   	TestPo obj = testService.getTestPo(id);
	   	modelAndView.addObject("value", obj.getValue());
        return modelAndView;
    }
   
   @RequestMapping(value = "test/test2.do", method = { RequestMethod.POST, RequestMethod.GET })
   @ResponseBody
   public Response courseAndStudentInfo(HttpServletResponse response, HttpServletRequest request,
		   @RequestParam Integer id) {
	   
       try {
           if (id == null ) {
               return new ResponseBuilder(ResponseStatus.PARAM_ERROR, ErrorCode.PARAM_NULL).build();
           }
           Object obj = testService.getTestPo(id);
           logger.info(obj.toString());
           return new ResponseBuilder().setData(obj).build();
       } catch (Exception e) {
           logger.error("test/test2.do", e);
           return new ResponseBuilder(ResponseStatus.BUSINESS_ERROR).build();
       }
   }
}
