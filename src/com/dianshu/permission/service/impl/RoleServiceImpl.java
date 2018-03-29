package com.dianshu.permission.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianshu.common.dao.RoleMapper;
import com.dianshu.common.model.Role;
import com.dianshu.core.mybatis.BaseMybatisDao;
import com.dianshu.core.mybatis.page.Pagination;
import com.dianshu.core.shiro.token.manager.TokenManager;
import com.dianshu.permission.bo.RolePermissionAllocationBo;
import com.dianshu.permission.service.RoleService;

@Service
@SuppressWarnings("unchecked")
public class RoleServiceImpl extends BaseMybatisDao<RoleMapper> implements RoleService{

	@Autowired
	RoleMapper roleMapper;
	@Override	
	public Set<String> findRoleByUserId(Long userId) {
		
		return roleMapper.findRoleByUserId(userId);
	}
	
	@Override
	public Pagination<Role> findPage(Map<String, Object> resultMap, Integer pageNo, Integer pageSize) {
		return super.findPage(resultMap, pageNo, pageSize);
	}

	@Override
	public int insertSelective(Role role) {
		return roleMapper.insertSelective(role);
	}

	@Override
	public Role selectByName(String name) {
		return roleMapper.selectByName(name);
	}

	@Override
	public List<Role> findNowAllPermission() {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userId", TokenManager.getToken().getId());
		return roleMapper.findNowAllPermission(map);
	}

	@Override
	public Map<String, Object> deleteRoleById(String ids) {
		Map<String, Object> resultMap= new HashMap<String,Object>();
		try {
			int count = 0;
			String ArrayIds[] = new String[] {};
			if(StringUtils.contains(ids, ",")) {
				ArrayIds=ids.split(",");
			}else {
				ArrayIds= new String [] {ids};
			}
			for (String id : ArrayIds) {
				count+=roleMapper.deleteByPrimaryKey(new Long(id));
			}
			resultMap.put("status", 200);
			resultMap.put("message", "成功删除"+count+"条");
		}catch (Exception e) {
			resultMap.put("status", 200);
			resultMap.put("message", "删除失败");
		}
		
		return resultMap;
	}

	@Override
	public Pagination<RolePermissionAllocationBo> findRoleAndPermissionPage(
			Map<String, Object> resultMap, Integer pageNo, Integer pageSize) {
		return super.findPage("findRoleAndPermission", "findCount", resultMap, pageNo, pageSize);
	}

}
