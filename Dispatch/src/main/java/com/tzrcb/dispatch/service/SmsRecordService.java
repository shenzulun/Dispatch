/**
 * File Name: SmsRecordService.java
 * Date: 2020-04-21 15:56:14
 */
package com.tzrcb.dispatch.service;

import com.tzrcb.dispatch.model.table.dispatch.SmsRecord;

/**
 * Description: 短信服务类
 * @author shenzulun
 * @date 2020-04-21
 * @version 1.0
 */
public class SmsRecordService {

	private SmsRecord dao = new SmsRecord().dao();
	
	/**
	 * 保存短信记录
	 * @param phone
	 * @param content
	 * @param serno
	 * @param code
	 * @param message
	 */
	public void save(String phone, String content, String serno, String code, String message, String remark) {
		SmsRecord sms = new SmsRecord();
		sms.setPhone(phone);
		sms.setContent(content);
		sms.setSerno(serno);
		sms.setCode(code);
		sms.setMessage(message);
		sms.setRemark(remark);
		sms.save();
	}
	
}
