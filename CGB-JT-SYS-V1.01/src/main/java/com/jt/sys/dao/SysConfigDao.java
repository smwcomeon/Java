package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.sys.entity.SysConfig;

public interface SysConfigDao {//SysConfigMapper
	
	/**
	 * 更新配置信息
	 * @param entity 封装配置信息
	 * @return 表示更新的行数
	 */
	int updateObject(SysConfig entity);

	/**
	 * 将对象持久化输出
	 * @param entity
	 * @return
	 */
	int insertObject(SysConfig entity);
	
	/**
	 * 配置id执行删除操作
	 * @param ids
	 * @return
	 */
	int deleteObjects(@Param("ids")Integer...ids);
	
	
	/**
	 * 按条件执行分页查询
	 * @param name 查询条件
	 * @param startIndex 当前页的起始位置
	 * @param pageSize 页面大小(当前页要取的记录数)
	 * @return 当前页数据
	 * select *
	 * from sys_configs
	 * where name like concat("%",#{name},"%")
	 * limit #{startIndex},#{pageSize}
	 */
	 List<SysConfig> findPageObjects(
			@Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	 /**
	  * 依据条件统计数据库中的总记录数
	  * @param name
	  * @return
	  */
	 int getRowCount(@Param("name")String name);
}






