package com.zhangyao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zhangyao.entity.Permission;
import com.zhangyao.entity.Role;
import com.zhangyao.entity.User;

/**
 * @author zhangyao
 * @date Mar 7, 2019
 */
public interface UserService extends BaseService<User>{

	/**
	 * 添加用户-角色关系
	 *
	 * @param userId
	 * @param roleIds
	 */
	void correlationRoles(Long userId, Long... roleIds);

	/**
	 * 移除用户-角色关系
	 *
	 * @param userId
	 * @param roleIds
	 */
	void uncorrelationRoles(Long userId, Long... roleIds);

	/**
	 * 根据用户名查找其他角色
	 *
	 * @param username
	 * @return
	 */
	List<Role> findRoles(String username);

	/**
	 * 根据用户名查找其他权限
	 *
	 * @param username
	 * @return
	 */
	List<Permission> findPermissions(String username);

	/**
	 * 修改密码
	 *
	 * @param userId
	 * @param newPassword
	 */
	void changePassword(Long userId, String newPassword);

	/**
	 * 根据用户名查询
	 *
	 * @param username
	 * @return
	 */
	User findByName(String username);

	/**
	 * 删除此用户关联的所有角色信息
	 * 
	 * @param id
	 */
	void deleteAllUserRoles(Long id);

}
