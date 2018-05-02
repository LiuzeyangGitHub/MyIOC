package com.lzy.ioc.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 根据对象和属性获取set方法
 * 
 * @author admin
 *
 */
public class BeanUtils {

	/**
	 * 获取set方法
	 * 
	 * @param beanObj set方法所属的类
	 * @param name set方法字段
	 * @return
	 */
	public static Method getWriteMethod(Object beanObj, String name) {

		Method method = null;
		try {
			// 1.分析bean对象 -->BeanInfo
			BeanInfo beanInfo = Introspector.getBeanInfo(beanObj.getClass());
			// 2.根据beaninfo获取所有属性的描述器
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			// 3.遍历属性器
			if (pds != null) {
				for (PropertyDescriptor pd : pds) {
					// 获取当前属性
					String pName = pd.getName();
					if (pName.equals(name)) {
						// 获取set方法
						method = pd.getWriteMethod();
					}
				}
			}

		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		if (method == null) {
			throw new RuntimeException("请检查" + name + "属性的set方法是否创建");
		}

		return method;
	}

	/**
	 * 通过对象直接给属性赋值，不通过set方法
	 * 
	 * @param bean set方法所属的类
	 * @param fieldName 属性字段名
	 * @param value  注入的值
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static void setValue(Object bean, String fieldName, Object value)
			throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		Field privateVar = bean.getClass().getDeclaredField(fieldName);
		privateVar.setAccessible(true);
		privateVar.set(bean, value);
	}
	/**
	 * 不通过get方法获取属性值
	 * 
	 * 
	 * @param bean get方法所属的类
	 * @param fieldName 属性字段名
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static Object getValue(Object bean, String fieldName)
			throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		Field privateVar = bean.getClass().getDeclaredField(fieldName);
		privateVar.setAccessible(true);
		return privateVar.get(bean);
	}
}
