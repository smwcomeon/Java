package com.jt.common.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * web服务器启动时会自定加载此类 相当于web.xml
 * @author Administrator
 *
 */
public class WebAppInitializer extends 
			AbstractAnnotationConfigDispatcherServletInitializer{

	/**
	 * 此方法配置前段控制器
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		//super.onStartup(servletContext);  等价于下边的两个行代码
		registerContextLoaderListener(servletContext);
		//注册shiro过滤器
		registerFilter(servletContext);
		registerDispatcherServlet(servletContext);
	}

	
	/**
	 *注册shiro中的核心过滤器 
	 * @param servletContext
	 */
	private void registerFilter(ServletContext servletContext) {
		//注册Filter对象
		//什么时候需要采用此方式进行注册?
		//项目没有web.xml并且此filter不是自己写的
		FilterRegistration.Dynamic dy=servletContext.addFilter("filterProxy",
						DelegatingFilterProxy.class);
		dy.setInitParameter("targetBeanName","shiroFilterFactoryBean");
		dy.addMappingForUrlPatterns(
				null,//EnumSet<DispatcherType>(null表示所有 转发 重定向等 （DispatcherType）中的类型)
				false,//isMatchAfter
				"/*");//url-pattern
	}


	/**
	 * 此方法负责加载service ，dao
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// AppShiroConfig 配置shiro的核心类（包含securityManager、shiroFilterFactoryBean）
		return new Class[]{AppRootConfig.class,AppShiroConfig.class};
	}

	
	/**
	 * 负责加载spring mvc
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[]{AppServletConfig.class};
	}

	
	/**
	 * 配置映射路径
	 */
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"*.do"};
	}

}
