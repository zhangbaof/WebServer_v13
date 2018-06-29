package cn.tedu.spring.test;

import java.io.FileInputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class TestCase {

	@Test
	public void testMd5() throws Exception {
		String file = "passwd";
		FileInputStream in = new FileInputStream(file);
		//计算文件摘要
		String md5 = DigestUtils.md5Hex(in);
		System.out.println(md5);
		in.close();
	}
	
	@Test
	public void testStringMd5() {
		String password = "1234";
		String md5 = DigestUtils.md5Hex(password);
		System.out.println(md5);
		String salt = "今天你吃了吗？";
		md5 = DigestUtils.md5Hex(password+salt);
		System.out.println(md5);
		
	}
	
	
	
	
}
