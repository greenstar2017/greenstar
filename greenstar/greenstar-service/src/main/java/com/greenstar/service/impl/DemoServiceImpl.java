package com.greenstar.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.greenstar.dto.FlexiPageDto;
import com.greenstar.entity.Demo;
import com.greenstar.mapper.DemoMapper;
import com.greenstar.service.DemoService;

@Service
public class DemoServiceImpl extends BaseServiceImpl<Demo> implements DemoService{

	@Resource
	private DemoMapper mapper;
	
	@Override
	public PageInfo<Demo> selectMyPage(FlexiPageDto pageHelper) {
		PageHelper.startPage(pageHelper.getPage(), pageHelper.getRp());
		List<Demo> list = mapper.selectMyPage(pageHelper);
		PageInfo<Demo> page = new PageInfo<Demo>(list);
		
		return page;
	}
}