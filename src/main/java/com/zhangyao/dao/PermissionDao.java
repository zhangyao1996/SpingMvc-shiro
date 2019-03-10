package com.zhangyao.dao;

import java.util.List;

import com.zhangyao.entity.Permission;
import com.zhangyao.entity.Role;

/**
 * @author zhangyao
 * @date Mar 7, 2019
 */
public interface PermissionDao {
	void create(Permission permission);

	void delete(Long id);

	List<Permission> findAll();

	void update(Permission permission);

	Permission findById(Long id);

	List<Role> findRoleByPermissionId(Long id);

	void deleteAllPermissionsRoles(Long id);

	void correlationRoles(Long permissionId, Long roleId);
}
