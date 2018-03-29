package com.dianshu.user.controller;
import java.sql.SQLException;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dianshu.common.controller.BaseController;
import com.dianshu.common.dao.RoleMapper;
import com.dianshu.common.model.Permission;
import com.dianshu.common.model.User;
import com.dianshu.common.utils.StringUtils;
import com.dianshu.core.shiro.token.manager.TokenManager;
import com.dianshu.permission.service.PermissionService;
import com.dianshu.permission.service.RoleService;
import com.dianshu.user.manager.UserManager;
import com.dianshu.user.service.UserService;

@Controller
@Scope(value="prototype")
@RequestMapping("u")
public class UserLoginController extends BaseController{
	@Autowired
	UserService userService;
	/**
	 * 登录跳转
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.GET)
	public ModelAndView login(){
		
		return new ModelAndView("user/login");
	}
	
	@RequestMapping(value="submitLogin",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitLogin (User user,HttpServletRequest request,HttpSession session){
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPswd());
		try{

			    subject.login(token);
			    session.setAttribute("token", TokenManager.getToken());
				resultMap.put("status", 200);
				resultMap.put("message", "登录成功");
			    String url = request.getContextPath() + "/user/index";
			    resultMap.put("back_url", url);
			
		}catch (Exception e) {
			// TODO: handle exception
		    e.printStackTrace();
			resultMap.put("status", 500);
			resultMap.put("message","账号密码错误");
		}
		return resultMap;
	}
	
	@RequestMapping(value="logout",method =RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> logout(){
		try {
			TokenManager.logout();
			resultMap.put("status", 200);
		} catch (Exception e) {
			resultMap.put("status", 500);
			logger.error("errorMessage:" + e.getMessage());
			System.out.printf("退出出现错误，%s。", e.getMessage());
		}
		return resultMap;
	}
}
