package com.dianshu.permission.controller;

import java.util.List;
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
import com.dianshu.common.model.Role;
import com.dianshu.core.mybatis.page.Pagination;
import com.dianshu.permission.service.RoleService;
import com.dianshu.user.manager.UserManager;


@Controller
@RequestMapping("role")
@Scope(value="prototype")
public class RoleController extends BaseController{
	@Autowired
	RoleService roleService;
	@RequestMapping(value="index")
	public ModelAndView index(String findContent,ModelMap modelMap,Integer pageNo) {
		modelMap.put("findContent", findContent);
		Pagination<Role> role = roleService.findPage(modelMap,pageNo,pageSize);
		return new ModelAndView("role/index","page",role);
	}
	
	
	@RequestMapping(value="addRole",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRole(Role role){
		System.out.println(role.getName());
		try {
			int count = roleService.insertSelective(role);
			resultMap.put("status", 200);
			resultMap.put("successCount", count);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "添加失败，请刷新后再试！");
			System.out.printf("添加角色报错。source[%s]",role.toString());
		}
		return resultMap;
	}
	
	@RequestMapping(value="mypermission",method=RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("role/mypermission");
	}
	
	@RequestMapping(value="getPermissionTree",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getPermissionTree(){
		
		List<Role> role = roleService.findNowAllPermission();
		System.out.println(role);
		
		List<Map<String, Object>> date = UserManager.toTreeData(role);
		return date;
	}
	
	@RequestMapping(value="deleteRoleById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRoleById(String ids){
		
		return roleService.deleteRoleById(ids);
	}

}
