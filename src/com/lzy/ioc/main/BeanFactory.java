package com.lzy.ioc.main;
/**
 * bean 工厂类
 * 
 * 只负责bean的基本管理，不负责bean 的创建
 * 
 * @author admin
 *
 */
public interface BeanFactory {

	/**
	 * 根据bean的名称获取bean
	 * 
	 * @param beanName
	 * @return
	 */
	Object getBean(String beanName);
	
	
}
