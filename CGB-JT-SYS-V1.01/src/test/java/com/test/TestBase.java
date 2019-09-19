package com.test;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jt.common.config.AppServletConfig;

public class TestBase {
	private AnnotationConfigApplicationContext ctx;

	@Before
	public void init(){
		ctx = new AnnotationConfigApplicationContext(AppServletConfig.class);
	}
	
	@After
	public void close(){
		ctx.close();
	}
}
