package com.greenstar.mapper;

import java.util.List;

import com.greenstar.annotation.MapperClass;
import com.greenstar.conf.mybatis.MyBaseMapper;
import com.greenstar.entity.UserRole;

@MapperClass(value=UserRoleMapper.class)
public interface UserRoleMapper extends MyBaseMapper<UserRole> {
    public List<Integer> findUserIdByRoleId(Integer roleId);
}