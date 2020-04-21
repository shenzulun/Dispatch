/**
 * File Name: DataCheck.java
 * Date: 2020-04-17 16:13:04
 */
package com.tzrcb.dispatch.util.check;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description: 数据校验注解
 * @author shenzulun
 * @date 2020-04-17
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DataCheck {
	
	/**
	 * 最大长度
	 * @return
	 */
	int maxLength() default 0;
	/**
	 * 日期格式
	 * @return
	 */
	String datePattern() default "";
	/**
	 * 最大
	 * @return
	 */
	int max() default 0;
	
	boolean required() default false;
}

