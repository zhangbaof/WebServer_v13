package com.tedu.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tedu.conf.Server;

/**
 * 表示一个Http的响应信息
 * 
 * HTTP协议要求一个响应要求三部分:状态行，响应头，响应正文
 * 
 * 状态行格式：
 * 协议版本 状态代码 状态描述CRLF
 * 
 * 例如：
 * HTTP/1.1 200 OK
 * 
 * 
 * 响应头格式：（与消息头中的格式相同）
 * name1:value1CRLF
 * name2:value2CRLF
 * ..........
 * namex:valuexCRLF
 * CRLF(当读发送一个CRLF表示响应头发送完毕！)
 * 
 * 响应正文为实际数据（字节数据）
 * 				 
 * @author soft01
 *
 */
public class HttpResponse {
	
	/**
	 * 状态代码
	 */
	private int statusCode;
	
	/**
	 * 响应头
	 */
	private Map<String,String> headers;
	
	/**
	 * 响应实体
	 * 实际要给客户端发送的数据
	 */
	
	private File entity;
	
	/**
	 * 对应客户端的输出流
	 * 通过这个流就可以将响应信息发送回给客户端
	 */
	private OutputStream out;

	public HttpResponse(OutputStream out) {
		this.out = out;
		this.headers = new HashMap<String,String>();
	}
	/**
	 * 将当前HttpResponse的响应内容发送回给客户端
	 */
	public void flush() {
		/**
		 * 一个Http响应会发送三部分：
		 * 1：发送状态行信息
		 * 2：发送响应头信息
		 * 3：发送响应正文信息
		 */
		//发送状态行信息
		sendResponseStatusLine();
		//发送响应头信息
		sendResponseHeaders();
		//发送响应正文
		sendResponseContent();
		
		
	}
	
	
	
	private void sendResponseContent() {
		/**
		 * 发送文件类型的响应正文时，先判断
		 * 若没有文件，或者entity表示的时目录则不发送
		 */
		if(entity == null||entity.isDirectory()) {
			return;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(entity);
			byte[] data = new byte[1024*10];
			int len = -1;
			while((len = fis.read(data))!=-1) {
				out.write(data,0,len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	/**
	 * 发送响应头信息
	 */
	private void sendResponseHeaders() {
			
			/**
			 * Content-Type：说明实际响应的数据是什么类型数据
			 * 							文件，图片，HTML页面等等
			 * Content-Length：说明实际响应的数据长度（单位是字节）
			 * 
			 */
//			headers.forEach((k,v)->println(k+":"+v));
			Set<Entry<String,String>> set = headers.entrySet();
			for(Entry<String,String> e:set) {
				String line = e.getKey()+":"+e.getValue();
				
				println(line);
			}
			//单独发送一个CRLF表示响应头内容发送完毕
			println("");
			
			
			
	}
	
	
	/**
	 * 发送状态行信息
	 */
	private void sendResponseStatusLine() {
		String line = Server.PROTOCOL+" "+statusCode+" "+HttpContext.STATUS_CODE_MAPPING.get(statusCode);
		println(line);
	}
	
	
	/**
	 * 将给定的字符串发送给客户端
	 * 写完该字符串后会自动发送CRLF
	 * 
	 * @param line
	 */
	private void println(String line) {
		
		try {
			out.write(line.getBytes("ISO8859-1"));
			out.write(HttpContext.CR);//CR
			out.write(HttpContext.LF);//LF
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public Map<String, String> getHeader() {
		return headers;
	}
	public void setHeader(Map<String, String> header) {
		this.headers = header;
	}
	public File getEntity() {
		return entity;
	}
	public void setEntity(File entity) {
		this.entity = entity;
	}
	public OutputStream getOut() {		

		return out;
	}
	public void setOut(OutputStream out) {
		this.out = out;
	}
	
	/**
	 * 设置响应头：Content-Type信息
	 * @param mime
	 * @see HttpContext
	 */
	public void setContentType(String mime) {
		this.headers.put("Content-Type", mime);
	}
	/**
	 * 设置响应头：Content-Length信息
	 * @param length
	 * @see HttpContext
	 */
	public void setContentLength(long length) {
		this.headers.put("Content-Length", String.valueOf(length));
	}
	
	
	/**
	 * 要求客户端重定向到指定路径
	 * 
	 */
	
	public void sendRedirect(String url) {
		//设置响应中的状态行的转化状态代码为302（重定向）
		this.setStatusCode(HttpContext.STATUS_CODE_REDIRECT);
		/**
		 * 在响应头中添加一个为重定向指定的路径Location头，它的值就是告知浏览器访问该地址
		 */
		this.headers.put("Location",url);
	}
	
	
	
	
	
	
	
	
	
	
	
}


























