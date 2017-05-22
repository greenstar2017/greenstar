package com.greenstar.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.greenstar.entity.Role;

/**
 * @author yuanhualiang
 *
 */
public interface RoleService extends BaseService<Role> {

    public List<Role> queryRoleListWithSelected(Integer uid);

    PageInfo<Role> selectByPage(Role role, int start, int length);

    /**
     * 删除角色 同时删除角色资源表中的数据
     * @param roleid
     */
    public void delRole(Integer roleid);
}
