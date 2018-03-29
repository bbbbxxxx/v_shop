package com.dianshu.permission.controller;

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
import com.dianshu.common.model.Permission;
import com.dianshu.core.mybatis.page.Pagination;
import com.dianshu.permission.service.PermissionService;

@Controller
@RequestMapping("permission")
@Scope(value="prototype")
public class PermissionController extends BaseController{
	@Autowired
	PermissionService permissionService;
	@RequestMapping(value="index")
	public ModelAndView index(String findContent,ModelMap modelMap,Integer pageNo){
		modelMap.put("findContent", findContent);
		Pagination<Permission> permissions = permissionService.findPage(modelMap,pageNo,pageSize);
		return new ModelAndView("permission/index","page",permissions);
	}
	
	@RequestMapping(value="deletePermissionById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePermissionById(String ids){
		return permissionService.deletePermissionById(ids);
	}
	
	@RequestMapping(value="addPermission",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPermission(Permission permission){
		try {
			permissionService.insertSelective(permission);
			resultMap.put("status", 200);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "添加失败");
		}
		return resultMap;
	}
}
