package com.dianshu.core.shiro.token.manager;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.dianshu.common.model.User;
import com.dianshu.common.utils.SpringContextUtil;
import com.dianshu.core.shiro.token.SampleRealm;
import com.dianshu.core.shiro.token.ShiroToken;

public class TokenManager {
	//用户登录管理

		
		public static User getToken() {
			User token = (User) SecurityUtils.getSubject().getPrincipal();
			
			return token;
		}
		
		public static User login(User user) {
			ShiroToken token = new ShiroToken(user.getName(), user.getPswd());
			try {
				SecurityUtils.getSubject().login(token);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return getToken();
		}
		
		/**
		 * 退出登录
		 */
		public static void logout() {
			SecurityUtils.getSubject().logout();
		}
		
//		/**
//		 * 清空当前用户权限信息。
//		 * 目的：为了在判断权限的时候，再次会再次 <code>doGetAuthorizationInfo(...)  </code>方法。
//		 * ps：	当然你可以手动调用  <code> doGetAuthorizationInfo(...)  </code>方法。
//		 * 		这里只是说明下这个逻辑，当你清空了权限，<code> doGetAuthorizationInfo(...)  </code>就会被再次调用。
//		 */
//		public static void clearNowUserAuth(){
//			/**
//			 * 这里需要获取到shrio.xml 配置文件中，对Realm的实例化对象。才能调用到 Realm 父类的方法。
//			 */
//			/**
//			 * 获取当前系统的Realm的实例化对象，方法一（通过 @link org.apache.shiro.web.mgt.DefaultWebSecurityManager 或者它的实现子类的{Collection<Realm> getRealms()}方法获取）。
//			 * 获取到的时候是一个集合。Collection<Realm> 
//				RealmSecurityManager securityManager =
//			    			(RealmSecurityManager) SecurityUtils.getSecurityManager();
//			  	SampleRealm realm = (SampleRealm)securityManager.getRealms().iterator().next();
//			 */
//			/**
//			 * 方法二、通过ApplicationContext 从Spring容器里获取实列化对象。
//			 */
//			realm.clearCachedAuthorizationInfo();
//			/**
//			 * 当然还有很多直接或者间接的方法，此处不纠结。
//			 */
//		}
		
}
