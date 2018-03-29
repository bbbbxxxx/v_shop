package com.dianshu.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.dianshu.common.dao.UserMapper;
import com.dianshu.common.dao.UserRoleMapper;
import com.dianshu.common.model.User;
import com.dianshu.common.model.UserRole;
import com.dianshu.core.mybatis.BaseMybatisDao;
import com.dianshu.core.mybatis.page.Pagination;
import com.dianshu.permission.bo.RoleBo;
import com.dianshu.permission.bo.UserRoleAllocationBo;
import com.dianshu.user.service.UserService;

@Service
@SuppressWarnings("unchecked")
public class UserServiceImpl  extends BaseMybatisDao<UserMapper> implements UserService{

	@Autowired
	UserMapper usermapper;
	@Autowired
	UserRoleMapper userRoleMapper;
	@Override
	public User login(String username,String pswd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", username);
		map.put("pswd", pswd);
		
		return usermapper.login(map);
	}
	
	/**
	 * 
	* 方法描述: updateByPrimaryKeySelective 
	* 只是更新新的model中不为空的字段。
	* @param @param userId
	* @param @return 
	* @return int
	 */
	@Override
	public int updateByPrimaryKeySelective(User user) {
		return usermapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public int insertSelective(User user) {
		return usermapper.insertSelective(user);
	}

	@Override
	public int deleteByPrimaryKey(Long userId) {
		return usermapper.deleteByPrimaryKey(userId);
	}

	@Override
	public User selectByPrimaryKey(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserByName(String name) {
		return usermapper.findUserByName(name);
	}

	@Override
	public Map<String, Object> deleteUserById(String ids) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			int count = 0;
			String []idArray = new String[] {};
			if(StringUtils.contains(ids, ",")) {
				idArray=ids.split(",");
			}else {
				idArray= new String [] {ids};
			}
			for (String id : idArray) {
				count+=this.deleteByPrimaryKey(new Long(id));
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
		}catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	@Override
	public Pagination<UserRoleAllocationBo> findUserAndRole(ModelMap modelMap, Integer pageNo, Integer pageSize) {
		return super.findPage("findUserAndRole", "findCount", modelMap, pageNo, pageSize);
	}

	@Override
	public List<RoleBo> selectRoleByUserId(Long id) {
		return usermapper.selectRoleByUserId(id);
	}

	@Override
	public Map<String, Object> addRole2User(Long userId, String ids) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		int count=0;
		try {
			//删除原先的
			userRoleMapper.deleteByUserId(userId);
			if(StringUtils.isNotBlank(ids)) {
				String[] idArrat = null;
				if(StringUtils.contains(ids, ",")) {
					idArrat = ids.split(",");
				}else {
					idArrat = new String[] {ids};
				}
				
				for(String rid:idArrat) {
					UserRole userrole = new UserRole(userId, new Long(rid));
					count+=userRoleMapper.insertSelective(userrole);
				}
				
			}
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		}catch (Exception e) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作失败，请重试！");
		}
		
		return resultMap;
	}

	@Override
	public Map<String, Object> deleteRoleByUserIds(String userIds) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("userIds", userIds);
			userRoleMapper.deleteRoleByUserIds(resultMap);
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作失败，请重试！");
		}
		return resultMap;
	}


	@Override
	public Pagination<User> findByPage(Map<String, Object> resultMap, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		return super.findPage(resultMap, pageNo, pageSize);
	}

}
