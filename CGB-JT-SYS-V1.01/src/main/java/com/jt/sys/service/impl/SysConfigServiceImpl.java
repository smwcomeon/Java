package com.jt.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.jt.common.annotation.RequestLog;
import com.jt.common.exception.ServiceException;
import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysConfigDao;
import com.jt.sys.entity.SysConfig;
import com.jt.sys.service.SysConfigService;

@Service
@Transactional(rollbackFor=Throwable.class)  //全局事务事务（类中的所有方法都加事务）  事务回滚
public class SysConfigServiceImpl implements SysConfigService {
	//IOC get set 方法 注解属性
	@Autowired
	private SysConfigDao sysConfigDao;
	
	
	/**
	 * 通过@RequestLog自定义注解对方法业务进行描述
	 * @Transactional放方法上添加特性    isolation=Isolation.READ_COMMITTED  表示提交的事务（不允许脏读）
	 */
	@RequestLog("配置信息分页查询")
	@Transactional(readOnly=true,isolation=Isolation.READ_UNCOMMITTED,timeout=30)  
	@Override
	public PageObject<SysConfig> findPageObject(String name, Integer pageCurrent) {
		//1.参数有效性认证
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("当前页码值无效");
		//2.基于name参数查询总记录数
		int rowCount= 0;
		try{
			rowCount=sysConfigDao.getRowCount(name);
		}catch(Throwable e){
			e.printStackTrace();
			//报警（给运维人员发送短信）
			throw new ServiceException("系统故障，稍后访问");
		}
		//3.对总记录数进行判定，假如值为0无需再次执行后续查询
		if(rowCount==0)
			throw new ServiceException("不存在对应记录");
		//4.依据条件查询当前页记录(list)
		//4.1定义页面大小
		int pageSize=2;
		//4.2计算当前页记录的起始位置
		int startIndex=(pageCurrent-1)*pageSize;
		//4.3从当前页起始位置查询当前页记录
		List<SysConfig> records=
				sysConfigDao.findPageObjects(name, startIndex, pageSize);
		//5.对查询的结果进行封装
		PageObject<SysConfig> pageObject= new PageObject<>();
		pageObject.setRecords(records);
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCurrent(pageCurrent);

		int pageCount=rowCount/pageSize;
		if(rowCount%pageSize!=0){
			pageCount++;
		}
		//pageObject=(rowCount-1)/pagesize+1
		pageObject.setPageCount(pageCount);

		//6.返回结果
		return pageObject;
	}
	//--------------------------------------------------------
	
	@Override
	public int updateObject(SysConfig entity) {
		if(entity==null){
			throw new IllegalArgumentException("保存对象不能为空");
		}
		if(StringUtils.isEmpty(entity.getName())){
			throw new IllegalArgumentException("参数名不能为空");
		}
		if(StringUtils.isEmpty(entity.getValue())){
			throw new IllegalArgumentException("参数值不能为空");
		}

		int rows =sysConfigDao.insertObject(entity);

		if(rows==0){
			throw new ServiceException("记录可能已经不存在了");
		}
		return rows;
	}

	//--------------------------------------------------------
	//@Transactional
	@Override
	public int saveObject(SysConfig entity) {
		//参数有效性验证
		if(entity==null){
			throw new IllegalArgumentException("保存对象不能为空");
		}
		if(StringUtils.isEmpty(entity.getName())){
			throw new IllegalArgumentException("参数名不能为空");
		}
		if(StringUtils.isEmpty(entity.getValue())){
			throw new IllegalArgumentException("参数值不能为空");
		}

		int rows =sysConfigDao.insertObject(entity);

		if(rows==0){
			throw new ServiceException("系统故障，正在恢复");
		}
		return rows;
	}

//--------------------------------------------------------------------
	@Override
	public int deleteObjects(Integer... ids) {
		//1.进行参数验证
		if(ids==null||ids.length==0)
			throw new IllegalArgumentException("至少选择一条记录");
		//2.执行删除操作
		int rows =0;
		try {
			rows = sysConfigDao.deleteObjects(ids);
		} catch (Throwable e) {
			e.printStackTrace();
			//1、给运维法短信或者切换到另一台服务器
			throw new ServiceException("系统运维中");
		}
		//3、验证删除结果
		if(rows==0)
			throw new ServiceException("记录可能已经不存在了");
		//4.返回结果
		return rows;
	}









}
