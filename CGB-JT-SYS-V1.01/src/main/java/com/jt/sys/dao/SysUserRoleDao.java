package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


/**
 * 用于操作用户和角色的关系表数据
 *
 */
public interface SysUserRoleDao {
	

	
	/**
	 * 基于用户id删除用户信息
	 * @param userId
	 * @return
	 */
	int deleteObjectsByUserId(Integer userId);
	/**
	 * 负责将用户与角色的关系数据写入到数据库
	 * @param userId 用户id
	 * @param roleIds 多个角色id
	 * @return
	 */
	int insertObject(
			@Param("userId")Integer userId,
			@Param("roleIds")Integer[]roleIds);


	/**基于角色id删除角色和用户在中间中的关系数据*/
	int deleteObjectsByRoleId(Integer roleId);

	/**
	 * 查询用户对应的角色id
	 * 从用户和角色对用的中间表查询用户对应的角色id
	 * @param id 用户id
	 * @return
	 */
	List<Integer> findRoleIdsByUserId(Integer id);

}
