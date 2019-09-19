package com.jt.sys.service;

import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysConfig;

public interface SysConfigService {

	/**
	 * 将【配置信息写入数据库
	 * @param entity
	 * @return
	 */
	int updateObject(SysConfig entity);
	
	/**
	 * 将【配置信息写入数据库
	 * @param entity
	 * @return
	 */
	int saveObject(SysConfig entity);
	/**
	 * 基于id执行删除业务
	 * @param ids 此值来自页面 选择的多条记录的id值
	 * @return
	 */
	int deleteObjects(Integer...ids);
	/**
	 * 依据条件执行分页查询
	 * @param name 查询条件
	 * @param pageCurrent 当前页页码
	 * @return
	 */
	PageObject<SysConfig> findPageObject(
			String name,
			Integer pageCurrent
			);
	
	
	
}
