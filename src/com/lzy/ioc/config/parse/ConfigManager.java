package com.lzy.ioc.config.parse;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lzy.ioc.config.Bean;
import com.lzy.ioc.config.Property;

/**
 * 读取配置文件，返回Bean
 * 
 * @author admin
 *
 */
public class ConfigManager {

	/**
	 * 将配置文件转换成Bean返回 
	 * 
	 * @param path
	 * @return
	 */
	public static Map<String, Bean> getConfig(String path){
		//存储解析出来的bean
		Map<String, Bean> beanMap = new HashMap<>();
		
		// 1.创建解析器
		SAXReader saxReader = new SAXReader();
		
		// 2.读取配置 文件
		InputStream is = ConfigManager.class.getResourceAsStream(path);
		Document  document = null;
		try {
			document = saxReader.read(is);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException("请检查xml配置文件");
		}
		
		// 3.定义xpath表达式,用于匹配所有bean
		String xpath = "//bean";
		
		// 4.从document中找出所有的bean元素
		List<Element> beanNodes = document.selectNodes(xpath);
		if(beanNodes != null){
			for (Element beanNode : beanNodes) {
				Bean bean =  new Bean();
				// 将xml中的bean描述信息封装到自定义的Bean对象中
				String name = beanNode.attributeValue("name");
				String className = beanNode.attributeValue("class");
				bean.setName(name);
				bean.setClassName(className);
				
				// 将xml中的property封装到bean中
				List<Element> children = beanNode.elements("property");
				if(children != null){
					for (Element child : children) {
						// 获取xml中的Property属性的信息，并封装到Property对象中 
						Property property = new Property();
						String pName = child.attributeValue("name");
						String pValue = child.attributeValue("value");
						String pRef = child.attributeValue("ref");
						
						property.setName(pName);
						property.setRef(pRef);
						property.setValue(pValue);
						
						// 将property封装到bean对象中
						bean.getProperties().add(property);
						
					}
					// 将bean存储到beanMap中
					beanMap.put(name, bean);
				}
			}
		}
		
		
		return beanMap;
	}
	
	
}
