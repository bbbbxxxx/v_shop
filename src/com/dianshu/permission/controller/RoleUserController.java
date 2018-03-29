package com.dianshu.permission.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dianshu.common.controller.BaseController;
import com.dianshu.core.mybatis.page.Pagination;
import com.dianshu.permission.bo.RoleBo;
import com.dianshu.permission.bo.UserRoleAllocationBo;
import com.dianshu.user.service.UserService;
@Controller
@RequestMapping("role")
@Scope(value="prototype")

public class RoleUserController extends BaseController{
	@Autowired
	UserService userService;
	@RequestMapping(value="allocation")
	public ModelAndView allocation(String findContent,ModelMap modelMap,Integer pageNo) {
		modelMap.put("findContent", findContent);
		Pagination<UserRoleAllocationBo> boPage = userService.findUserAndRole(modelMap,pageNo,pageSize);
		modelMap.put("page", boPage);
		return new ModelAndView("role/allocation");
	}
	
	@RequestMapping(value="selectRoleByUserId")
	@ResponseBody
	public List<RoleBo> selectRoleByUserId(Long id){
		List<RoleBo> bo = userService.selectRoleByUserId(id);
		System.out.println(id);
		System.out.println(bo.get(0));
		return bo;
	}
	
	@RequestMapping(value="clearRoleByUserIds")
	@ResponseBody
	public Map<String, Object> clearRoleByUserIds(String userIds){
		System.out.println(userIds);
		return userService.deleteRoleByUserIds(userIds);
	}
	
	@RequestMapping(value="addRole2User")
	@ResponseBody
	public Map<String,Object> addRole2User(Long userId,String ids){
		return userService.addRole2User(userId,ids);
	}
}
