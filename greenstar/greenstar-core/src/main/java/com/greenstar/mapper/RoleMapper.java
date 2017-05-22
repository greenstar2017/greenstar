package com.greenstar.mapper;

import java.util.List;

import com.greenstar.annotation.MapperClass;
import com.greenstar.conf.mybatis.MyBaseMapper;
import com.greenstar.entity.Role;

@MapperClass(value=RoleMapper.class)
public interface RoleMapper extends MyBaseMapper<Role> {
    public List<Role> queryRoleListWithSelected(Integer id);
}