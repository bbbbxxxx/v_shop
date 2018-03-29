package com.dianshu.common.dao;

import java.util.List;
import java.util.Map;

import com.dianshu.common.model.User;
import com.dianshu.permission.bo.RoleBo;

public interface UserMapper {
	  int insert(User user);
	  int insertSelective(User user);
	  int deleteByPrimaryKey(Long userId);
	  int updateByPrimaryKeySelective(User user);
	  User login(Map<String, Object> map);
	  User findUserByName(String name);
	  List<RoleBo> selectRoleByUserId(Long userId);
}
