package com.tedu.http;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Http协议相关定义信息
 * @author soft01
 *
 */
public class HttpContext {

	public static final int CR = 13;
	public static final int LF = 10;
	
	
	/**
	 * 状态码相关常量
	 */
	
	
	/**
	 * 状态码200
	 * 表示请求处理成功，正常响应客户端
	 */
	public static final int STATUS_CODE_OK = 200;
	
	/**
	 * 状态码：302
	 * 表示重定向到指定资源
	 */
	public static final int STATUS_CODE_REDIRECT = 302;
	
	/**
	 * 状态码：404
	 * 表示服务端未找到客户端请求的资源
	 */
	public static final int STATUS_CODE_NOT_FOUND = 404;
	/**
	 * 状态码：500
	 * 表示服务器发生了一个错误，导致无法完成客户端的请求
	 */
	public static final int STATUS_CODE_ERRON = 500;
	
	/**
	 * 状态码对应的状态描述
	 * K：状态码
	 * V：状态描述
	 */
	public static final Map<Integer,String> STATUS_CODE_MAPPING = new HashMap<Integer,String>();
	/**
	 * Content-Type类型映射
	 * key：资源类型
	 * value：Http协议对应该类型的值
	 * 
	 * 
	 * 例如：
	 * key：jpeg
	 * value:image/jpeg
	 */
	public static final Map<String,String> MIME_MAPPING = new HashMap<String,String>();
	
	static {
		//初始化HttpContext静态资源
		//1初始化状态码对应的状态描述信息
		initCodeMapping();
	}
	public static void initMimeMapping(Element root) {
//		MIME_MAPPING.put("html", "text/html");
//		MIME_MAPPING.put("jpeg", "image/jpeg");
//		MIME_MAPPING.put("jpg", "image/jpeg");
//		MIME_MAPPING.put("gif","image/gif");
//		MIME_MAPPING.put("png", "image/png");
//		MIME_MAPPING.put("ico", "image/x-icon");
		
		
		
		/**
		 * 解析当前目录下的子目录conf中的文件web.xml
		 * 将该文件中所有<mime-mapping>标签中内容存入
		 * MIME_MAPPING这个Map中
		 * 其中key为<extension>标签中的文本信息
		 * value为<mime-type>标签中的文本信息
		 */
		
		try {
//			SAXReader reader = new SAXReader();
//			Document doc = reader.read(new FileInputStream("conf"+File.separator+"web.xml"));
//			System.out.println("读取完毕！");
//			
//			Element root = doc.getRootElement();
			System.out.println(root.getName());
			List<Element> list = root.elements("mime-mapping");
			for(Element emp:list) {
//				Element extension = emp.element("extension");
//				String key = extension.getText();
				String key = emp.elementText("extension");
//				Element mimeType = emp.element("mime-type");
//				String value = mimeType.getText();
				String value = emp.elementText("mime-type");
				MIME_MAPPING.put(key, value);
			}
			System.out.println(MIME_MAPPING.size());
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void initCodeMapping(){
		STATUS_CODE_MAPPING.put(200,"ok");
		STATUS_CODE_MAPPING.put(302, "found");
		STATUS_CODE_MAPPING.put(404, "not found");
		STATUS_CODE_MAPPING.put(500, "internal server error");
	}
	

	
	
}

























