/**
 * File Name: ThreadCenter.java
 * Date: 2020-04-20 09:04:54
 */
package com.tzrcb.dispatch.core.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Description: 线程中心
 * @author shenzulun
 * @date 2020-04-20
 * @version 1.0
 */
public class ThreadCenter {
	
	private static ExecutorService executorService;
	
	private ThreadCenter() {}
	
	private ThreadCenter(int size) {
		executorService = Executors.newFixedThreadPool(size);
	}
	
	public static ThreadCenter getInstance() {
		return ThreadCenterInstance.threadCenter;
	}
	
	private static class ThreadCenterInstance{
		private static ThreadCenter threadCenter = new ThreadCenter(4);
	}
	
	/**
	 * 提交任务, 同步返回执行结果
	 * @param task
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public <T> T submitTaskSync(Callable<T> task) throws InterruptedException, ExecutionException {
		Future<T> future = executorService.submit(task);
		return future.get();
	}
	
	/**
	 * 提交任务, 无返回结果
	 * @param task
	 */
	public void submitTask(Runnable task) {
		executorService.execute(task);
	}
	
}
