package com.dianshu.user.controller;

import java.util.Map;

import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dianshu.common.controller.BaseController;
import com.dianshu.common.model.User;
import com.dianshu.core.shiro.token.manager.TokenManager;
import com.dianshu.user.manager.UserManager;
import com.dianshu.user.service.UserService;

import net.sf.json.JSONObject;

@Controller
@Scope(value="prototype")
@RequestMapping("user")
public class UserIndexController extends BaseController{
	@Autowired
	UserService userService;
	
	@RequestMapping(value="index",method=RequestMethod.GET)
	public ModelAndView userIndex(){
		
		return new ModelAndView("user/index");
	}
	/**
	 * 偷懒一下，通用页面跳转
	 * @param page
	 * @return
	 */
	@RequestMapping(value="{page}",method=RequestMethod.GET)
	public ModelAndView toPage(@PathVariable("page")String page){
		return new ModelAndView(String.format("user/%s", page));
	}
	
	@RequestMapping(value="updatePswd",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updatePswd(String pswd,String newPswd){
		//根据当前登录的用户帐号 + 老密码，查询。
		String name = TokenManager.getToken().getName();
				pswd = UserManager.md5Pswd(name, pswd);
		User	user = userService.login(name, pswd);
		
		if("admin".equals(name)){
			resultMap.put("status", 300);
			resultMap.put("message", "管理员不准修改密码。");
			return resultMap;
		}
		
		if(null == user){
			resultMap.put("status", 300);
			resultMap.put("message", "密码不正确！");
		}else{
			user.setPswd(newPswd);
			//加工密码
			user = UserManager.md5Pswd(user);
			//修改密码
			userService.updateByPrimaryKeySelective(user);
			resultMap.put("status", 200);
			resultMap.put("message", "修改成功!");
			//重新登录一次
			TokenManager.login(user);
		}
		return resultMap;
	}
	/**
	 * 个人资料修改
	 * @return
	 */
	@RequestMapping(value="updateSelf",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateSelf(User entity){
		try {
			userService.updateByPrimaryKeySelective(entity);
			resultMap.put("status", 200);
			resultMap.put("message", "修改成功!");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "修改失败!");
			System.out.printf("修改个人资料出错。[%s]", JSONObject.fromObject(entity).toString());
		}
		return resultMap;
	}
}
