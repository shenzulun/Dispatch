/**
 * File Name: APIController.java
 * Date: 2020-04-16 19:41:40
 */
package com.tzrcb.dispatch.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.tzrcb.dispatch.core.ExtraJsonRender;
import com.tzrcb.dispatch.dto.MessageDTO;
import com.tzrcb.dispatch.util.CommonUtils;

/**
 * Description: API控制层
 * @author shenzulun
 * @date 2020-04-16
 * @version 1.0
 */
public class APIController extends Controller{
	
	/**
	 * 发起请求
	 */
	public void request() {
		String sign = getHeader("sign");
		String jsonStr = HttpKit.readData(getRequest());
		ExtraJsonRender render = CommonUtils.dispatch(new MessageDTO(jsonStr, sign));
		renderJson(render);
	}

}
