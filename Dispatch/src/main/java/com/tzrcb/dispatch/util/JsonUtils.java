/**
 * File Name: JsonUtils.java
 * Date: 2020-04-16 19:00:30
 */
package com.tzrcb.dispatch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jfinal.kit.JsonKit;

/**
 * Description: Json工具类
 * @author shenzulun
 * @date 2020-04-16
 * @version 1.0
 */
public class JsonUtils {
	protected static Logger log = LoggerFactory.getLogger(JsonUtils.class);	

	
	/**
	 * json字符串 -> 对象
	 * @param <T>
	 * @param jsonStr
	 * @param parametrizedClass
	 * @param parameterClasses
	 * @return
	 */
	public static <T> T toObject(String jsonStr, Class<T> parametrizedClass, Class<?>... parameterClasses) {
		T result = null;
		try {
			if(parameterClasses != null && parameterClasses.length > 0) {
				JavaType javaType = TypeFactory.defaultInstance().constructParametricType(parametrizedClass, parameterClasses);
				result = new ObjectMapper().readValue(jsonStr, javaType);
			}else {
				result = new ObjectMapper().readValue(jsonStr, parametrizedClass);
			}
		} catch (JsonMappingException e) {
			log.error("json转换出错", e);
		} catch (JsonProcessingException e) {
			log.error("json转换出错", e);
		}
		return result;
	}
	
	/**
	 * object -> json
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		return JsonKit.toJson(obj);
	}
	
}
