package com.jt.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jt.common.vo.Node;
import com.jt.sys.entity.SysMenu;

public interface SysMenuDao {
	
	List<String> findPermissions(
			@Param("menuIds")
			Integer... menuIds);

	
	int updateObject(SysMenu entity);
	/**
	 * 将数据持久化到数据库
	 * 假如参数使用了@Param("entity")注解修饰，那么在mapper中获取对象值
	 * 时要加上@Param注解中定义的参数作为前缀
	 * @param entity
	 * @return
	 */
	int insertObject(SysMenu entity);
	/**
	 * 查询菜单节点信息，将此信息最后呈现在zTree对象上
	 * @return
	 */
	List<Node> findZtreeMenuNodes();
	 /**
	  * 根据菜单id统计子菜单的个数
	  * @param id
	  * @return
	  */
	 int getChildCount(Integer id);
	 /**
	  * 根据id 删除菜单
	  * @param id
	  * @return
	  */
	 int deleteObject(Integer id);

	
	/**
	 * 查询所有菜单以及上一级菜单
	 * 假如本菜单没有上一级菜单是否要呈现？
	 * 
	 * 当菜单为一级菜单时它的上一级菜单默认为null？
	 * 这样的菜单，采用怎样的sql查询？关联关系？
	 * 方案：表关联
	 * @return
	 */
	List<Map<String,Object>> findObjects();
}
