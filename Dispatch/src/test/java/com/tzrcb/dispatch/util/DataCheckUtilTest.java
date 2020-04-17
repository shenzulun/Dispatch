/**
 * File Name: DataCheckUtilTest.java
 * Date: 2020-04-17 16:16:19
 */
package com.tzrcb.dispatch.util;

import org.junit.Test;

import com.tzrcb.dispatch.model.CommonRequestModel;
import com.tzrcb.dispatch.model.api._10010101._10010101RequestBody;
import com.tzrcb.dispatch.util.check.DataCheckUtil;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-04-17
 * @version 1.0
 */
public class DataCheckUtilTest {

	@Test
	public void test1() {
		CommonRequestModel<_10010101RequestBody> requestModel = new CommonRequestModel<_10010101RequestBody>();
		requestModel.setData(new _10010101RequestBody("13586026617","hello world"));
//		requestModel.setTransNo("10010101");
		requestModel.setSource("1002");
		requestModel.setTarget("1001");
		requestModel.setTransTime("20200416163501001");
		
		DataCheckUtil.checkData(requestModel);
	}
}
