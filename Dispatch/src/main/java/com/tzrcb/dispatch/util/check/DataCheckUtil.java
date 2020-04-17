/**
 * File Name: DataCheckUtil.java
 * Date: 2020-04-17 16:14:11
 */
package com.tzrcb.dispatch.util.check;

import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.belucky.easytool.util.CommonUtils;

/**
 * Description: 数据校验工具类
 * @author shenzulun
 * @date 2020-04-17
 * @version 1.0
 */
public class DataCheckUtil {
	protected static Logger log = LoggerFactory.getLogger(DataCheckUtil.class);
	
	/**
	 * 校验数据
	 * @param model
	 * @return
	 */
	public static String checkData(Object model){
		if(model == null){
			return null;
		}
		StringBuffer buff = new StringBuffer();
		Class<?> cls = model.getClass();
		Field[] fields = CommonUtils.getAllFields(cls);
		for(Field field : fields){
			DataCheck dataCheck = field.getAnnotation(DataCheck.class);
			if(dataCheck != null){
				Object value = CommonUtils.invoke(model, field.getName());
				if(value != null){
					int maxLength = dataCheck.maxLength();
					if(maxLength > 0 && String.valueOf(value).length() > maxLength){
						String errMsg = "字段[" + field.getName() + "],值[" + value + "],长度超长,最大长度[" + dataCheck.maxLength() + "]";
						buff.append(errMsg).append("|");
						log.error(errMsg);
					}
					int max = dataCheck.max();
					if(max > 0 && Integer.valueOf(value + "") > max){
						String errMsg = "字段[" + field.getName() + "],值[" + value + "],超过最大值,最大[" + dataCheck.max() + "]";
						buff.append(errMsg).append("|");
						log.error(errMsg);
					}
				}else {
					boolean required = dataCheck.required();
					if(required) {
						//非空
						String errMsg = "字段[" + field.getName() + "]非空,请检查";
						buff.append(errMsg).append("|");
						log.error(errMsg);
					}
				}
			}
		}
		return buff.toString();
	}
}
