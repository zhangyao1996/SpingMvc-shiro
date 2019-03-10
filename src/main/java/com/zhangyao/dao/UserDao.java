package com.zhangyao.dao;

import java.util.List;

import com.zhangyao.entity.Permission;
import com.zhangyao.entity.Role;
import com.zhangyao.entity.User;

/**
 * @author zhangyao
 * @date Mar 7, 2019
 */
public interface UserDao {
	void correlationRoles(Long userId, Long... roleIds);

	void uncorrelationRoles(Long userId, Long... roleIds);

	List<Role> findRoles(String username);

	List<Permission> findPermissions(String username);

	void create(User user);

	void delete(Long id);

	void update(User user);

	List<User> findAll();

	User findByName(String username);

	User findById(Long id);

	void deleteAllUserRoles(Long id);
}
