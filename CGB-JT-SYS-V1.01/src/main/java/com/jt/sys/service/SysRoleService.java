package com.jt.sys.service;

import java.util.List;
import java.util.Map;

import com.jt.common.vo.CheckBox;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysRole;

public interface SysRoleService {

	/**
	 * 分页查询当前页记录及总数记录
	 * @param name
	 * @param pageCurrent 当表示要查询的当前页的页码值
	 * @return
	 */
	PageObject<SysRole> findPageObjects(
			String name,
			Integer pageCurrent);
	
	/**
	 * 获取所有的角色信息
	 * @return
	 */
	List<CheckBox> findObjects();
	
	/**
	 * 跟新数据信息
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	int updateObject(SysRole entity,Integer[] menuIds);

	/**
	 * 基于角色id查询角色及关联的菜单id
	 * @param id
	 * @return
	 */
	Map<String,Object> findObjectById(Integer id);
	
	
	/**
	 * 删除角色信息
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	/**
	 * 保存角色以及角色与菜单的关系数据
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	int saveObject(SysRole entity,Integer[] menuIds);
	
	
}
