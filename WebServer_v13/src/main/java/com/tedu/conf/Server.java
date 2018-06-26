package com.tedu.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileFilter;


import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tedu.http.HttpContext;
import com.tedu.webconf.ApplicationConfig;

/**
 * 服务端配置数据
 * 通过解析conf/server.xml文件得到配置数据
 *  
 * 通过解析所有项目（web-apps中每个目录为一个web项目）
 * 目录中WEB-INF目录中的web.xml文件，保存所有web项目的配置信息
 * 
 * 注：v11版本，先临时解析项目根目录中的web.xml，配置一个，并没有分析web-apps目录中的各个项目配置文件
 * 
 * @author soft01
 *                        
 */
public class Server {
	
	/**
	 * 字符集
	 */
	public static String URIENCODING;
	/**
	 * 服务端的服务端口
	 */
	public static  int PORT;
	/**
	 * HTTP协议版本
	 */
	public static  String PROTOCOL;
	/**
	 * 服务端最大线程数
	 */
	public static  int MAX_THREADS;
	
	/**
	 * 每个项目对应的配置文件web.xml信息
	 * 当前Map的结构
	 * key:web-apps中每个项目的名字（web-apps中每个目录的名字作为该项目的名字）
	 * value:对应该项目中WEB-INFO中web.xml文件的配置信息，一个ApplicationConfig实例
	 */
	public static Map<String,ApplicationConfig> appConfigs;
	/**
	 * 服务器默认首页
	 */
	public static List<String> welcomeFileList;
	
	
	private static SAXReader reader = new SAXReader();
	
	
	
	
	static {
		//初始化服务端主配置信息conf/server.xml
		initServerConfig();
		//初始化首页信息，以及HttpContext相关信息conf/web.xml
		initWebConfig();
		//初始化所有web项目配置信息
		initApplicationConfigs();
	}
	/**
	 * 初始化WevServer的主要配置信息
	 * 端口，线程池信息等
	 */
	private static void initWebConfig() {
		welcomeFileList = new ArrayList<String>();
		try {
			/**
			 * 加载conf/web.xml初始化首页信息
			 * 再初始化HttpContext的相关信息
			 */
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new FileInputStream("conf/web.xml"));
			Element root = doc.getRootElement();
			
			/**
			 * 获取<welcome-file-list>标签中的每一项，并存入welcomeFile
			 */
			Element fileList = root.element("welcome-file-list");
			List<Element> list = fileList.elements();
			for(Element e:list) {
				String key = e.getText();
				welcomeFileList.add(key);
			}
			
			/**
			 *将根标签传递给HttpContext的初始化Content-Type映射信息 
			 */
			HttpContext.initMimeMapping(root);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 初始化WebServer的主要配置信息，端口，线程池信息等
	 */
	private static void initServerConfig() {
		//解析server.xml文件
		try {
			Document doc = reader.read(new FileInputStream("conf"+File.separator+"server.xml"));
			Element root = doc.getRootElement();
			Element connectorEle = root.element("Connector");
			URIENCODING = connectorEle.attributeValue("URIEncoding");
			PORT = Integer.parseInt(connectorEle.attributeValue("port"));
			PROTOCOL = connectorEle.attributeValue("protocol");
			Element maxThread = root.element("Executor");
			MAX_THREADS = Integer.parseInt(maxThread.attributeValue("maxThreads"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 初始化每个项目配置信息
	 */
	private static void initApplicationConfigs() {
		appConfigs = new HashMap<String,ApplicationConfig>();
		
		
		
		File root = new File("web-apps");
		/**
		 * 获取web-apps下的所有应用项目目录
		 */
		File[] applications = root.listFiles((f)->f.isDirectory());
		/**
		 * 目标，将web-apps中每个应用项目中的WEB-INF目录里的web.xml初始化为一个ApplicationConfig对象，并
		 * 将应用目录的名字做为key，ApplicationConfig对象作为value，存入得到Servlet的静态属性：appConfigs当中
		 */
		
		for(File application:applications) {
			File[] webInfo = application.listFiles((f)->f.getName().equals("WEB-INF"));
			if(webInfo.length>0) {
				File[] webXmlFile = webInfo[0].listFiles((f)->f.getName().equals("web.xml"));
				if(webXmlFile.length>0) {
					ApplicationConfig config = new ApplicationConfig(webXmlFile[0]);
					appConfigs.put("/"+application.getName(), config);
				}
			}
		}
	}
	
	
	
	
	
}
