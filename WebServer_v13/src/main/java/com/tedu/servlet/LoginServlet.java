package com.tedu.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.tedu.http.HttpContext;
import com.tedu.http.HttpRequest;
import com.tedu.http.HttpResponse;
import com.tedu.http.HttpServlet;

public class LoginServlet extends HttpServlet{

	public void service(HttpRequest request, HttpResponse response) {
		String lname = request.getParameter("lname");
		String lwd = request.getParameter("lwd");
		try {
			boolean have = false;
			FileInputStream fis = new FileInputStream("user.txt");
			InputStreamReader isr = new InputStreamReader(fis,"utf-8");
			BufferedReader br = new BufferedReader(isr);
			String str = null;
			while((str = br.readLine())!=null) {
				String[] data = str.split(":");
				if(lname.equals(data[0])&&lwd.equals(data[1])) {
					have = true;
					break;
				}
			}
			File file = null;
			if(have) {
				String url = File.separator+"web"+File.separator+"web"+File.separator+"login_success.html";
				forward(request,response,url);
			}else {
				String url = File.separator+"web"+File.separator+"web"+File.separator+"login_info.html";
				forward(request,response,url);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String url = File.separator+"web"+File.separator+"web"+File.separator+"500.html";
			forward(request,response,url);
		}
		
	}

}
