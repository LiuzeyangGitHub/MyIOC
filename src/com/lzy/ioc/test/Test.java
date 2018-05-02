package com.lzy.ioc.test;

import com.lzy.ioc.main.AnnotationApplicationContext;
import com.lzy.ioc.main.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		AnnotationApplicationContextTest();
	}
	
	
	/**
	 * Annotation 方式注入
	 * 
	 */
	public static void AnnotationApplicationContextTest(){
		AnnotationApplicationContext ioc  = new AnnotationApplicationContext("com.lzy.ioc");
		BeanB b = (BeanB) ioc.getBean("beanB");
		System.out.println(b.toString());
	}
	
	
	/**
	 * XML 方式注入
	 * 
	 */
	public static void ClassPathXmlApplicationContextTest(){
		String path = "applicationContext.xml";
		ClassPathXmlApplicationContext ioc  = new ClassPathXmlApplicationContext(path);
		BeanA a = (BeanA) ioc.getBean("beanA");
		//System.out.println(a.getBeanB().getName());
	}
	
}
