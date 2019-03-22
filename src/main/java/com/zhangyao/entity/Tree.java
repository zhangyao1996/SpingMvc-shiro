package com.zhangyao.entity;
/**
* @author zhangyao
* @version Mar 18, 2019 10:59:31 AM
*/

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Tree<T> {
	private Long id;
	private Long pId;
	private Boolean hasChildren;//可以没有
	private Boolean hasParent;//可以没有
	private String name;
	private List<Tree<T>> children =new ArrayList<>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public Boolean getHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	public Boolean getHasParent() {
		return hasParent;
	}
	public void setHasParent(Boolean hasParent) {
		this.hasParent = hasParent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Tree<T>> getChildren() {
		return children;
	}
	public void setChildren(List<Tree<T>> children) {
		this.children = children;
	}
	@Override
	public String toString() {
		return "Tree [id=" + id + ", pId=" + pId + ", hasChildren=" + hasChildren + ", hasParent=" + hasParent
				+ ", name=" + name + ", children=" + children + "]";
	}
	
	
	
	
}

