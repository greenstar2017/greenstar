package com.greenstar.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.greenstar.entity.Demo;
import com.greenstar.mapper.DemoMapper;
import com.greenstar.service.DemoService;

@Service
public class DemoServiceImpl extends BaseServiceImpl<Demo> implements DemoService{

	@Resource
	private DemoMapper mapper;
	
	@Override
	public List<Demo> selectList(Map<String, Object> condition) {
		
		return mapper.selectList(condition);
	}
}