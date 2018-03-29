package com.dianshu.user.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dianshu.common.controller.BaseController;
import com.dianshu.common.model.User;
import com.dianshu.core.mybatis.page.Pagination;
import com.dianshu.user.manager.UserManager;
import com.dianshu.user.service.UserService;
@Controller
@Scope(value="prototype")
@RequestMapping("member")

public class UserMemberController extends BaseController{
	@Autowired
	UserService userService;

	
	@RequestMapping(value="addMember",method=RequestMethod.GET)
	public ModelAndView addMember(){
		
		return new ModelAndView("member/addMember");
	}
	
	@RequestMapping(value="list")
	public ModelAndView list(ModelMap map,Integer pageNo,String findContent){
		map.put("findContent", findContent);
		Pagination<User> page = userService.findByPage(map,pageNo,pageSize);
		map.put("page", page);
		return new ModelAndView("member/list");
	}
	@RequestMapping(value="addMemberSub",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMember(User user){
		Date date = new Date();
		if(null!=userService.findUserByName(user.getName())) {
			resultMap.put("status", 400);
			resultMap.put("message", "用户已存在");
			
			return resultMap;
		}
		user.setCreateTime(date);
		System.out.println(user.getName());
		System.out.println(user.getPswd());
		user = UserManager.md5Pswd(user);
		System.out.println(user.getName());
		System.out.println(user.getPswd());
		userService.insertSelective(user);
		resultMap.put("message", "添加成功！");
		resultMap.put("status", 200);
		return resultMap;
	}
	
	@RequestMapping(value="deleteUserById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUserById(String ids){
		return userService.deleteUserById(ids);
	}
}
