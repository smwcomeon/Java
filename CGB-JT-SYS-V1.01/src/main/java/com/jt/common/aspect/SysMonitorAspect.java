package com.jt.common.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
/**
 * Demo 监控切面
 *
 */
@Order(2) //定义切面的执行顺序
@Service
@Aspect
public class SysMonitorAspect {
	@Pointcut("bean(*ServiceImpl)")
	public void monitor(){}
	/**
	 * 前置通知 
	 */
	@Before("monitor()")
	public void beforeMethod(){
		System.out.println("@Before");
	}
	/**返回通知*/
	@AfterReturning("monitor()")
	public void returnMethod(){
		System.out.println("@AfterReturning");
	}
	/**异常通知*/
	@AfterThrowing("monitor()")
	public void  throwMethod(){
		System.out.println("@AfterThrowing");
	}
	/**后置通知*/
	@After("monitor()")
	public void beforeAfter(){
		System.out.println("@After");
	}
}	
/**
 * 假如将如上通知放入有一种结构的话，可参考如下结构
 * try{
 * @Before
 * 核心业务
 * @AfterReturning
 * }catch(Exception e){
 * AfterThrowing
 * }finally{
 * @After
 * }
 */
