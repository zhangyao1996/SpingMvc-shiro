package com.zhangyao.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangyao.entity.Role;
import com.zhangyao.entity.TreeEntity;
import com.zhangyao.service.PermissionService;
import com.zhangyao.service.RoleService;

/**
 * @author zhangyao
 *@date Mar 7, 2019
 */
@Controller
@RequestMapping("/permissions")
public class PermissionController {
	
	
    @Autowired
    private PermissionService permissionService;
    
    @Autowired
    private RoleService roleService;
	/**
     * 查询所有
     *
     * @param model
     * @return
     */
    @RequiresRoles(value = {"admin"})//授权
    @RequestMapping("/findAll")
    public String findAll(Model model) {
        model.addAttribute("permissionsList", permissionService.findAll());
        return "page/permission";
    }
    
    /**
     * 新增-- 构建一棵Tree树
     */
    @ResponseBody
    @RequestMapping("/getRolesZTree")
    public List<TreeEntity> getRolesZTree() {
        try {
            List<TreeEntity> treeList = new ArrayList<TreeEntity>();
            List<Role> dataList = roleService.findAll();
            for (Role role : dataList) {
                if (role.getPid() == 0) {
                    //说明是父节点
                    treeList.add(new TreeEntity(role.getId(), role.getDescription(), true, role.getPid()));
                } else {
                    //说明是子节点
                    treeList.add(new TreeEntity(role.getId(), role.getDescription(), false, role.getPid()));
                }
            }
            return treeList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据权限id查询其所关联的角色数据
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/findRoleByPermissionId")
    public List<Role> findRoleByPermissionId(@RequestParam("id") Long id) {
        try {
            return permissionService.findRoleByPermissionId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	

}
