package com.jt.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;

@Controller
@RequestMapping("/")
public class LoginController {

	@ResponseBody
	@RequestMapping("doLogin")
	public JsonResult doLogin(String username,
			String password){
		//对用户身份进行认证
		System.out.println(username+"/"+password);
		//获取主体对象
		Subject subject = SecurityUtils.getSubject();
		//提交用户信息
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		subject.login(token);
		return new JsonResult("login ok");
	}
	
}
