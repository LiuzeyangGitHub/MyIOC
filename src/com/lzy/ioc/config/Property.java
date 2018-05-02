package com.lzy.ioc.config;

/**
 * bean 的属性值 <property name ="name" value="zhangsan" ref="bean2"></property>
 * 
 * @author admin
 *
 */
public class Property {

	/**名称*/
	private String name;
	/**值*/
	private String value;
	/**引用 */
	private String ref;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

}
