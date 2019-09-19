package com.jt.oop;

interface HelloService{
	public void sayHello();
		
}
class HelloServiceImpl implements HelloService{
	public void sayHello(){
		System.out.println("hello world");
	}
}
class SubHelloServiceImpl extends HelloServiceImpl{
	@Override
	public void sayHello() {
		System.out.println("==="+System.currentTimeMillis());
		super.sayHello();
	}
}

class HelloServiceImplProxy implements HelloService{
	private HelloService helloService;
	public HelloServiceImplProxy(HelloService helloService) {
		this.helloService =helloService;
	}
	
	@Override
	public void sayHello() {
		System.out.println(System.nanoTime());
		helloService.sayHello();
	}
	
}
public class TestOOP01 {
	public static void main(String[] args) {
		//HelloServiceImpl h = new HelloServiceImpl();
		//父类型的可以接受子类类型
		//HelloServiceImpl h = new SubHelloServiceImpl();
		HelloService h = new HelloServiceImplProxy(new HelloServiceImpl());
		h.sayHello();
	}
}
