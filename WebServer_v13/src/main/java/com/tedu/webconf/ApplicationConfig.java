package com.tedu.webconf;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 对应项目WEB-INF目录中web.xml中的配置信息
 * @author soft01
 */
public class ApplicationConfig {	
	
	/**
	 * 保存web.xml文件中所有<servlet>中的信息
	 * key：子标签<servlet-name>中间的文本信息
	 * value：子标签<servler-class>中间的文本信息
	 */
	private Map<String,String> servletMap;
	
	/**
	 * 保存web.xml文件中所有<servlet-mapping>中的信息
	 * key：子标签<url-pattern>中间的文本信息
	 * value：子标签<servlet-name>中间的文本信息
	 */
	private Map<String,String> servletMappingMap;
	
	/**
	 * 根据给定的文件初始化配置对象
	 * 某个项目WEB-INF目录中的web.xml文件
	 * @param file
	 */
	public ApplicationConfig(File file) {
		servletMap = new HashMap<String,String>();
		servletMappingMap = new HashMap<String,String>();
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(file);
			Element root = doc.getRootElement();
			System.out.println(root.getName());
			
			/**
			 * 解析所有<servlet>标签
			 */
			List<Element>list = root.elements("servlet");
			System.out.println(list.size());
			for(Element emp:list) {
				String key = emp.elementText("servlet-name");
				String value = emp.elementText("servlet-class");
				System.out.println(key+":"+value);
				servletMap.put(key, value);
			}
			
			List<Element> list1 = root.elements("servlet-mapping");
			for(Element emp:list1) {
				String key = emp.elementText("url-pattern");
				String value = emp.elementText("servlet-name");
				System.out.println(key+":"+value);
				servletMappingMap.put(key, value);
			}
			
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		
	}

	public Map<String, String> getServletMap() {
		return servletMap;
	}

	public Map<String, String> getServletMappingMap() {
		return servletMappingMap;
	}
	
//	public static void main(String[] args) {
//		File file = new Document doc = reader.read(file);
//		ApplicationConfig app = new ApplicationConfig(file);
//	}
	
	
	
}
