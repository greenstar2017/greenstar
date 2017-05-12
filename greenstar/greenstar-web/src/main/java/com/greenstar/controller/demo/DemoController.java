package com.greenstar.controller.demo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageInfo;
import com.greenstar.controller.common.BaseController;
import com.greenstar.dto.FlexiPageDto;
import com.greenstar.entity.Demo;
import com.greenstar.response.RestObject;
import com.greenstar.service.DemoService;

@Controller
@RequestMapping(value = "/demo")
public class DemoController extends BaseController{

	@Autowired
	private DemoService demoService;

	@ResponseBody
	@RequestMapping("/add")
	public String add(String name) {
		Demo demo = new Demo();
		demo.setName(name);
		demo.setCreateTime(new Date());
		demo.setUpdateTime(new Date());
		demoService.addEntity(demo);
		return "" + demo.getId();
	}

	
	@ResponseBody
	@RequestMapping("/find")
	public RestObject findData(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize) {
		Example example = new Example(Demo.class);
		FlexiPageDto flexiPageDto = new FlexiPageDto(pageNumber, 2);
		List<Demo> demos = demoService.findByPage(example, flexiPageDto);
		int rowCount = demoService.findRowCount(example);
		return RestObject.newOk("", demos, rowCount);
	}
	
	@ResponseBody
	@RequestMapping("/findMyPage")
	public RestObject findMyPage(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize) {
		FlexiPageDto flexiPageDto = new FlexiPageDto(1, 2);
		PageInfo<Demo> page = demoService.selectMyPage(flexiPageDto);
//		int rowCount = demoService.findRowCount(example);
		return RestObject.newOk("", page.getList(), page.getTotal());
	}
	
}