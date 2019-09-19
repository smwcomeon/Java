package com.jt.common.controller;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;

/**
 * 全局异常处理类（需要使用@ControllerAdvice注解）
 *可以在此类中添加所有的controller中需要共享的异常处理方法
 *就近异常处理 当有Controller处理异常时只运行Controller中的异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	public GlobalExceptionHandler() {
		System.out.println("GlobalExceptionHandler");
	}
	/**
	 * 此注解声明的方法
	 * 为一个异常处理方法
	 * @return class[]
	 * 
	 * 1)Spring MVC如何识别这个方法是一个异常处理方法
	 * 		--注解
	 * 2)Spring mvc 后端处理器的方法出现异常底层会如何处理？
	 * 
	 */

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public JsonResult doRuntimeException(RuntimeException e){
		System.out.println("Global.doRuntimeException");
		e.printStackTrace();
		//return e.getMessage();
		return new JsonResult(e);
	}
	
	@ExceptionHandler(ShiroException.class)
	  @ResponseBody
	  public JsonResult doHandleShiroException(
			  ShiroException e){
		  e.printStackTrace();
		  JsonResult result=new JsonResult();
		  result.setState(0);//error
		  if(e instanceof IncorrectCredentialsException){
			result.setMessage("密码不正确");//message
			return result;
		  }if(e instanceof AuthorizationException){
			result.setMessage("没权限执行此操作");//message
			return result;
		  }
		  result.setMessage(e.getMessage());
		  return result;
	  }
	  
}
