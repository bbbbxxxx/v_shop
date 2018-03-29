package com.dianshu.common.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dianshu.common.model.Role;

public interface RoleMapper {
	int deleteByPrimaryKey(Long roleId);
	int insertSelective(Role record);
	int updateByPrimaryKeySelective(Role role);
	
	Role selectByPrimaryKey(Long id);
	Role selectByName(String name);
	Set<String> findRoleByUserId(Long id);

	List<Role> findNowAllPermission(Map<String, Object> map);
	
	void initData();
}
