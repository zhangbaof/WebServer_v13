package com.tedu.servlet;

import java.io.File;

import com.tedu.http.HttpRequest;
import com.tedu.http.HttpResponse;
import com.tedu.http.HttpServlet;

public class UpdateServlet extends HttpServlet{

	public void service(HttpRequest request,HttpResponse response) {
		System.out.println("已进入修改页面");
		String url = File.separator+"web"+File.separator+"web"+File.separator+"update.html";
		forward(request,response,url);
	}
}
