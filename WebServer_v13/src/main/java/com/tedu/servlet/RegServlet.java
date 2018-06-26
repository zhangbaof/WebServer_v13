package com.tedu.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.tedu.http.HttpRequest;
import com.tedu.http.HttpResponse;
import com.tedu.http.HttpServlet;

/**
 * 用来处理用户注册业务
 * @author soft01
 *
 */
public class RegServlet extends HttpServlet{

	
	
	
	public void service(HttpRequest request,HttpResponse response) {
		System.out.println("准备进行用户注册！");
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
		/**
		 * 将用户的注册信息写入服务端本地的一个
		 * 文件 user.txt中
		 */
			//获取用户名
			String uname = request.getParameter("uname");
			String upwd = request.getParameter("upwd");
			System.out.println("uname:"+uname);
			System.out.println("upwd:"+upwd);
			
			
			
			
			/**
			 *首先检查该用户是否已经注册过，若注册过则调转到提示页面regiter_info.html 
			 */
			boolean have = false;
			File infoFile = new File("user.txt");
			if(infoFile.exists()) {
                     
				FileInputStream fis = new FileInputStream("user.txt");
				InputStreamReader isr = new InputStreamReader(fis,"utf-8");
				br = new BufferedReader(isr);
				String str = null;
				while((str = br.readLine())!=null) {
					String name = str.substring(0,str.indexOf(":"));
					if(uname.equals(name)) {
						have = true;
					}
				}
				
			}
			
			File file = null;
			if(have) {
				String url = File.separator+"web"+File.separator+"web"+File.separator+"register_info.html";
				forward(request,response,url);
			}else {
				pw = new PrintWriter(
						new OutputStreamWriter(
								new FileOutputStream("user.txt",true),"utf-8"
								)
						);
				pw.println(uname+":"+upwd);
				pw.flush();
				System.out.println("注册完毕！");
				
				/**
				 * 响应用户注册成功页面
				 */
//				file = new File("web-apps"+File.separator+"web"+File.separator+"web"+File.separator+"register_success.html");
				
				String url = File.separator+"web"+File.separator+"web"+File.separator+"register_success.html";
				forward(request,response,url);
			}
			
//			response.setStatusCode(HttpContext.STATUS_CODE_OK);
//			response.setContentType(HttpContext.MIME_MAPPING.get("html"));
//			response.setContentLength(file.length());
//			response.setEntity(file);
			
			
			
			
			
		
		}catch(Exception e) {
			e.printStackTrace();
			
			String url = File.separator+"web"+File.separator+"web"+File.separator+"500.html";
			forward(request,response,url);
			
			
		}finally {
			if(br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(pw!=null) {
				pw.close();
			}
		}
	}
	
	
	
	
	
}
