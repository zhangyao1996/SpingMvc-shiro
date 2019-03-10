package com.zhangyao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhangyao.entity.Permission;
import com.zhangyao.entity.Role;

/**
 * @author zhangyao
 * @date Mar 7, 2019
 */
public interface RoleMapper {
	void correlationPermissions(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

	void uncorrelationPermissions(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

	boolean exists(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

	Role findById(Long id);

	void create(Role role);

	void deleteUserRole(Long roleId);

	void deleteRole(Long roleId);

	List<Role> findAll();

	List<Permission> findRolePermissionByRoleId(Long id);

	List<Permission> findPermissionByRoleId(Long id);

	void update(Role role);

	void deleteAllRolePermissions(Long id);

	void updateUserRole_Id(Role role);
}
