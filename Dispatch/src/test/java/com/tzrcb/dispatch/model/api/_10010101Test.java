/**
 * File Name: _10010101Test.java
 * Date: 2020-04-16 16:30:54
 */
package com.tzrcb.dispatch.model.api;

import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jfinal.json.JacksonFactory;
import com.jfinal.json.JsonManager;
import com.jfinal.kit.JsonKit;
import com.tzrcb.dispatch.model.CommonRequestModel;
import com.tzrcb.dispatch.model.api._10010101._10010101RequestBody;
import com.tzrcb.dispatch.util.JsonUtils;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-04-16
 * @version 1.0
 */
public class _10010101Test {
	
	@Before
	public void init() {
		JsonManager.me().setDefaultJsonFactory(new JacksonFactory());
	}
	
	
	@Test
	public void test1() throws JsonMappingException, JsonProcessingException {
		CommonRequestModel<_10010101RequestBody> requestModel = new CommonRequestModel<_10010101RequestBody>();
		requestModel.setData(new _10010101RequestBody("13586026617","hello world"));
		requestModel.setTransNo("10010101");
		requestModel.setSource("1002");
		requestModel.setTarget("1001");
		requestModel.setTransTime("20200416163501001");
		
		String reqJson = JsonKit.toJson(requestModel);
		System.out.println(reqJson);
		CommonRequestModel resp = JsonUtils.toObject(reqJson, CommonRequestModel.class);
		System.out.println(resp.getTransNo());
	}


}
