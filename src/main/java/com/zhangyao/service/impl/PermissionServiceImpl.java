package com.zhangyao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangyao.dao.PermissionDao;
import com.zhangyao.entity.Permission;
import com.zhangyao.entity.Role;
import com.zhangyao.service.PermissionService;

/**
 * @author zhangyao
 * @date Mar 7, 2019
 */
@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;
	
	@Override
	public Permission findById(Long id) {
		return permissionDao.findById(id);
	}

	@Override
	public void create(Permission permission) {
		permissionDao.create(permission);
	}

	@Override
	public void delete(Long id) {
		permissionDao.delete(id);
	}

	@Override
	public void update(Permission permission) {
		permissionDao.update(permission);
	}

	@Override
	public List<Permission> findAll() {
		return permissionDao.findAll();
	}

	@Override
	public List<Role> findRoleByPermissionId(Long id) {
		return permissionDao.findRoleByPermissionId(id);
	}

	@Override
	public void deleteAllPermissionsRoles(Long id) {
		permissionDao.deleteAllPermissionsRoles(id);
	}

	@Override
	public void correlationRoles(Long permissionId, Long roleId) {
		permissionDao.correlationRoles(permissionId, roleId);
	}

}
