/**
 * File Name: CodeConfig.java
 * Date: 2020-04-17 16:02:01
 */
package com.tzrcb.dispatch.config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Description: 代码配置类
 * @author shenzulun
 * @date 2020-04-17
 * @version 1.0
 */
public class CodeConfig {
	private static final ConcurrentMap<String, String> map = new ConcurrentHashMap<String, String>();
	/**
	 * 交易成功
	 */
	public static final String SUCCESS = "0000";
	/**
	 * 通用失败
	 */
	public static final String ERR_9999 = "9999";
	/**
	 * 报文校验不通过
	 */
	public static final String ERR_1001 = "1001";
	/**
	 * 重复交易
	 */
	public static final String ERR_2222 = "2222";
		
	static {
		map.put(SUCCESS, "交易成功");
		map.put(ERR_9999, "交易失败");
		map.put(ERR_1001, "报文内容校验不通过");
		map.put(ERR_2222, "重复交易");
	}
	
	public static String getCodeMessage(String code) {
		return map.get(code);
	}
}
