package com.greenstar.mapper;

import com.greenstar.annotation.MapperClass;
import com.greenstar.conf.mybatis.MyBaseMapper;
import com.greenstar.entity.RoleResources;

@MapperClass(value=UserMapper.class)
public interface RoleResourcesMapper extends MyBaseMapper<RoleResources> {
}