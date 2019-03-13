package com.zhangyao.service;

import java.util.List;

import com.zhangyao.entity.Permission;
import com.zhangyao.entity.Role;

/**
 * @author zhangyao
 *@date Mar 7, 2019
 */
public interface RoleService extends BaseService<Role> {
	 /**
     * 添加角色-权限之间关系
     *
     * @param roleId
     * @param permissionIds
     */
    void correlationPermissions(Long roleId, Long... permissionIds);

    /**
     * 移除角色-权限之间关系
     *
     * @param roleId
     * @param permissionIds
     */
    void uncorrelationPermissions(Long roleId, Long... permissionIds);

    /**
     * 根据角色id查询当前role-permission表中关联的数据
     *
     * @return
     */
    List<Permission> findRolePermissionByRoleId(Long id);

    /**
     * 根据角色id查询permission表中属于此角色节点的子节点权限
     *
     * @param id
     * @return
     */
    List<Permission> findPermissionByRoleId(Long id);

    /**
     * 删除此角色所有关联的权限id
     *
     * @param id
     */
    void deleteAllRolePermissions(Long id);

    /**
     * 通过当前角色的更新数据，更新用户表中显示关联角色的字段
     *
     * @param role
     */
    void updateUserRole_Id(Role role);
    
    /**
     * 通过父节点获取roleList
     * @param id
     * @return
     */
    List<Role> findRolesByPid(Long id);

}
