package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


/**
 * 为什么写此DAO？
 * 市场规则：
 * 一个表对应一个映射文件
 * 一个映射文件对应一个Dao接口
 * @author Administrator
 *
 */
public interface SysRoleMenuDao {
	/**
	 * 基于角色id从中间表
	 * @param roleIds
	 * @return
	 */
	List<Integer> findMenuIdsByRoleId(
			@Param("roleIds")Integer... roleIds);

	
	//基于菜单id删除关系表数据
	int deleteObjectsByMenuId(Integer menuId);
	
	/**基于角色id删除菜单关系表数据*/
	int deleteObjectsByRoleId(Integer menuId);
	/**
	 * 角色与菜单时 多对多的关系，这种关系的维护，在表设计领域中是通过中间表来实现的
	 * 保存角色和菜单的关系数据
	 * @param roleId 角色id
	 * @param menuIds 菜单id
	 * @return
	 */
	int insertObject(
			@Param("roleId")Integer roleId,
			@Param("menuIds")Integer[] menuIds);

	/**
	 * 定义基于角色查询菜单id的方法
	 * 基于角色id从中间表获取菜单id
	 * @param roleId
	 * @return
	 */
	List<Integer> findMenuIdByRoleId(Integer roleId);
	
	/**
	 * 根据添加角色id删除关系数据的方法
	 * @param roleId
	 * @return
	 */
	int delteObjectsByRoleId(Integer roleId);
}
