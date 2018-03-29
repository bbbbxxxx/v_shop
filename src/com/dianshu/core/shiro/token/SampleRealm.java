package com.dianshu.core.shiro.token;

import java.util.Date;
import java.util.Set;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.beans.factory.annotation.Autowired;

import com.dianshu.common.model.User;
import com.dianshu.core.shiro.token.manager.TokenManager;
import com.dianshu.permission.service.PermissionService;
import com.dianshu.permission.service.RoleService;
import com.dianshu.user.service.UserService;

public class SampleRealm extends AuthorizingRealm{
	@Autowired
	UserService userservice;
	@Autowired
	PermissionService permissionService;
	@Autowired
	RoleService roleService;

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		super.setName("SampleRealm");
	}
	
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		User user = (User) principal.getPrimaryPrincipal();
		System.out.println(131);
		System.out.println(user.getId());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//根据用户ID查询角色（role），放入到Authorization里。
		Set<String> role=roleService.findRoleByUserId(user.getId());
		System.out.println(role.toString());
		info.setRoles(role);
		//根据用户ID查询权限（permission），放入到Authorization里。
		Set<String> permissions = permissionService.findPermissionByUserId(user.getId());
		System.out.println(permissions.toString());
		info.setStringPermissions(permissions);
		 return info;  	
	}

	//认证信息
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenToken) 
			throws AuthenticationException {
		
		String username = authenToken.getPrincipal().toString();
		String password = new String ((char[]) authenToken.getCredentials());

		User user = userservice.login(username,password);
		System.out.println(password);
		if(null == user) {
			System.out.println(12313);
			throw new AccountException("用户名密码不正确");
		}else {		
			Date date = new Date();
			user.setLastLoginTime(date);
			userservice.updateByPrimaryKeySelective(user);
		}
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPswd(),getName());
		return simpleAuthenticationInfo;
	}

	 /**
     * 清空当前用户权限信息
     */
	public  void clearCachedAuthorizationInfo() {
		System.out.println("123");
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	/**
	 * 指定principalCollection 清除
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
}
