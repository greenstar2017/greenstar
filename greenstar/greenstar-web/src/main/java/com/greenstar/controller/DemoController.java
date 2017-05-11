package com.greenstar.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenstar.entity.Demo;
import com.greenstar.service.DemoService;

@Controller 
public class DemoController {

	@Autowired
	private DemoService demoService;

	@ResponseBody
	@RequestMapping("/add")
	public String add(String name) {
		Demo demo = new Demo();
		demo.setName("1");
		demo.setCreateTime(new Date());
		demo.setUpdateTime(new Date());
		demoService.addEntity(demo);
		return "" + demo.getId();
	}

	@ResponseBody
	@RequestMapping("/findById")
	public String findById(Integer id) {
		Demo demo = demoService.getEntityById(Demo.class, id);
		return demo.toString();
	}
	
	@RequestMapping("/test")
	public String test(Model model) {
		model.addAttribute("data", "xxx");
		return "modules/base/test";
	}

}