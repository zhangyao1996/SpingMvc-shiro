package com.zhangyao.dao;

import java.util.List;

import com.zhangyao.entity.Permission;
import com.zhangyao.entity.Role;

/**
 * @author zhangyao
 * @date Mar 7, 2019
 */
public interface RoleDao {

	void correlationPermissions(Long roleId, Long[] permissionIds);

	void uncorrelationPermissions(Long roleId, Long[] permissionIds);

	Role findById(Long id);

	void create(Role role);

	void delete(Long id);

	List<Role> findAll();

	List<Permission> findRolePermissionByRoleId(Long id);

	List<Permission> findPermissionByRoleId(Long id);

	void update(Role role);

	void deleteAllRolePermissions(Long id);

	void updateUserRole_Id(Role role);
}
