package com.jt.common.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 通过此类扫描springMVC
 * @author Administrator
 *
 */
@Configuration//下面写的时候可以省略
@ComponentScan(value="com.jt",
	includeFilters={@Filter(classes={Controller.class,ControllerAdvice.class},
type=FilterType.ANNOTATION)},//这个type不指定时会出现无效的参数异常，要指定上边的为注解类
useDefaultFilters=false)
@EnableWebMvc//启用mvc默认配置()
public class AppServletConfig extends WebMvcConfigurerAdapter {

	/*配置视图解析器*/
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {

		registry.jsp("/WEB-INF/pages/", ".html");
	}

	//整合fastjson库（提供了操作json的API）
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		//1.构建messageConverter对象
		FastJsonHttpMessageConverter msConverter = 
				new	FastJsonHttpMessageConverter();
		
		//设置fastJons的基本设置(循环引用的问题)
		FastJsonConfig config = new FastJsonConfig();
		config.setSerializeConfig(SerializeConfig.globalInstance);
		//禁止循环引用问题（id）
		config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
		msConverter.setFastJsonConfig(config);
		
		
		//2.配置messageConverter对象
		List<MediaType> list = new ArrayList<>();
		list.add(new MediaType("text", "html", Charset.forName("utf-8")));
		list.add(new MediaType("application", "json", Charset.forName("utf-8")));
		
		msConverter.setSupportedMediaTypes(list);
		//3.将messageConverter对象添加到converters容器
		converters.add(msConverter);
	}
	
	//加入项目需要拦截器 可在此位置拦截器
	
	
	
}
