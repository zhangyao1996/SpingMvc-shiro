package com.zhangyao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhangyao.entity.Permission;
import com.zhangyao.entity.Role;
import com.zhangyao.entity.User;

/**
 * @author zhangyao
 * @date Mar 7, 2019
 */
public interface UserMapper {

	void correlationRoles(@Param("userId") Long userId, @Param("roleId") Long roleId);

	void uncorrelationRoles(@Param("userId") Long userId, @Param("roleId") Long roleId);

	List<Role> findRoles(String username);

	List<Permission> findPermissions(String username);

	boolean exists(@Param("userId") Long userId, @Param("roleId") Long roleId);

	void create(User user);

	void delete(Long id);

	void update(User user);

	List<User> findAll();

	User findByName(String username);

	User findById(Long id);

	void deleteAllUserRoles(Long id);

}
