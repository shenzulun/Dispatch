/**
 * File Name: CommonTest.java
 * Date: 2020-04-17 14:39:47
 */
package com.tzrcb.dispatch;

import java.nio.charset.Charset;

import org.junit.Test;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.tzrcb.dispatch.util.CommonUtils;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-04-17
 * @version 1.0
 */
public class CommonTest {
	
	@Test
	public void test1() {
		Hasher hasher = Hashing.sha256().newHasher();  
		hasher.putString("123456", Charset.forName("UTF-8"));
		String str = hasher.hash().toString();
		System.out.println(str);
	}
	
	/**
	 * 呼叫中心
	 */
	@Test
	public void test2() {
		Hasher hasher = Hashing.sha256().newHasher();  
		hasher.putString("callcenter", Charset.forName("UTF-8"));
		String str = hasher.hash().toString();
		str = str.substring(16, 48);
		System.out.println(str);
	}
	
	/**
	 * 短信平台
	 */
	@Test
	public void test3() {
		Hasher hasher = Hashing.sha256().newHasher();  
		hasher.putString("sms", Charset.forName("UTF-8"));
		String str = hasher.hash().toString();
		str = str.substring(16, 48);
		System.out.println(str);
	}
	
	@Test
	public void test4() {
		String msg = "{\"transNo\":\"10010103\",\"serno\": \"123456\",\"source\":\"1003\",\"target\":\"1001\",\"transTime\":\"20200416163501001\",\"data\":{\"phone\":\"13586026616\",\"codeName\":\"0101\",\"sysName\":\"绩效系统\"}}";
		String salt = "68de1e4c2b3fe61effbd7f06611a6ec0";
		System.out.println(CommonUtils.encodeSHA256(msg, salt));
	}
	
	/**
	 * 短信平台
	 */
	@Test
	public void test5() {
		Hasher hasher = Hashing.sha256().newHasher();  
		hasher.putString("jxxt", Charset.forName("UTF-8"));
		String str = hasher.hash().toString();
		str = str.substring(16, 48);
		System.out.println(str);
	}

}
