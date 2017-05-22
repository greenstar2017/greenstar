package com.greenstar.mapper;

import java.util.List;
import java.util.Map;

import com.greenstar.annotation.MapperClass;
import com.greenstar.conf.mybatis.MyBaseMapper;
import com.greenstar.entity.Resources;

@MapperClass(value=ResourcesMapper.class)
public interface ResourcesMapper extends MyBaseMapper<Resources> {

    public List<Resources> queryAll();

    public List<Resources> loadUserResources(Map<String,Object> map);

    public List<Resources> queryResourcesListWithSelected(Integer rid);
}