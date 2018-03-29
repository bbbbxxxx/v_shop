package com.dianshu.common.dao;

import java.util.List;
import java.util.Set;

import com.dianshu.common.model.Permission;
import com.dianshu.permission.bo.PermissionBo;

public interface PermissionMapper {
	int insertSelective(Permission record);
    int deleteByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(Permission record);
    
    Permission selectByPrimaryKey(Long id);

    List<PermissionBo> selectPermissionById(Long id);
	//根据用户ID获取权限的Set集合
	Set<String> findPermissionByUserId(Long id);

}
