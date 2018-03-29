package com.dianshu.permission.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.dianshu.common.model.Role;
import com.dianshu.core.mybatis.page.Pagination;
import com.dianshu.permission.bo.RolePermissionAllocationBo;

@Service
public interface RoleService {
	Set<String> findRoleByUserId(Long userId);
	Pagination<Role> findPage(Map<String, Object> resultMap,Integer pageNo,Integer pageSize);
	
	
	Pagination<RolePermissionAllocationBo> findRoleAndPermissionPage(
			Map<String, Object> resultMap, Integer pageNo, Integer pageSize);
	
	
	int insertSelective(Role role);
	Map<String, Object> deleteRoleById(String ids);
	Role selectByName(String name);
	List<Role> findNowAllPermission();
}
