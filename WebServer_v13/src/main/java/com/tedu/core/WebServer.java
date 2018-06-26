package com.tedu.core;
/**
 * WebServer
 * @author soft01
 *
 */

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.tedu.conf.Server;
import com.tedu.http.HttpContext;
import com.tedu.http.HttpRequest;
import com.tedu.http.HttpResponse;
import com.tedu.http.HttpServlet;
import com.tedu.webconf.ApplicationConfig;

public class WebServer {

	private ServerSocket server;
	/**
	 * 线程池，用来管理多客户端并发请求处理
	 */
	private ExecutorService threadPool;

	public WebServer() {
		
		try {
			System.out.println("初始化服务端......");
			server = new ServerSocket(Server.PORT);
			
			threadPool = Executors.newFixedThreadPool(Server.MAX_THREADS);
			
			System.out.println("服务端初始化完毕！");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void start() {
		try {
			while(true) {
				Socket socket = server.accept();
				InetAddress address = socket.getInetAddress();
				String str = address.getHostAddress();
				ClientHandler handler = new ClientHandler(socket);
				threadPool.execute(handler);
				System.out.println("一个客户端连接:"+str);
			}
			/**
			 * 浏览器与服务端建立连接后，会发送过来HTTP
			 * 请求
			 * HTTP：超文本传输协议
			
			 * 
			 */
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	class ClientHandler implements Runnable{
		private Socket socket;
		public ClientHandler(Socket socket) {
			this.socket = socket;
		}
		public void run() {
			try {
				//创建HttpRequest对象
				HttpRequest request = new HttpRequest(socket.getInputStream());
				
				HttpResponse response = new HttpResponse(socket.getOutputStream());
				
				//是否去首页
				if(request.getUrl().equals("/")) {
					
					toWelcomPage(request,response);
					//查看是否请求Servlet
				}else if(checkServletRequest(request)) {
						try {
							executeServlet(request,response);
						}catch(Exception e) {
							e.printStackTrace();
						}
				}else{
					responseFile(request,response);
				}
				response.flush();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				//与客户端断开连接后的操作
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		

		/**
		 * 调转首页
		 * @param request
		 * @param response
		 */
		private void toWelcomPage(HttpRequest request,HttpResponse response) {
			/**
			 * 遍历所有配置的首页信息，找到可用首页并进行调转
			 */
			boolean have = false;
			for(String path:Server.welcomeFileList) {
				File welcomePage = new File("web-apps"+File.separator+path);
				if(welcomePage.exists()) {
					//重定向到该页面
					response.sendRedirect(path);
					have = true;
					break;
				}
			}
			if(!have) {
				response404(request,response);
			}
		}
		
		
		
		
		
		
		/**
		 * 根据request判断该请求是否为请求Servlet
		 * @return
		 */
		private boolean checkServletRequest(HttpRequest request) {
			/**
			 * 1：根据请求获取对应应用项目的配置文件
			 * 2：根据请求路径查看配置文件中是否有对应的Servlet,并将判断结果返回
			 */
			//根据请求的项目找到对应的web.xml配置文件
			
			ApplicationConfig config = Server.appConfigs.get(request.getContextPath());
			//找到对应应用项目的配置文件
			if(config!=null) {
				return config.getServletMappingMap().containsKey(request.getRequestLine());
			}
			
			//返回false表示该请求不是对应Servlet
			return false;
		}
		
		/**
		 * 执行Servlet
		 * @param request
		 * @param response
		 */
		private void executeServlet(HttpRequest request,HttpResponse response) {
			try {
				ApplicationConfig config = Server.appConfigs.get(request.getContextPath());
				
				String servletName = config.getServletMappingMap().get(request.getRequestLine());
				String servletClassName = config.getServletMap().get(servletName);

			
				ClassLoader loader = ClassLoader.getSystemClassLoader();
				Class cls = loader.loadClass(servletClassName);
				HttpServlet o = (HttpServlet)cls.newInstance();
				o.service(request, response);
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		/**
		 * 响应一个静态资源(静态页面，图片)
		 * @param url
		 */
		private void responseFile(HttpRequest request,HttpResponse response) {
			try {
				File	file = new File("web-apps"+request.getRequestLine());
				if(file.exists()&&file.isFile()) {
					/**
					 * 发送响应正文，实际给客户端的数据
					 * 将客户端要访问的文件数据全部发送给客户端
					 */
					//响应用户请求的资源
					//设置状态码
					response.setStatusCode(HttpContext.STATUS_CODE_OK);
					//设置响应头相关信息
					/**
					 * 根据文件名的后缀获取对应的Content-Type的值
					 */
					String fileName = file.getName();
					int index = fileName.lastIndexOf(".")+1;
					String extension = fileName.substring(index);
					String contentType = HttpContext.MIME_MAPPING.get(extension);
					
					//设置Content-Type
					response.setContentType(contentType);
					response.setContentLength(file.length());
					//设置实体数据
					response.setEntity(file);
				}else {
					//响应404页面
					response404(request,response);
					
					
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 响应404页面
		 * @param request
		 * @param response
		 */
		private void response404(HttpRequest request , HttpResponse response) {
			//设置状态行为404
			response.setStatusCode(HttpContext.STATUS_CODE_NOT_FOUND);
			response.setContentType("text/html");
			File notFoundPage = new File("web-apps"+File.separator+"ROOT"+File.separator+"404.html");
			response.setContentLength(notFoundPage.length());
			response.setEntity(notFoundPage);
		}
		
		
	}
	public static void main(String[] args) {
		try {
			WebServer server = new WebServer();
			server.start(); 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
