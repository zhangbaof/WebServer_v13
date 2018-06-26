package com.tedu.http;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.tedu.conf.Server;

/**
 * HttpRequest的每一个实例用于表示客户端发送过来的一个Http请求信息
 * @author soft01
 * HTTP请求的格式分为三部分
 * 1：请求行
 * 2：消息头
 * 3：消息正文
 * 
 * 
 * CR:回车（\r）光标回到该行的最开始
 * LF:换行（\n）换到下一行
 * 
 * 1：请求行
 * method url protocol
 * 例如：
 * GET /index.html HTTP/1.1CRLF
 *  POST
 * 2：消息头
 * 根据请求资源的不同消息头中的内容也不
 * 完全一样
 * 消息头中每一个信息都以CRLF结束
 * name:valueCRLF  
 * 消息头中会有若干组信息发送过来，当所有消息头内容发色红完毕后，会单独发送一个
 * CRLF表示消息头中所有内容发送完毕
 * 格式例如：
 * name1:value1CRLF
 * name2:value2CRLF
 * ......
 * CRLF   单独读取到一个CRLF表示消息头全部发送完毕
 * 
 * 3：消息正文（暂时略）
 * 
 */
public class HttpRequest {
	
	/**
	 * 请求行相关信息
	 */

	//请求方法
	private String method;
	//请求资源
	private String url;
	//请求使用的协议版本
	private String protocol;
	
	
	
	/**
	 * 对于请求功能的URL来说，我们分别保存请求的路径以及传递的参数
	 * 例如：
	 * /index.html 对于直接访问资源的不处理
	 *
	 *下面这样处理
	 * /web/regUser?uname=zbfnlr&upwd=111111&upwdconfirm=111111
	 * 
	 * ？左面的作为请求路径
	 * ？右面的作为传递的参数
	 *
	 */
	//请求路径
	private String requestLine;
	
	//参数部分
	private String queryLine;
	
	//保存所有传递过来的参数的Map
	private Map<String,String> parasMap;
	
	
	
	
	
	
	
	/**
	 * 消息头相关信息
	 */
	private Map<String,String> headers;
	
	
	/**
	 * 根据给定的输入流，读取客户端发送过来的所有
	 * Http请求信息，并使用当前创建的HttpRequest
	 * 对象表示这些信息
	 * @param in
	 */
	public HttpRequest(InputStream in) throws Exception{
		/**
		 * 三件事：
		 * 1.解析请求行信息
		 * 2.解析消息头
		 * 3.解析消息正文
		 */
		//解析请求行信息
		parseRequestLine(in);
		//解析消息头
		parseHeaders(in);
		//解析消息正文
		parseEntity(in);
		
	}
	
	/**
	 * 解析消息正文
	 */
	private void parseEntity(InputStream in) {
		
		/**
		 * 通过消息头中获取正文长度
		 */
		if(this.headers.containsKey("Content-Length")) {
			int contentLength = Integer.parseInt(this.headers.get("Content-Length"));
			String contentType = this.headers.get("Content-Type");
			System.out.println(contentType);
			//根据Content-Type判断是否为form表单
			if("application/x-www-form-urlencoded".equals(contentType)) {
				System.out.println("开始处理Post请求中form表单内容！");
				try {
					byte[] data = new byte[contentLength];
					in.read(data);
					String line = URLDecoder.decode(new String(data,"ISO8859-1"),Server.URIENCODING);
					System.out.println(line);
					parseQuery(line);
				}catch(Exception e) {
					
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	
	
	/**
	 * 解析HTTP请求中的所有消息头信息
	 * 格式：
	 * 紧跟在HTTP请求里请求行后面
	 * name1:value1CRLF（每个头信息都以CRLF结尾）
	 * name2:value2CRLF
	 * ..........
	 * namex：valuexCRLF
	 * CRLF（所有头发送完毕后会单独发送一个CRLF）
	 * @param in
	 */
	private void parseHeaders(InputStream in) {
		/**
		 * 实现思路：
		 * 循环读取若干行消息头（以CRLF结尾为一行，调用readLine方法即可），若单独读取到了一个CRLF
		 * 则停止读取，因为所有都读取完了（调用readLine方法返回空字符串则表示只读到一个CRLF），然后
		 * 在读取的过程中将每个消息用“：”拆开，并存入headers这个Map中
		 */
		
		headers = new HashMap<String,String>();
		while(true) {
			String str = readLine(in);
			if(str.isEmpty()) {
				break;
			}
			String key = str.substring(0,str.indexOf(":")).trim();
			String value = str.substring(str.indexOf(":")+1).trim();
			headers.put(key,value);
			
		}
		headers.forEach((k,v)->System.out.println(k+":"+v));
		
	}
	
	
	/**
	 * 解析请求行信息
	 * 格式如：
	 * GET /index.html HTTP/1.1CRLF
	 * @param in
	 */				//创建HttpResponse对象

	private void parseRequestLine (InputStream in) throws Exception{
		/**
		 * 实现思路：
		 * 首先通过输入流读取一行字符串（CRLF结尾）
		 * 因为客户端发送过来的请求中第一行一定是
		 * HTTP协议规定的请求行信息
		 * 如：GET /index.html HTTP/1.1CRLF
		 * 读取到以后按照空格拆分出三部分，分别设置到
		 * 当前对象的method，url，protocol
		 * 上保存
		 * 对于url部分如果时请求功能（url中带有?，并且传递过来了参数）
		 * 我们还需要进一步解析
		 * 
		 * 
		 */
		try {
			//1.读取请求行信息
			String line = readLine(in);
			if(line.length() == 0) {
				throw new RuntimeException("无效请求");
			}
			//2
			String[] array = line.split("\\s");
			method = array[0];
			url = array[1];
			protocol = array[2];
			//进一步解析URL
			parseUrl(this.url);
			
		}catch(Exception e) {
			/**
			 * try中抛出异常，catch中在实际开发中会
			 * 做一个 工作就是记录日志，若该异常不应当
			 * 在当前方法中被解决可以继续在catch中将
			 * 该异常抛出
			 */
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 进一步解析URL
	 * 如果该资源路径中含有？，则将？左面的内容保存到
	 * requestLine属性上，将？右面的内容保存到queryLine属性上，并且在将
	 * queryLine中的各个参数解析并存入到paraMap中
	 * 如果该url不含有？，则不做任何处理，直接将url赋值给requestLine属性即可
	 * @param url
	 */
	public void parseUrl(String url) {
//		parasMap = new HashMap<String,String>();
//		if(url.indexOf("?")!=-1) {
//			String[] array = url.split("\\?");
//			requestLine = array[0];
//			queryLine = array[1];
//			
//			array = queryLine.split("&");
//			for(int i = 0;i<array.length;i++) {
//				String[] arrays = array[i].split("=");
//				System.out.println(arrays[0]+":"+arrays[1]);
//				parasMap.put(arrays[0], arrays[1]);
//			}
//		}else {
//			requestLine = url; 
//			
//		}
		//查找url中？的位置
		int index = url.indexOf("?");
		//判断url中是否含有？
		if(index>=0) {
			requestLine = url.substring(0,index);
			queryLine = decodeUrl(url.substring(index+1));
			parseQuery(queryLine);
		}else {
			requestLine = url;
		}
		
	}
	
	
	private String decodeUrl(String line) {
		String str;
		try {
			
			System.out.println("解码前的内容："+line);
			str = URLDecoder.decode(line,Server.URIENCODING);
			System.out.println("解码后的内容："+str);
			return str;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return line;
	}
	
	
	private void parseQuery(String queryLine) {
		//初始化属性parasMap
		parasMap = new HashMap<String,String>();
		//首先按照&拆分
		String[] data = queryLine.split("&");
		for(String para:data) {
			//每一个参数按照=拆分
			String[] paraArr = para.split("=");
			parasMap.put(paraArr[0],paraArr[1]);
		}
	}
	
	
	
	
	
	
	/**
	 * 通过给定的输入流读取一行字符串（以CRLF结尾）
	 * 并将该字符串返回，返回的字符串中不含有最后的CRLF.
	 * @param in
	 * @return
	 */
	
	private String readLine(InputStream in) {
		try {
			int d = -1;
			StringBuilder builder = new StringBuilder();
			char c1 = 1,c2 = 1;
			while((d = in.read())!=-1) {
				c2 = (char)d;
				if(c1 ==HttpContext.CR&&c2 == HttpContext.LF) {
					break;
				}
				builder.append(c2);
				c1 = c2;
			}
			return builder.toString().trim();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}


	
	
	public String getMethod() {
		return method;
	}


	public String getUrl() {
		return url;
	}


	public String getProtocol() {
		return protocol;
	}


	public Map<String, String> getHeaders() {
		return headers;
	}


	public String getRequestLine() {
		return requestLine;
	}


	public String getQueryLine() {
		return queryLine;
	}


	
	/**
	 * 根据给定的参数名获取参数值
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		return parasMap.get(name);
	}
	
	/**
	 * 获取应用项目名
	 * @return
	 */
	
	public String getContextPath() {
		/**
		 * 常见的requestLine格式：
		 * 目标，获取下面地址中[] 中间的内容
		 * http://localhost:8088[/web]/web/regUser
		 * 
		 * 解析URL后，request中的requestLine如下
		 *  /web/web/regUser
		 *  /web/index.html
		 *  /index.html
		 */
		
		if(requestLine.length()>1) {
			int index = requestLine.indexOf("/",1);
			if(index>0) {
				return requestLine.substring(0,index);
			}
		}
		return "";
	}
	
//	
//	public HttpRequest() {}
//	public static void main(String[] args) {
//		HttpRequest request = new HttpRequest();
//		request.requestLine = "/web/index.html";
//		String str = request.getContextPath();
//		System.out.println(str);
//	}


}






































