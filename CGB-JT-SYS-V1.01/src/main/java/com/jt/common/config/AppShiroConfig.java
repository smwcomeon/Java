package com.jt.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 定义shiro框架的配置信息
 * @author Administrator
 *
 */
@Configuration
public class AppShiroConfig {
	
	/**
	 * 配置shiro的SecurityManager对象
	 */
	@Bean("securityManager")
	public SecurityManager newSecurityManager(
			AuthorizingRealm realm){
		DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
		//通过reaml访问数据库
		sManager.setRealm(realm);
		return sManager; 
	}

	/**
	 * 配置ShiroFilterFactory工厂
	 * @param securityManager
	 * @return
	 */
	@Bean("shiroFilterFactoryBean")
	public ShiroFilterFactoryBean newShiroFilterFactoryBean(
			SecurityManager securityManager){//shiro 包
		ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		//当此用户是一个非认证用户,需要先登陆进行认证
		bean.setLoginUrl("/doLoginUI.do");
		LinkedHashMap<String,String> fcMap=new LinkedHashMap<>();
		//下边表示允许匿名访问的文件夹 ， 前面为固定的文件夹的路径（根据自己的项目而定例如图片等等）
		fcMap.put("/bower_components/**","anon");//anon表示允许匿名访问
		fcMap.put("/build/**", "anon");
		fcMap.put("/dist/**","anon");
		fcMap.put("/plugins/**","anon");
		fcMap.put("/doLogin.do","anon");
		fcMap.put("/doLogout.do ","logout");//"logout"表示退出
		fcMap.put("/**", "authc");//必须授权才能访问
		bean.setFilterChainDefinitionMap(fcMap);
		return bean;
	}


	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor newLifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
	}


	@DependsOn(value="lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator(){
		return new DefaultAdvisorAutoProxyCreator();
	}


	@Bean
	public AuthorizationAttributeSourceAdvisor newAuthorizationAttributeSourceAdvisor(
			SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor bean=
				new AuthorizationAttributeSourceAdvisor();
		bean.setSecurityManager(securityManager);
		return bean;
	}



}
