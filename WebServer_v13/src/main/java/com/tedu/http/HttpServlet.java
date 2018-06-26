package com.tedu.http;

import java.io.File;

/**
 * 用户处理一个Http请求的业务
 * @author soft01
 *
 */
public class HttpServlet {
	public void service(HttpRequest request,HttpResponse response) {
		if("GET".equals(request.getMethod().toUpperCase())) {
			doGet(request,response);
		}else if("POST".equals(request.getMethod().toUpperCase())){
			doPost(request,response);
		}
	}
	public void doGet(HttpRequest request,HttpResponse response) {
		
	}
	public void doPost(HttpRequest request,HttpResponse response) {
		
	}
	/**
	 * 调转到指定资源
	 * @param request
	 * @param response
	 * @param url
	 */
	public void forward(HttpRequest request,HttpResponse response,String url) {
		File file = new File("web-apps"+url);
		response.setStatusCode(HttpContext.STATUS_CODE_OK);
		response.setContentType(HttpContext.MIME_MAPPING.get("html"));
		response.setContentLength(file.length());
		response.setEntity(file);
	}
}
