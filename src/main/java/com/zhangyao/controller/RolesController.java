package com.zhangyao.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangyao.entity.Permission;
import com.zhangyao.entity.Role;
import com.zhangyao.entity.Tree;
import com.zhangyao.entity.TreeEntity;
import com.zhangyao.service.RoleService;
import com.zhangyao.utils.TreeUtils;

/**
 * @author zhangyao
 * @version Mar 12, 2019 3:12:34 PM
 */

@Controller
@RequestMapping("/roles")
public class RolesController {
	@Autowired
	private RoleService roleService;
	
	
	
//	@RequestMapping("/findRoles")
//	@ResponseBody
//	public List<Tree<Role>> getAllRoles(){
//		List<Role> roleList=roleService.findAll();
//		List<Tree<Role>> treeList=new ArrayList<>();
//		for (Role role : roleList) {
//			Tree<Role> tree=new Tree<>();
//			tree.setId(role.getId());
//			tree.setpId(role.getPid());
//			tree.setName(role.getDescription());
//			treeList.add(tree);
//		}
//		return	TreeUtils.build2(treeList);
//		//return TreeUtils.build(treeList);
//	}

	/**
	 * 根据id查询角色数据
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findById")
	public Role findById(@RequestParam("id") Long id) {
		try {
			return roleService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询所有
	 *
	 * @return
	 */
	@RequestMapping("/findAll")
	public String findAll(Model model) {
		model.addAttribute("rolesList", roleService.findAll());
		return "page/role";
	}

	/**
	 * 构建一颗ZTree树 以JSON格式返回数据库中角色表-权限表的所有数据 数据结构类似： [{id: xx, name: 父节点, parent:
	 * true}, {id: xx, name: 子节点, parent: false},{子节点....}, {父节点....}, {子节点...}]
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getZTreeForRolePermissionAll")
	public List<TreeEntity> getZTreeForRolePermissionAll(@RequestParam("id") Long id) {
		try {
			List<TreeEntity> treeList = new ArrayList<TreeEntity>();
			List<Permission> dataList = roleService.findPermissionByRoleId(id);

			// 根据id查询此角色的数据，即为父节点的数据
			Role role = roleService.findById(id);
			treeList.add(new TreeEntity(role.getId(), role.getDescription(), true, role.getPid()));

			// 遍历设置子节点数据
			for (Permission permission : dataList) {
				treeList.add(new TreeEntity(permission.getId(), permission.getDescription(), false, role.getId()));
			}
			return treeList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据角id查询其所关联的权限根据角色id查询当前role-permission表中关联的数据
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findPermissionByRoleId")
	public List<Permission> findPermissionByRoleId(@RequestParam("id") Long id) {
		try {
			return roleService.findRolePermissionByRoleId(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 更新角色-权限信息，包含以下参数：
	 * <p>
	 * id 当前角色的id ids 当前角色-权限的id集合 parents 当前角色-权限的是否是父节点的parent集合
	 *
	 * <p>
	 * 之前在User层面上，我们要对其进行角色的更新，也就是说在User用户层面上更新权限，只需要维护用户-角色表数据。
	 * 而在角色层面上，只需要维护角色-权限表数据。
	 * <p>
	 * 误区：始终要记得，同一个角色拥有不同权限的情况是不存在的，如果两个用户拥有不同的权限，那么他们一定拥有了不同的角色
	 *
	 * @return
	 * @Param dataMap 以上参数数据的Map集合
	 *
	 *        <p>
	 *        为什么要用Map接收？
	 *        如果想要传递对象(如数组)参数，ajax就必须发送post请求，而post请求传递对象参数就要用JSON.stringify()格式化数据为JSON字符串才能传递；
	 *        一旦使用了JSON.stringify()格式化数据，传递的就是JSON字符串，后端必须使用String类型接收，而我们传递的数据中包含了普通变量、数组等多种数据，所以使用使用Map接收，并指定泛型是String类型
	 *
	 *        <p>
	 *        前端传递进来的参数在Map中封装的数据结构类似： {0:{key: id, value: {....}}, 1:{key: ids,
	 *        value: {...}}, 2:{key: parents, value: {...}}}
	 */
	@ResponseBody
	@RequestMapping(value = "/updateRolesPermissions", method = RequestMethod.POST)
	public Map updateRolesPermissions(@RequestBody Map<String, Object> dataMap) {
		Map map = new HashMap<>();
		try {
			Long id = Long.valueOf((String) dataMap.get("id")); // 当前操作角色的id
			ArrayList ids = (ArrayList) dataMap.get("ids"); // 当前角色更新的权限id的集合体
			ArrayList parents = (ArrayList) dataMap.get("parents"); // 当前更新的角色权限parent信息

			// 更新角色的权限关系即需要维护角色-权限表，前端传来了什么数据？ 1、角色id；2、被选中的权限Id集合。
			// 如何更新角色的权限？我们常会想到，根据表的主键update呀，但是，因为页面上展示的数据是后端构建的ZTree实体类JSON数据，其中并不包含表的主键值
			// 所以，这里就采取一种比较极端的方式，先删除此角色所有关联的权限id，再依次关联此角色当前更新的权限id

			// 先删除此角色关联的所有权限id
			roleService.deleteAllRolePermissions(id);

			// 再依次关联此角色更新的权限id
			for (int i = 0; i < ids.size(); i++) {
				if (!(boolean) parents.get(i)) {
					// 不是父节点才进行关联，因为父节点是角色，子节点才是权限
					roleService.correlationPermissions(id, Long.valueOf(String.valueOf(ids.get(i))));
				}
			}
			System.out.println(dataMap);
			map.put("message", "更新数据成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "发生未知错误");
			return map;
		}
	}

	/**
	 * 添加角色 --构建一棵ZTree树
	 * <p>
	 * 这棵ZTree树仅显示当前角色所关联的权限，不需要得到所有的权限信息
	 * <p>
	 * 返回的JSON数据结构类似： [{id: xx, name: 父节点, parent: true}, {id: xx, name: 子节点,
	 * parent: false}, {子节点...}, {子节点}]
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getZTreeForAllRoles")
	public List<TreeEntity> getZTreeForAllRoles() {
		try {
			List<TreeEntity> treeList = new ArrayList<TreeEntity>();
			List<Role> dataList = roleService.findAll();

			for (Role role : dataList) {
				if (role.getPid() != null) {
					if (role.getPid() == 0) {
						// 说明是父节点
						treeList.add(new TreeEntity(role.getId(), role.getDescription(), true, role.getPid()));
					} else {
						// 说明是子节点
						treeList.add(new TreeEntity(role.getId(), role.getDescription(), false, role.getPid()));
					}
				}
			}
			return treeList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 创建角色
	 *
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/create")
	public Map create(@RequestBody Role role) {
		Map map = new HashMap<>();
		try {
			roleService.create(role);
			map.put("message", "创建角色成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "创建角色失败");
			return map;
		}
	}

	/**
	 * 更新角色状态或者内容
	 * 
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public Map updateRoleStatus(@RequestBody Role role) {

		Map map = new HashMap<>();
		try {
			if (role.getDescription() != null) {
				// 说明更新了角色的描述信息，那就更新用户表中显示的角色名称
				roleService.updateUserRole_Id(role);
			}
			roleService.update(role);
			map.put("message", "更新角色成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "更新角色失败");
			return map;
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/delete")
	public Map deleteRoleById(Long id) {
		Map map=new HashMap<>();
		try {
			Role role=roleService.findById(id);
			//如果role是父节点
			if(role.getPid()==0) {
				//删除该角色
				roleService.delete(id);
				
				List<Role> chihldRoles=roleService.findRolesByPid(id);
				//删除子节点角色
				for (Role role2 : chihldRoles) {
					roleService.delete(role2.getId());
					roleService.deleteAllRolePermissions(role2.getId());
				}
			}else {
				//删除该角色
				roleService.delete(id);
				//删除该角色所有权限
				roleService.deleteAllRolePermissions(id);
			}
			//可以给该角色的用户一个默认值
			
			
			
			
			roleService.delete(id);
			map.put("message", "删除角色成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "删除角色失败");
			return map;
		}
	}

}
