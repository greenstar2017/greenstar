package com.greenstar.controller.demo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
	
	@RequestMapping("/list")
	public String list() {
		return "modules/demo/list";
	}

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
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/findMyPage")
	public RestObject findMyPage(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize) {
		Page<Demo> page = PageHelper.startPage(1, 2);
		Map<String, Object> condition = new HashMap<String, Object>();
//		condition.put("name", "123");
		List<Demo> data = demoService.selectList(condition);
		
//		condition = new HashMap<String, Object>();
//		data = demoService.selectList(condition);
		return RestObject.newOk("", data, page.getTotal());
	}
	
}