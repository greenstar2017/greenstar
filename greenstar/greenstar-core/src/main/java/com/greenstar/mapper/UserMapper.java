package com.greenstar.mapper;

import com.greenstar.annotation.MapperClass;
import com.greenstar.conf.mybatis.MyBaseMapper;
import com.greenstar.entity.User;

@MapperClass(value=RoleResourcesMapper.class)
public interface UserMapper extends MyBaseMapper<User> {
}