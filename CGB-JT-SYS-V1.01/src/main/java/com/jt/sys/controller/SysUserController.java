package com.jt.sys.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
import com.jt.sys.vo.SysUserDeptResult;

@Controller
@RequestMapping("/user/")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;
	
	
	/**
	 * @RequiresPermissions 注解表示的方法
	 * 需要进行授权才可访问
	 * 底层系统在执行此方法时就会使用
	 * subject.isPermitted(sys:user:valid)
	 * 此信息提交给SecurityManager, 此对象会调用授权管理器来完成授权操作
	 * @param id
	 * @param valid 
	 * @return
	 */
	@RequiresPermissions("sys:user:valid")
	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(
			Integer id,
			Integer valid){
		//用户登录成功之后会自动将用户信息存入session对象中
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		sysUserService.validById(
				id,
				valid, 
				user.getUsername());//"admin"用户将来是登陆用户
		return new JsonResult("update ok");
	}
	
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(
			SysUser entity,
			Integer[] roleIds){
		sysUserService.updateObject(entity,roleIds);
		return new JsonResult("update ok");
	}
	
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id){
		Map<String,Object> map=
				sysUserService.findObjectById(id);
		return new JsonResult(map);
	}
	
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(
			SysUser entity,
			Integer[] roleIds){
		
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		entity.setModifiedUser(user.getUsername());
		entity.setCreatedUser(user.getUsername());
		
		sysUserService.saveObject(entity,roleIds);
		return new JsonResult("save ok");
	}

	
	@RequestMapping("doUserEditUI")
	public String doUserEditUI(){
		return "sys/user_edit";
	}

	
	@RequestMapping("doUserListUI")
	public String doUserListUI(){
		return "sys/user_list";
	}

	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(
			String username,Integer pageCurrent){
		PageObject<SysUserDeptResult> pageObject=
				sysUserService.findPageObjects(username,
						pageCurrent);
		return new JsonResult(pageObject);
	}

	

}
