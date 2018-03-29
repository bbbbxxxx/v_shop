
package com.dianshu.permission.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.dianshu.common.dao.PermissionMapper;
import com.dianshu.common.dao.RolePermissionMapper;
import com.dianshu.common.model.Permission;
import com.dianshu.common.model.RolePermission;
import com.dianshu.core.mybatis.BaseMybatisDao;
import com.dianshu.core.mybatis.page.Pagination;
import com.dianshu.permission.bo.PermissionBo;
import com.dianshu.permission.service.PermissionService;
@Service
@SuppressWarnings("unchecked")
public class PermissionServiceImpl extends BaseMybatisDao<PermissionMapper> implements PermissionService{

	@Autowired
	PermissionMapper permissionMapper;
	@Autowired
	RolePermissionMapper rolePermissionMapper;
	@Override
	public Set<String> findPermissionByUserId(Long userId) {
		// TODO Auto-generated method stub
		return permissionMapper.findPermissionByUserId(userId);
	}
	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int insertSelective(Permission record) {
		//每添加一个权限，都往【系统管理员 	888888】里添加一次。保证系统管理员有最大的权限
		executePermission(new Long(1), String.valueOf(record.getId()));
		// 添加
		return permissionMapper.insertSelective(record);
	
	}
	private Map<String, Object> executePermission(Long roleId, String ids) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String,Object>();
		int count = 0;
		try {
			//如果ids,permission 的id 有值，那么就添加。没值象征着：把这个角色（roleId）所有权限取消。
			if(StringUtils.isNotBlank(ids)){
				String[] idArray = null;
				
				//这里有的人习惯，直接ids.split(",") 都可以，我习惯这么写。清楚明了。
				if(StringUtils.contains(ids, ",")){
					idArray = ids.split(",");
				}else{
					idArray = new String[]{ids};
				}
				//添加新的。
				for (String pid : idArray) {
					//这里严谨点可以判断，也可以不判断。这个{@link StringUtils 我是重写了的} 
					if(StringUtils.isNotBlank(pid)){
						RolePermission entity = new RolePermission(roleId,new Long(pid));
						count += rolePermissionMapper.insertSelective(entity);
					}
				}
			}
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作失败，请重试！");
		}
		//清空拥有角色Id为：roleId 的用户权限已加载数据，让权限数据重新加载
//		List<Long> userIds = userRoleMapper.findUserIdByRoleId(roleId);
//		
//		TokenManager.clearUserAuthByUserId(userIds);
		resultMap.put("count", count);
		return resultMap;
	}
	@Override
	public Permission selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int updateByPrimaryKeySelective(Permission record) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int updateByPrimaryKey(Permission record) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Map<String, Object> deletePermissionById(String ids) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			int count = 0;
			String ArrayIds[] = new String[] {};
			if(StringUtils.contains(ids, ",")) {
				ArrayIds=ids.split(",");
			}else {
				ArrayIds=new String[] {ids};
			}
			for (String id : ArrayIds) {
			
				count+=permissionMapper.deleteByPrimaryKey(new Long(id));
			}
			
		}catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "操作失败");
		}
		return resultMap;
	}

	@Override
	public Pagination<Permission> findPage(Map<String, Object> resultMap, Integer pageNo, Integer pageSize) {
		return super.findPage(resultMap, pageNo, pageSize);
	}
	@Override
	public List<PermissionBo> selectPermissionById(Long id) {
		// TODO Auto-generated method stub
		return permissionMapper.selectPermissionById(id);
	}
	@Override
	public Map<String, Object> addPermission2Role(Long roleId, String ids) {
		return executePermission(roleId, ids);
	}
	
	@Override
	public Map<String, Object> deleteByRids(String roleIds) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap.put("roleIds", roleIds);
			rolePermissionMapper.deleteByRids(resultMap);
			resultMap.put("status", 200);
			resultMap.put("message", "删除成功");
		}catch (Exception e) {
			// TODO: handle exception
			resultMap.put("status", 500);
			resultMap.put("message", "删除失败");
		}
		return resultMap;
	}

}
