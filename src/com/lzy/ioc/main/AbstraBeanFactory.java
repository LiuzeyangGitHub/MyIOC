package com.lzy.ioc.main;

import java.util.HashMap;
import java.util.Map;


public abstract class AbstraBeanFactory  implements BeanFactory{
	/** bean 容器，用于存储创建的bean */
	protected Map<String,Object> context = new HashMap<>();
	
	@Override
	public Object getBean(String beanName) {
		return context.get(beanName);
	}
}
