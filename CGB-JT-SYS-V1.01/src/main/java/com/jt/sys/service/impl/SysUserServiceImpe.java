package com.jt.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.jt.common.exception.ServiceException;
import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
import com.jt.sys.vo.SysUserDeptResult;
@Service
public class SysUserServiceImpe implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@RequiresPermissions("sys:user:update")
	@Override
	public int updateObject(SysUser entity, Integer[] roleIds) {
		//1.合法验证
		if(entity==null)
			throw new ServiceException("用户信息不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new ServiceException("用户名不能为空");
		//用户名已经存在的验证,尝试自己实现.
		if(roleIds==null||roleIds.length==0)
			throw new ServiceException("用户必须选一个角色");
		System.out.println("username="+entity.getUsername()+"/"+"password"+entity.getPassword());
		if(!StringUtils.isEmpty(entity.getPassword())){
			//对密码加密
			String salt=UUID.randomUUID().toString();
			SimpleHash hash=//shiro
					new SimpleHash(
							"MD5",
							entity.getPassword(),
							salt,1);
			entity.setSalt(salt);
			entity.setPassword(hash.toString());
		}
		//2.更新数据
		int rows=0;
		try{
			rows=sysUserDao.updateObject(entity);
			//基于用户id删对应的角色信息
			sysUserRoleDao.deleteObjectsByUserId(entity.getId());
			sysUserRoleDao.insertObject(
					entity.getId(),roleIds);
		}catch(Throwable e){
			e.printStackTrace();
			//发起报警信息
			throw new ServiceException("服务端现在异常,请稍后访问");
		}
		//3.返回结果
		return rows;
	}
	
	@RequiresPermissions("sys:user:valid")
	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {
		//1.合法性验证
		if(id==null||id<=0)
			throw new ServiceException("参数不合法,id="+id);
		if(valid==null||valid!=1&&valid!=0)
			throw new ServiceException("参数不合法,valie="+valid);
		if(StringUtils.isEmpty(modifiedUser))
			throw new ServiceException("修改用户不能为空");
		//2.执行禁用或启用操作
		int rows=0;
		try{
			rows=sysUserDao.validById(id, valid, modifiedUser);
		}catch(Throwable e){
			e.printStackTrace();
			//报警,给维护人员发短信
			throw new ServiceException("底层正在维护");
		}
		//3.判定结果,并返回
		if(rows==0)
			throw new ServiceException("此记录可能已经不存在");
		return rows;

	}

	
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		//1.判定参数有效性
		if(entity==null){
			throw new IllegalArgumentException("保存对象不能为空");
		}
		if(StringUtils.isEmpty(entity.getUsername())){
			throw new IllegalArgumentException("用户名不能为空");
		}
		if(StringUtils.isEmpty(entity.getPassword())){
			throw new IllegalArgumentException("密码不能为空");
		}
		//2.保存用户自身信息
		String salt = UUID.randomUUID().toString();//随机的字符串
		SimpleHash sh =new SimpleHash("MD5",//加密算法
				entity.getPassword(), //被加密的对象	
				salt,	//salt盐值(使用随机参数的字符串)
				1);//hashIterations加密次数
		entity.setSalt(salt);
		entity.setPassword(sh.toString());
		int rows = sysUserDao.insertObject(entity);
		//3.保存用户与角色的关系数据
		sysUserRoleDao.insertObject(entity.getId(), roleIds);
		//4.返回结果
		return rows;
	}

	@Override
	public Map<String, Object> findObjectById(Integer userId) {
		//合法性验证
		if(userId==null||userId<=0){
			throw new ServiceException("参数不合法，userId="+userId);
		};
		//业务查询
		SysUserDeptResult user = sysUserDao.findObjectById(userId);
		if(user==null){
			throw new ServiceException("此用户已经不存在");
		};
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
		//数据封装
		HashMap<String,Object> map = new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}

	
	
	@Override
	public PageObject<SysUserDeptResult> findPageObjects(String username, Integer pageCurrent) {
		//参数有效性验证
		if(pageCurrent==null||pageCurrent<1){
			throw new IllegalArgumentException("当前页码值无效");
		}
		//查询总记录数，并进行验证
		int rowCount = sysUserDao.getRowCount(username);
		if(rowCount==0){
			throw new ServiceException("没有找到对应的记录");
		}
		//查询当前页数据
		int pageSize=3;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysUserDeptResult> records = 
				sysUserDao.findPageObjects(username, startIndex, pageSize);
		//封装查询结果
		PageObject<SysUserDeptResult> po=new PageObject<>();
		po.setRecords(records);
		po.setRowCount(rowCount);
		po.setPageSize(pageSize);
		po.setPageCurrent(pageCurrent);
		po.setPageCount((rowCount-1)/pageSize+1);
		//返回查询结果
		return po;
	}

}
