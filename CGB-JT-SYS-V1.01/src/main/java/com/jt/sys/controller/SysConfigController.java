package com.jt.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.annotation.HandlerMonitor;
import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysConfig;
import com.jt.sys.service.SysConfigService;

@Controller
@RequestMapping("/config/")
public class SysConfigController {

	@Autowired
	private SysConfigService sysConfigService;

	
	@RequestMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(Integer... ids){
		sysConfigService.deleteObjects(ids);
		return new JsonResult("delete OK");
	}
	
	
	@RequestMapping(value="doSaveObject",
			produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doSaveObject(SysConfig entity){
		sysConfigService.saveObject(entity);
		return new JsonResult("save OK");
	}
	
	//修改数据
	@RequestMapping(value="doUpdateObject",
			produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doUpdateObject(SysConfig entity){
		sysConfigService.updateObject(entity);
		return new JsonResult("update OK");
	}
	
	
	@RequestMapping(value="doFindPageObjects",method=RequestMethod.GET
			/*,produces="application/json;charset=utf-8"*/)
	@ResponseBody
	@HandlerMonitor
	public JsonResult doFindPageObjects(String name,
			Integer pageCurrent){
		PageObject<SysConfig> pageObject = 
				sysConfigService.findPageObject(name, pageCurrent);
		return new JsonResult(pageObject);
		
	}//将对象转换成json输出（底层借助fastjson）
	
	
	
	@RequestMapping("doConFindUI")
	private String doFindUI(){
		return "config-jquery-ajax";
	}
	
	@RequestMapping("doConfigListUI")
	private String doConfigListUI(){
		return "sys/config_list";
	}
	
	
	@RequestMapping("doConfigEditUI")
	private String doConfigEditUI(){
		return "sys/config_edit";
	}
	
	
	
	/**
	 * 此注解声明的方法
	 * 为一个异常处理方法
	 * @return class[]
	 * 1)Spring MVC如何识别这个方法是一个异常处理方法
	 * 		--注解
	 * 2)Spring mvc 后端处理器的方法出现异常底层会如何处理？
	 * 
	 */
	

}
