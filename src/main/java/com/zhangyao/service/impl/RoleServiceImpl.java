package com.zhangyao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangyao.dao.RoleDao;
import com.zhangyao.entity.Permission;
import com.zhangyao.entity.Role;
import com.zhangyao.service.RoleService;

/**
 * @author zhangyao
 * @date Mar 7, 2019
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public void correlationPermissions(Long roleId, Long... permissionIds) {
		roleDao.correlationPermissions(roleId, permissionIds);
	}

	@Override
	public void uncorrelationPermissions(Long roleId, Long... permissionIds) {
		roleDao.uncorrelationPermissions(roleId, permissionIds);
	}

	@Override
	public Role findById(Long id) {
		return roleDao.findById(id);
	}

	@Override
	public List<Permission> findRolePermissionByRoleId(Long id) {
		return roleDao.findRolePermissionByRoleId(id);
	}

	@Override
	public List<Permission> findPermissionByRoleId(Long id) {
		return roleDao.findPermissionByRoleId(id);
	}

	@Override
	public void deleteAllRolePermissions(Long id) {
		roleDao.deleteAllRolePermissions(id);
	}

	@Override
	public void updateUserRole_Id(Role role) {
		roleDao.updateUserRole_Id(role);
	}

	@Override
	public void create(Role role) {
		roleDao.create(role);
	}

	@Override
	public void delete(Long id) {
		roleDao.delete(id);
	}

	@Override
	public void update(Role role) {
		roleDao.update(role);
	}

	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}

	@Override
	public List<Role> findRolesByPid(Long id) {
		// TODO Auto-generated method stub
		return roleDao.findRolesByPid(id);
	}

}
