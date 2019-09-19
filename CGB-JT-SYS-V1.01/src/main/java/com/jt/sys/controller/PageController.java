package com.jt.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
	
	/**通过此方法直接返回页面首页*/
	@RequestMapping("doIndexUI")
	public String doIndexUI(){
		return "starter";//返回的是thml
	}
	
	
	/**
	 * 此方法用于返回分页页面
	 * @return
	 */
	@RequestMapping("doPageUI")
	public String doPageUI(){
		return "common/page";
	}
	
	
	/**
	 * 返回登录页面
	 * @return
	 */
	@RequestMapping("doLoginUI")
	public String doLoginUI(){
		return "login";
	}
	
}
