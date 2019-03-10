package com.zhangyao.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhangyao.entity.Permission;
import com.zhangyao.entity.Role;
import com.zhangyao.entity.User;
import com.zhangyao.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhangyao
 * @date Mar 7, 2019
 */

/*
 * Shiro从Realm中获取安全数据，我们可以自定义多个Realm实现，但都要在SecurityManager中定义。
 * 一般我们自定义实现的Realm继承AuthorizingRealm（授权）即可，它继承了AuthenticatingRealm（身份验证）；
 * 所以自定义Realm一般存在两个最主要的功能：1.身份验证；2.权限校验。
 */
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	/**
	 * 权限校验
	 * 用户和角色间是一对多的关系；角色和权限是多对多的关系；权限和资源是多对多的关系。
	 * 但是在我们设计的表：Shiro实现权限管理系统之表结构的设计中，我并没有设置单独设置资源表，而是仅用了权限表。
	 * 当然你可以再写一个资源表(Resource)，建立权限和资源间的关系，这样权限管理能精确到对每个按钮的管理。
	 * @param principals
	 * @return
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("权限校验--执行了doGetAuthorizationInfo...");

		String username = (String) principals.getPrimaryPrincipal();

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 注意这里的setRoles和setStringPermissions需要的都是一个Set<String>类型参数
		Set<String> role = new HashSet<String>();
		List<Role> roles = userService.findRoles(username);
		for (Role r : roles) {
			role.add(r.getRole());
		}
		authorizationInfo.setRoles(role);
		Set<String> permission = new HashSet<String>();
		List<Permission> permissions = userService.findPermissions(username);
		for (Permission p : permissions) {
			permission.add(p.getPermission());
		}
		authorizationInfo.setStringPermissions(permission);

		return authorizationInfo;
	}

	/**
	 * 身份校验
	 * 
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("身份校验--执行了goGetAuthenticationInfo...");

		// 身份验证中两个重要的参数：principals(一般是用户名),credentialsr(一般是密码)
		String username = (String) token.getPrincipal();
		Object credentials = token.getCredentials();
		System.out.println("userName:" + username + "credentials:" + credentials);
		User user = userService.findByName(username);

		if (user == null) {
			throw new UnknownAccountException(); // 没有找到账号
		}

		if (Boolean.TRUE.equals(user.getLocked())) {
			throw new LockedAccountException(); // 账号锁定
		}

		// 交给AuthenticationRealm使用CredentialsMatcher进行密码匹配
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), // 用户名
				user.getPassword(), // 密码
				ByteSource.Util.bytes(user.getCredentialsSalt()), // salt=username+salt
				getName() // realm name
		);

		return authenticationInfo;
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}
}