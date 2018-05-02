package com.lzy.ioc.test;

import com.lzy.ioc.annotation.Autowired;
import com.lzy.ioc.annotation.Bean;

@Bean
public class BeanA {
	@Autowired
	private BeanB beanB;

	public BeanB getBeanB() {
		return beanB;
	}

	/*public void setBeanB(BeanB beanB) {
		this.beanB = beanB;
	}*/
	
}
