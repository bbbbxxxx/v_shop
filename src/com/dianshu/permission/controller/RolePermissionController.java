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
import com.dianshu.core.mybatis.page.Pagination;
import com.dianshu.permission.bo.PermissionBo;
import com.dianshu.permission.bo.RolePermissionAllocationBo;
import com.dianshu.permission.service.PermissionService;
import com.dianshu.permission.service.RoleService;

@Controller
@RequestMapping("permission")
@Scope(value="prototype")
public class RolePermissionController extends BaseController{
	@Autowired
	PermissionService permissionService;
	
	
	@Autowired
	RoleService roleService;
	@RequestMapping(value="allocation")
	public ModelAndView allocation(String findContent,ModelMap modelMap,Integer pageNo) {
		modelMap.put("findContent", findContent);
		Pagination<RolePermissionAllocationBo> boPage = roleService.findRoleAndPermissionPage(modelMap,pageNo,pageSize);
		modelMap.put("page", boPage);
		return new ModelAndView("permission/allocation");
	}
	
	@RequestMapping(value="clearPermissionByRoleIds",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> clearPermissionByRoleIds(String roleIds){
		return permissionService.deleteByRids(roleIds);
	}
	
	@RequestMapping(value="selectPermissionById",method=RequestMethod.POST)
	@ResponseBody
	public List<PermissionBo> selectPermissionById(Long id){
		List<PermissionBo> permissionBos = permissionService.selectPermissionById(id);
		return permissionBos;
	}
	@RequestMapping(value="addPermission2Role",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPermission2Role(Long roleId,String ids){
		return permissionService.addPermission2Role(roleId, ids);
	}
}

