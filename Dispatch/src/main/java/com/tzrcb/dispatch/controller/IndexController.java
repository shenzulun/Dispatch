/**
 * File Name: IndexController.java
 * Date: 2020-04-16 19:11:09
 */
package com.tzrcb.dispatch.controller;

import com.jfinal.core.Controller;

/**
 * Description: 首页控制器
 * @author shenzulun
 * @date 2020-04-16
 * @version 1.0
 */
public class IndexController extends Controller{
	
	public void hello() {
		renderText("hello world");
	}

}
