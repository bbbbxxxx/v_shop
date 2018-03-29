package com.dianshu.common.dao;

import java.util.List;
import java.util.Map;

import com.dianshu.common.model.RolePermission;

public interface RolePermissionMapper {
	   int insert(RolePermission record);

	    int insertSelective(RolePermission record);
	    
		int deleteByPid(Long id);
		
		int delete(RolePermission entity);

		int deleteByRids(Map<String,Object> resultMap);


		List<RolePermission> findRolePermissionByPid(Long id);
		
		List<RolePermission> findRolePermissionByRid(Long id);
		
		List<RolePermission> find(RolePermission entity);
}
		
	
