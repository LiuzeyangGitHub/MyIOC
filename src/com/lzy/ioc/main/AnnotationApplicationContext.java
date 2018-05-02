package com.lzy.ioc.main;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.lzy.ioc.annotation.Autowired;
import com.lzy.ioc.annotation.Bean;
import com.lzy.ioc.utils.BeanUtils;
/**
 * bean 工厂，使用注解注入bean的bean工厂
 * 
 * 
 * @author admin
 *
 */
public class AnnotationApplicationContext extends AbstraBeanFactory {

	/**
	 * 初始化bean工厂
	 * 
	 * @param packageName
	 */
	public AnnotationApplicationContext(String packageName) {
		// 1.获取扫描根包的实际路径
		scanPackage(packageName);
		// 为容器内的bean注入属性
		injectionField();
	}
	
	
	/**
	 * 扫描给的包下的类，并实例化到容器中，此处只实例化，不做属性注入
	 * 
	 * @param packageName
	 */
	public void scanPackage(String packageName){
		String currentpath = ClassLoader.getSystemResource("").getPath();
		String filePath = currentpath + (packageName.replace(".", "\\"));
		List<String> annotationClasses = new ArrayList<>();
		getAnnotationClassName(filePath, annotationClasses);
		for (String path : annotationClasses) {
			Class<?> clazz = null;
			try {
				clazz = Class.forName(path);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			Annotation[] annotations = clazz.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof Bean) {
					Object newInstance = null;
					try {
						newInstance = clazz.newInstance();
						String beanName = path.substring(path.lastIndexOf(".") + 1);
						char firstChar = Character.toLowerCase(beanName.charAt(0));
						String replaceFirst = beanName.replaceFirst(beanName.substring(0, 1),
								Character.toString(firstChar));
						context.put(replaceFirst, newInstance);
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}
	
	/**
	 * 向容器中注入属性
	 * 使用的set方法 注入的，所有必须包含set方法
	 * 
	 */
	public void injectionField(){
		Set<Entry<String, Object>> beans = context.entrySet();
		for (Entry<String, Object> beanEntry : beans) {
			Object bean = beanEntry.getValue();
			String beanName  = beanEntry.getKey();
			// 获取需要注入的属性
			Field[] declaredFields = bean.getClass().getDeclaredFields();
			for (Field field : declaredFields) {
				Annotation[] fieldAnnotions = field.getAnnotations();
				// 处理字段上的注入注解
				for (Annotation fieldAnnotation : fieldAnnotions) {
					String fieldName = field.getName();
					if(fieldAnnotation instanceof Autowired){
						// 判断容器中是否有该bean
						if(context.containsKey(fieldName)){
							// 将容器中的值通过set方法注入到bean中
							Method getMethod = BeanUtils.getWriteMethod(bean, fieldName);
							try {
								getMethod.invoke(bean, context.get(fieldName));
							} catch (IllegalAccessException | IllegalArgumentException
									| InvocationTargetException e) {
								e.printStackTrace();
							}
						
						}
					}else{
						// 容器中没有该值，需要创建该bean
						//  TODO
						throw new RuntimeException("请检查"+ beanName + "类中的" + fieldName + "字段是否已经注入到容器中");
					}
				}
				break;
			}
		}
		
	}
	
	
	

	/**
	 * 获取路径下的所有全类名
	 * 
	 * @param filePat
	 * @param annotationClasses
	 */
	public void getAnnotationClassName(String filePath, List<String> annotationClasses) {

		String currentpath = ClassLoader.getSystemResource("").getPath();

		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		for (File childFile : childFiles) {
			if (childFile.isDirectory()) {
				// 目录
				getAnnotationClassName(childFile.getPath(), annotationClasses);
			} else {
				// 文件
				String childPath = childFile.getPath();
				if (childPath.endsWith(".class")) {
					// 是class文件
					String packageNameOfClass = childPath.substring(currentpath.length() - 1, childPath.length() - 6)
							.replace("\\", ".");
					annotationClasses.add(packageNameOfClass);
				}
			}
		}
	}

}
