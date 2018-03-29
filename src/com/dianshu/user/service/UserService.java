package com.dianshu.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;

import com.dianshu.common.model.User;
import com.dianshu.core.mybatis.page.Pagination;
import com.dianshu.permission.bo.RoleBo;
import com.dianshu.permission.bo.UserRoleAllocationBo;


public interface UserService {
	public User login(String username,String psed);
	
	/**
	 * 
	* 方法描述: updateByPrimaryKeySelective 
	* 只是更新新的model中不为空的字段。
	* @param @param userId
	* @param @return 
	* @return int
	 */
	int updateByPrimaryKeySelective(User entity);
	
	int insertSelective(User user);
	
	int deleteByPrimaryKey(Long userId);
	
	User selectByPrimaryKey(Long userId);
	
	User findUserByName(String name);
	
	Map<String, Object> deleteUserById(String ids);
	
	
	Pagination<UserRoleAllocationBo> findUserAndRole(ModelMap modelMap,
			Integer pageNo, Integer pageSize);
	
	Pagination<User> findByPage(Map<String, Object> resultMap, Integer pageNo,
			Integer pageSize);
	
	List<RoleBo> selectRoleByUserId(Long id);

	Map<String, Object> addRole2User(Long userId, String ids);

	Map<String, Object> deleteRoleByUserIds(String userIds);
}
