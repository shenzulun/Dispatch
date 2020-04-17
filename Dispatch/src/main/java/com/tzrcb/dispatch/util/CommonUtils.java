/**
 * File Name: CommonUtils.java
 * Date: 2020-04-16 19:23:53
 */
package com.tzrcb.dispatch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tzrcb.dispatch.core.ExtraJsonRender;
import com.tzrcb.dispatch.core.route.RouteFactory;
import com.tzrcb.dispatch.dto.MessageDTO;
import com.tzrcb.dispatch.model.CommonResponseModel;
import me.belucky.easytool.task.TaskInitCenter;
import me.belucky.easytool.util.StringUtils;

/**
 * Description: 通用工具类
 * @author shenzulun
 * @date 2020-04-16
 * @version 1.0
 */
public class CommonUtils extends me.belucky.easytool.util.CommonUtils{
	protected static Logger log = LoggerFactory.getLogger(CommonUtils.class);
	private static volatile boolean taskInitFlag = false;         //任务初始化标志
	
	/**
	 * 程序初始化任务
	 */
	public static void init() {
		try {
			if(!taskInitFlag){
				taskInitFlag = true;
				TaskInitCenter.go();
				log.info("系统初始化成功...");
			}
		} catch (Exception e) {
			log.error("系统初始化启动失败...",e);
		}
	}
	
	/**
	 * 
	 * @param code
	 * @param message
	 * @return
	 */
	public static ExtraJsonRender buildReturnJsonRender(String code, String message) {
		CommonResponseModel resp = new CommonResponseModel();
		resp.setCode(code);
		resp.setMessage(message);
		return new ExtraJsonRender(resp);
	}
	
	/**
	 * 请求分发
	 * @param jsonStr
	 * @param sign
	 * @param transNo
	 * @return
	 */
	public static ExtraJsonRender dispatch(MessageDTO messageDTO) {
		log.info("收到请求...");
		log.info("报文内容:{}", messageDTO.getJsonStr());
		log.info("签名:{}", messageDTO.getSign());
		ExtraJsonRender render = null;
		//由于验签需要密钥,因此要先路由到对应请求后,取出报文中的source字段
		String jsonStr = messageDTO.getJsonStr();
		if(StringUtils.isBlank(jsonStr)) {
			render = buildReturnJsonRender("1001","报文内容不能为空");
			return render;
		}
		CommonResponseModel resp = RouteFactory.route(messageDTO);
		render = new ExtraJsonRender(resp);
		return render;
	}
	
	/**
	 * 验签
	 * @param messageDTO
	 */
	public static boolean decode(MessageDTO messageDTO) {
		return true;
	}
	
}
