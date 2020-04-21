/**
 * File Name: SmsFactory.java
 * Date: 2020-04-20 16:59:48
 */
package com.tzrcb.dispatch.core.server.sms;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.esms.MessageData;
import com.esms.PostMsg;
import com.esms.common.entity.Account;
import com.esms.common.entity.GsmsResponse;
import com.esms.common.entity.MTPack;
import com.esms.common.entity.MTPack.MsgType;
import com.esms.common.entity.MTPack.SendType;
import com.jfinal.aop.Aop;
import com.tzrcb.dispatch.config.CodeConfig;
import com.tzrcb.dispatch.dto.MessageDTO;
import com.tzrcb.dispatch.service.SmsRecordService;
import me.belucky.easytool.jfinal.PropUtils;
import me.belucky.easytool.util.StringUtils;

/**
 * Description: 短信工厂
 * @author shenzulun
 * @date 2020-04-20
 * @version 1.0
 */
public class SmsFactory {
	protected static Logger log = LoggerFactory.getLogger(SmsFactory.class);
	private static SmsRecordService smsRecordService = Aop.get(SmsRecordService.class);
	private static SmsConfig smsConfig = null;
	private static PostMsg pm = null;
	
	static {
		smsConfig = PropUtils.getConfig("sms", SmsConfig.class);
		pm = new PostMsg(smsConfig.getRemoteHost(), smsConfig.getRemotePort());// 发送器 建议单例配置
	}
	
	
	public static void main(String[] args) throws Exception {
		SmsFactory.doSendSms("13586026616", 
				"<cmppTemplate><template>f094f0c0f5894183be1a4e20032abadf</template><cust_name>张三丰测试</cust_name></cmppTemplate>"); // 短信下行
	}
	
	/**
	 * 发送短信
	 * @param phone  支持多个号码逗号分隔
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	public static MessageDTO sendSms(String phone, String content, String serno) throws Exception {
		MessageDTO messageDTO = new MessageDTO();
		if(StringUtils.isBlank(phone) || StringUtils.isBlank(content)) {
			messageDTO.setCode(CodeConfig.ERR_1001);
			messageDTO.setMessage("校验失败,短信号码或者内容不能为空");
			return messageDTO;
		}
		String[] phones = phone.split(",");
		if(phones.length == 1) {
			//单笔
			GsmsResponse response = doSendSms(phone, content);
			int code = response.getResult();
			if(code == 0) {
				//发送成功
				messageDTO.setCode(CodeConfig.SUCCESS);
			}else {
				messageDTO.setCode(response.getResult() + "");
			}
			messageDTO.setMessage(response.getMessage());
			smsRecordService.save(phone, content, serno, messageDTO.getCode(), messageDTO.getMessage(), response.getAttributes());
		}else {
			boolean isTotalSuccess = true;
			StringBuilder buff = new StringBuilder();
			for(String p : phones) {
				GsmsResponse response = doSendSms(phone, content);
				int code = response.getResult();
				if(code != 0) {
					//发送失败
					isTotalSuccess = false;
				}
				buff.append(p).append(":").append(response.getMessage()).append("|");
				smsRecordService.save(p, content, serno, response.getResult() + "", response.getMessage(), response.getAttributes());
			}
			if(isTotalSuccess) {
				messageDTO.setCode(CodeConfig.SUCCESS);
				messageDTO.setMessage("发送成功");
			}else {
				messageDTO.setCode(CodeConfig.ERR_9999);
				messageDTO.setMessage(buff.toString());
			}
		}
		return messageDTO;
	}
	
	/**
	 * 单笔发送短信
	 * @param phone
	 * @param content
	 * @throws Exception
	 */
	private static GsmsResponse doSendSms(String phone, String content) throws Exception {
		MTPack pack = buildDefaultSMSPack();
		pack.addMsg(MessageData.getInstance(phone, content));

		Account ac = new Account(smsConfig.getUserName(), smsConfig.getPassword());
		GsmsResponse resp = pm.post(ac, pack);
		log.info(resp.toString());
		return resp;
	}
	
	/**
	 * 创建默认下行批次 消息类型：短信； 发送方式；群发； 业务类型：0； 是否去重：否
	 */
	private static MTPack buildDefaultSMSPack() {
		return buildMtPack("sms-" + System.currentTimeMillis(), MsgType.SMS, SendType.MASS, 1, false);
	}
	
	/**
	 * @param batchName
	 *            批次名称
	 * @param msgType
	 *            消息类型
	 * @param sendType
	 *            发送方式
	 * @param biztype
	 *            业务类型根据自己的业务类型编号配置
	 * @param distinctFlag
	 *            是否去重
	 */
	private static MTPack buildMtPack(String batchName, MsgType msgType, SendType sendType, int biztype,
			boolean distinctFlag) {
		MTPack pack = new MTPack();
		pack.setBatchID(UUID.randomUUID());
		pack.setBatchName(batchName);
		pack.setMsgType(msgType);
		pack.setBizType(biztype);
		pack.setDistinctFlag(distinctFlag);
		pack.setSendType(sendType);
		List<MessageData> msgs = new ArrayList<MessageData>();
		pack.setMsgs(msgs);
		return pack;
	}
	
}
