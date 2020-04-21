/**
 * File Name: ConstConfigTask.java
 * Date: 2020-04-20 15:56:06
 */
package com.tzrcb.dispatch.core.task;

import com.tzrcb.dispatch.config.ConstConfig;

import me.belucky.easytool.task.AbstractTask;

/**
 * Description: 缓存初始化任务
 * @author shenzulun
 * @date 2020-04-20
 * @version 1.0
 */
public class ConstConfigTask extends AbstractTask{

	public ConstConfigTask(String taskName) {
		super(taskName);
	}

	public void execute() {
		ConstConfig.init();
	}

}
