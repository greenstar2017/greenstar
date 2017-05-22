package com.greenstar.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import com.greenstar.annotation.MapperClass;
import com.greenstar.mapper.UserRoleMapper;

@MapperClass(value = UserRoleMapper.class)
@Table(name = "user_role")
public class UserRole implements Serializable {
	private static final long serialVersionUID = -916411139749530670L;
	@Column(name = "userId")
	private Integer userid;

	@Column(name = "roleId")
	private String roleid;

	/**
	 * @return userId
	 */
	public Integer getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
}