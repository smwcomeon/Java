package com.jt.common.aspect;

import java.lang.reflect.Method;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jt.common.annotation.RequestLog;
import com.jt.common.util.IPUtils;
import com.jt.sys.dao.SysLogDao;
import com.jt.sys.entity.SysLog;
import com.jt.sys.entity.SysUser;
/**
 * 切面流程顺序 ：
 * 	一、在切面切点内new一个point对象
 * 		1.1获取被切面的类的bean注解（bean加类名）
 *  二、在service、dao层起用切面功能 加注解
 *  三、 @Order(1)不同切面的执行顺序  数字越小越优先
 *  
 *  日志切面
 * @Aspect 注解描述的类是一个切面对象
 * 		此对象通常要定义：
 * 	1）通知（扩展业务）
 *	2）切入点（织入扩展业务的那些点）
 *如果要启用这个功能要在业务层
 *	(在配置类AppRootConfig启用)核心类加注解 @EnableAspectJAutoProxy
 */
@Order(1)
@Service
@Aspect
public class SysLogAspect {
	@Autowired
	private SysLogDao sysLogDao;

	/**
	 * @Pointcut 注解用于定义切点表达式
	 * 切入点：切点扩展功能的多个连接点(具体的方法)的集合
	 * bean(sysConfigServiceImpl) 表示交给spring框架管理的类SysConfigServiceImpl
	 */
	@Pointcut("bean(sysConfigServiceImpl)")
	public void sysPointCut(){

	}
	/**
	 * @Around  注解修饰的方法为一个环绕通知对象
	 * @param point 连接点 通常指核心业务方法
	 * @return
	 * @throws Throwable 
	 * @Around("sysPointCut()")这个注解的意识是通过提取("bean(sysConfigServiceImpl)")
	 * 的方法 public void sysPointCut(){}
	 */
	//@Around("bean(sysConfigServiceImpl)")
	@Around("sysPointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable{

		Object result = saveSysLog(joinPoint);
		return result;
	}
	private Object saveSysLog(ProceedingJoinPoint joinPoint) throws Throwable, NoSuchMethodException {
		long startTime = System.currentTimeMillis();
		System.out.println("startTime:"+System.nanoTime());
		//执行目标方法(目标result就是目标方法执行的结果)
		/*
		 * 获取bean(sysConfigServiceImpl)中所有目标方法 joinPoint.proceed()
		 */
		Object result = joinPoint.proceed();
		long endTime = System.currentTimeMillis();
		//获取用户的操作日志，并将日志信息写入数据库
		saveSysLog(joinPoint, startTime, endTime);
		//System.out.println("endTime="+System.nanoTime());
		return result;//返回目标方法查询到的结果
	}
	private void saveSysLog(ProceedingJoinPoint joinPoint, long startTime, long endTime) throws NoSuchMethodException {
		//1.获取用户时长
		long totalTime=endTime-startTime;
		//2.获取当前操作用户
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		String username = user.getUsername();
		//3.获取操作的方法（哪个类的哪个方法）
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();

		Class<?> targetCls=joinPoint.getTarget().getClass();
		System.out.println(targetCls.getName());
		Class<?>[] parameterNames = ms.getParameterTypes();

		String methodName = ms.getName();
		//拼接完成的包名加类名加方法名 然后出入数据库 方便以后查看用户常访问的方法
		String clsMethod=targetCls.getName()+"."+methodName;//出入数据库
		System.out.println("clsMethod="+clsMethod);

		//通过签名获取操作方法 然后输出看方法类型
		//Method method=ms.getMethod();
		//System.out.println("method="+method);

		String s1 = ms.getClass().getName();
		System.out.println(s1);
		//4.获取操作方法时传入的实际参数
		String args = JSON.toJSONString(joinPoint.getArgs());
		System.out.println("args="+args);
		//5.获取当前操作的说明（这是什么操作）
		Method method=targetCls.getDeclaredMethod(methodName, parameterNames);
		System.out.println("method="+method);

		RequestLog requestLog = method.getDeclaredAnnotation(RequestLog.class);
		String operation="";
		if(requestLog!=null){
			System.out.println(requestLog.value());
			operation=requestLog.value();
		}
		//6,获取当前用户的id地址
		String ip = IPUtils.getIpAddr();
		System.out.println(ip);
		//7.封装日志信息 
		SysLog log = new SysLog();
		log.setIp(ip);
		log.setUsername(username);
		log.setMethod(clsMethod);
		log.setOperation(operation);
		log.setParams(args);
		log.setTime(totalTime);

		//8将日志对象存储到数据库
		sysLogDao.insertObject(log);
	}
}
