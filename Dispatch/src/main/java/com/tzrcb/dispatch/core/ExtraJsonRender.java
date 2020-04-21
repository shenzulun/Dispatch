/**
 * File Name: ExtraJsonRender.java
 * Date: 2020-04-16 20:01:58
 */
package com.tzrcb.dispatch.core;

import com.jfinal.render.JsonRender;
import com.tzrcb.dispatch.util.JsonUtils;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-04-16
 * @version 1.0
 */
public class ExtraJsonRender extends JsonRender{
	
	public ExtraJsonRender(Object object) {
		super.jsonText = JsonUtils.toJson(object);
		
		//加签
	}
	
	public ExtraJsonRender() {
		
	}

}
