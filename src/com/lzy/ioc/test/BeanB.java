package com.lzy.ioc.test;

import com.lzy.ioc.annotation.Bean;

@Bean
public class BeanB {

	private String name ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
