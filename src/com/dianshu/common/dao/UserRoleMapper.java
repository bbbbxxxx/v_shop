package com.dianshu.common.dao;

import java.util.List;
import java.util.Map;

import com.dianshu.common.model.UserRole;

public interface UserRoleMapper {
	 int insert(UserRole record);

	    int insertSelective(UserRole record);

		int deleteByUserId(Long id);

		int deleteRoleByUserIds(Map<String, Object> resultMap);
		
		
		List<Long> findUserIdByRoleId(Long id);
}
