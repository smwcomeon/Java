package com.jt.sys.service;

import java.util.Map;

import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysRole;
import com.jt.sys.entity.SysUser;
import com.jt.sys.vo.SysUserDeptResult;

public interface SysUserService {
	
	/**
	 * 跟新用户信息
	 * @param entity 用户对象
	 * @param roleIds	用户对应的信息
	 * @return
	 */
	int updateObject(SysUser entity,Integer[] roleIds);
	
	/**
	 * 基于用户id查询用户和角色信息
	 * @param userId
	 * @return
	 */
	Map<String,Object> findObjectById(Integer userId);
	
	
	/**
	 * 保存用户以及用户与角色的关系数据
	 * @param entity
	 * @param roleIds
	 * @return
	 */
	int saveObject(SysUser entity,Integer[] roleIds);
	/**
	 * 禁用或启用用户的状态信息
	 * @param id
	 * @param valid 状态（1启用，0禁用）
	 * @param modifiedUser
	 * @return
	 */
	int validById(Integer id,
			Integer valid,
			String modifiedUser);

	
	/**
	 * 分页查询用户以及对应的部门信息
	 * @param username 用户名
	 * @param pageCurrent 当前页的页码值
	 * @return
	 */
	PageObject<SysUserDeptResult> findPageObjects(
			String username,
			Integer pageCurrent);

}
