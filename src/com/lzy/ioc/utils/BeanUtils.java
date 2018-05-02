package com.lzy.ioc.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 根据对象和属性获取set方法
 * 
 * @author admin
 *
 */
public class BeanUtils {


	public static Method getWriteMethod(Object beanObj, String name){

		Method method = null;
		try {
			// 1.分析bean对象 -->BeanInfo
			BeanInfo beanInfo = Introspector.getBeanInfo(beanObj.getClass());
			// 2.根据beaninfo获取所有属性的描述器
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			// 3.遍历属性器
			if(pds!=null){
				for (PropertyDescriptor pd : pds) {
					//获取当前属性
					String pName = pd.getName();
					if(pName.equals(name)){
						//获取set方法
						method = pd.getWriteMethod();
					}
				}
			}
			
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		if(method==null){
			throw new RuntimeException("请检查"+name+"属性的set方法是否创建");
		}
		
		return method;
	}
	
	
	
}
