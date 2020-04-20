/**
 * File Name: DispatchRecordUtil.java
 * Date: 2020-04-20 08:43:19
 */
package com.tzrcb.dispatch.util.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jfinal.aop.Aop;
import com.tzrcb.dispatch.core.concurrent.ThreadCenter;
import com.tzrcb.dispatch.model.CommonRequestModel;
import com.tzrcb.dispatch.model.CommonResponseModel;
import com.tzrcb.dispatch.model.table.dispatch.DispatchRecord;
import com.tzrcb.dispatch.service.DispatchRecordService;

/**
 * Description: 服务分发记录表的工具类
 * @author shenzulun
 * @date 2020-04-20
 * @version 1.0
 */
public class DispatchRecordUtil {
	
	protected static Logger log = LoggerFactory.getLogger(DispatchRecordUtil.class);

	private static DispatchRecordService DispatchRecordService = Aop.get(DispatchRecordService.class);

	/**
	 * 保存分发记录
	 * @param request
	 * @param response
	 */
	public static void save(CommonRequestModel<?> request, CommonResponseModel response) {
		if(request == null || response == null) {
			return;
		}
		DispatchRecord record = new DispatchRecord();
		String transNo = request.getTransNo();
		record.set("trans_no", transNo);
		record.set("serno", request.getSerno());
		record.set("source", request.getSource());
		record.set("target", request.getTarget());
		record.set("request_msg", request.getTransMsg());
		record.set("code", response.getCode());
		record.set("message", response.getMessage());
		record.set("response_msg", response.getTransMsg());
		
		DispatchRecordService.save(record);
		log.info("流水[{}]保存成功...", request.getSerno());
	}
	
	/**
	 * 异步执行保存
	 * @param request
	 * @param response
	 */
	public static void saveAsyn(CommonRequestModel<?> request, CommonResponseModel response) {
		ThreadCenter.getInstance().submitTask(new Thread("DispatchRecord_SAVE") {
			public void run() {
				save(request, response);
			}
		});
	}
}
