package com.jt.sys.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.sys.entity.SysUser;
import com.jt.sys.vo.SysUserDeptResult;

public interface SysUserDao {
	
	 List<SysUserDeptResult> findPageObjects(
			 @Param("username")String username,
			 @Param("startIndex")Integer startIndex,
			 @Param("pageSize")Integer pageSize);
	 /**
	  * 依据调教统计总数记录
	  * @param username
	  * @return
	  * mybatis 拦截器 可以省略下面的代码 扩展
	  */
	 int getRowCount(@Param("username")String username);
	
	 /**
	 * 基于用户名查找的对象
	 * @param username
	 * @return
	 */
	SysUser findUserByUserName(String username);
	
	/**
	 * 更新用户信息
	 * @param entity
	 * @return
	 */
	int updateObject(SysUser entity);
	 /**
	  * 基于id查询部门用户信息
	  * @param id
	  * @return
	  */
	 SysUserDeptResult findObjectById(Integer id);
	
	/**
	 * 负责将用户信息写入到数据库
	 * @param entity
	 * @return
	 */
	int insertObject(SysUser entity);
	
	/**
	 * 基于用户id启用或者禁用用户状态
	 * @param id
	 * @param valid （状态 1，表示启用 2，表示禁用）
	 * @param modifiedUser （是谁执行的禁用或启用操作）
	 * @return
	 */
	int validById(
			@Param("id")Integer id,
			@Param("valid")Integer valid,
			@Param("modifiedUser")String modifiedUser);

	 /**
	  * 依据条件分页查询的用户以及部门信息
	  * @param username 用户名
	  * @param startIndex  当前页的起始位置
	  * @param pageSize	页面大小
	  * @return
	  */
	
	 int getUserCountByDeptId(Integer deptId);
	 
	

}
