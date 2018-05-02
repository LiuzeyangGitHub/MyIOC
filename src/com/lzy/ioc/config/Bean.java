package com.lzy.ioc.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述bean的基本信息
 * <bean name="B" class="com.lzy.ioc"> <property name ="a" ref="A"></property>
 * </bean> 主要有name 、 class、 property
 * 
 * @author admin
 *
 */
public class Bean {

	/** bean名称 */
	private String name;
	/** 类的全路径包名 */
	private String className;
	/** 类的全路径包名 */
	private List<Property> properties = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

}
