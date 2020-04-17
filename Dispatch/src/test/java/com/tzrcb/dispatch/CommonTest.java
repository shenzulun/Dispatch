/**
 * File Name: CommonTest.java
 * Date: 2020-04-17 14:39:47
 */
package com.tzrcb.dispatch;

import java.nio.charset.Charset;

import org.junit.Test;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

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

}
