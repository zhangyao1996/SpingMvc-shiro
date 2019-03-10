package com.zhangyao.entity;

import java.io.Serializable;

/**
 * @author zhangyao
 * @date Mar 7, 2019
 */
public class UserRole implements Serializable {
	private Long id; // 编号
	private Long userId; // 用户ID
	private Long roleId; // 角色ID

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "UserRole{" + "userId=" + userId + ", roleId=" + roleId + '}';
	}
}
