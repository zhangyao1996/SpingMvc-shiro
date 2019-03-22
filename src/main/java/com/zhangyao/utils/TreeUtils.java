package com.zhangyao.utils;

import java.util.ArrayList;
import java.util.List;

import com.zhangyao.entity.Tree;

/**
 * @author zhangyao
 * @version Mar 18, 2019 11:16:57 AM
 */
public class TreeUtils {

	// 获得递归树，方法一
	public static <T> List<Tree<T>> build(List<Tree<T>> treeList) {
		if (treeList == null) {
			return null;
		}

		List<Tree<T>> trees = new ArrayList<>();
		for (Tree<T> children : treeList) {
			Long pid = children.getpId();
			for (Tree<T> parent : treeList) {
				Long id = parent.getId();
				if (id != null && id.equals(pid)) {
					// 说明该节点是children的父节点
					//children.setHasParent(true);
					//parent.setHasChildren(true);
					parent.getChildren().add(children);
				}
			}
		}
		for (Tree<T> tree : treeList) {
			Long pid = tree.getpId();
			if (pid == null || pid.equals(0L)) {
				// 获得父节点
				trees.add(tree);
			}
		}

		return trees;
	}

	// 方法二
	public static <T> List<Tree<T>> build2(List<Tree<T>> treeList){
		List<Tree<T>> trees=new ArrayList<>();
		//先得到所有一级菜单
		for (Tree<T> tree : treeList) {
			if(tree.getpId().equals(0L)||tree.getpId()==null) {
				trees.add(tree);
			}
		}
		
		//一级菜单之后开始递归子菜单
		for (Tree<T> tree : trees) {
			tree.setChildren(getChild(tree.getId(), treeList));
		}
		return trees;
	} 
	
	private  static  <T> List<Tree<T>> getChild(Long id, List<Tree<T>> treeList) {
 
        List<Tree<T>> childList = new ArrayList<>();//存放直接子菜单
		/**
		 * 开始遍历二级菜单以及它的直接子菜单
		 */
        for (Tree<T> tree : treeList) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (tree.getpId()!=null||tree.getpId()!=0L) {
                if (id.equals(tree.getpId())) {//尽量让id 在前面，因为他不会为空（数据库设计为主键），parentId 不一定都有值。
                    childList.add(tree);//相等的话说明这些使它（id）的直接子节点,加入childList
                }
            }
        }//这时候已经将一级菜单以及一级的直接子孩子遍历出来了。
 
		/**
		 * 把子菜单的直接子菜单再循环一遍 这时候就是从Menu的直接子菜单中获得需要遍历的菜单也就是childList
		 */
		/*
        for (Menu menu : childList) {
            if (StringUtils.isBlank(menu.getUrl())) {//这个判断的意思是 如果url 不为空说明是最后一个节点，为空说明他不是最后一个子节点，这时候就需要去遍历
                menu.setChildMenus(getChild(menu.getId(), rootMenu));//递归
            }
        }*/
        for (Tree<T> tree : childList) {
			tree.setChildren(getChild(tree.getId(), treeList));
		}
        /*
        if (childList.size() == 0) {
        	// 递归退出条件（走到这里childList 大小等于0 说明该节点就是最后一个）
            return null;
        }*/
        return childList;
    }
	

}
