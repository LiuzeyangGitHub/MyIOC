package com.lzy.ioc.main;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.lzy.ioc.config.Bean;
import com.lzy.ioc.config.Property;
import com.lzy.ioc.config.parse.ConfigManager;
import com.lzy.ioc.utils.BeanUtils;
/**
 * 
 * 使用xml 方式注入bean
 * 
 * @author admin
 *
 */
public class ClassPathXmlApplicationContext extends AbstraBeanFactory {

	//配置信息  
    private Map<String, Bean> config;  
    
	public ClassPathXmlApplicationContext(String path) {
		// 1.读取配置信息，将xml转化成com.lzy.ioc.config.Bean对象
		config = ConfigManager.getConfig(path);
		
		// 2.遍历出所有的com.lzy.ioc.config.Bean，根据信息创建实例
		if(config != null){
			for(Entry<String, Bean> en :config.entrySet()){
				// 获取bean描述信息
				String beanName = en.getKey();
				Bean bean = en.getValue();
				
				// 判断该bean是否已经存在,存在就不再创建，不存在就继续创建
				Object object = context.get(beanName);
				if(Objects.isNull(object)){
					// 创建需要注入的bean
					Object creatBean = creatBean(bean);
					// 放入到容器中
					context.put(beanName, creatBean);
				}
				
			}
		}
	}
	
	
	
	/**
	 * 根据Bean对象的描述信息创建 注入到ioc容器的对象实例
	 * 
	 * 实现：获取bean中的className，根据反射实例化该对象
	 * 
	 * @param bean
	 * @return
	 */
	private Object creatBean(Bean bean) {
		// 1.获取class的路径
		String className = bean.getClassName();
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("请检查bean的Class配置" + className);
		}
		
		// 2.根据clazz创建实例对象
		Object beanObj = null;
		try {
			beanObj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("bean没有空参构造"+className); 
		}
		
		// 3.为创建的对象填充数据
		if(Objects.nonNull(bean.getProperties())){
			for (Property property : bean.getProperties()) {
				// 1.基本类型value注入
				// 获取注入的属性名称
				String name = property.getName();
				// 根据属性名称获取对应的set方法
				Method setMethod = BeanUtils.getWriteMethod(beanObj, name);
				Object parm = null;
				if(Objects.nonNull(property.getValue())){
					// 获取注入的属性值
					String value = property.getValue();
					parm = value;
				}
				
				// 2.如果是复杂对象的注入
				if(Objects.nonNull(property.getRef())){
					// 先从当前容器中获取，看是否已经被创建
					Object exsiBean = context.get(property.getRef());
					if(Objects.isNull(exsiBean)){
						// 创建bean并放到容器中
						exsiBean = creatBean(config.get(property.getRef()));
						context.put(property.getRef(), exsiBean);
					}
					parm = exsiBean;
				}
				
				
				// 3.调用set方法将值设置到对象中 
				try {
					setMethod.invoke(beanObj, parm);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					throw new RuntimeException("bean的属性"+parm+"没有对应的set方法，或者参数不正确"+className);  
				}
				
			}
		}
		
		
		
		return beanObj;  
	}

}
