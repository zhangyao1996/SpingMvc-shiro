package com.zhangyao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhangyao.entity.Permission;
import com.zhangyao.entity.Role;

/**
 * @author zhangyao
 * @date Mar 7, 2019
 */
public interface PermissionMapper {

	void create(Permission permission);

	void deleteRolePermission(Long permissionId);

	void deletePermission(Long permissionId);

	List<Permission> findAll();

	void update(Permission permission);

	Permission findById(Long id);

	List<Role> findRoleByPermissionId(Long id);

	void deleteAllPermissionsRoles(Long id);

	void correlationRoles(@Param("permissionId") Long permissionId, @Param("roleId") Long roleId);

}
