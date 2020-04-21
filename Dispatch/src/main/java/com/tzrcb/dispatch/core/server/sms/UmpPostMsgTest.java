package com.tzrcb.dispatch.core.server.sms;

import java.net.ConnectException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.esms.MOMsg;
import com.esms.MessageData;
import com.esms.PostMsg;
import com.esms.WechatMOMsg;
import com.esms.common.entity.Account;
import com.esms.common.entity.BusinessType;
import com.esms.common.entity.EmailContent;
import com.esms.common.entity.GsmsResponse;
import com.esms.common.entity.MTPack;
import com.esms.common.entity.MTPack.MsgType;
import com.esms.common.entity.MTPack.SendType;
import com.esms.common.entity.MTReport;
import com.esms.common.entity.MTResponse;
import com.esms.common.entity.TemplateData;
import com.esms.common.util.JsonUtil;
import com.esms.common.util.MediaUtil;

public class UmpPostMsgTest {

	private static PostMsg pm = new PostMsg("127.0.0.1", 8090);// 发送器 建议单例配置

	
	public void test() {
		try {
			extend(); // 扩展接口范例
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 扩展接口范例
	public static void extend() throws Exception {
		
		//设置网关上行数据获取模块的 IP和port，用于获取账号信息、上行、状态报告等. 如果需要通过sdk获取状态报告 上行等数据  则需要设置该地址
		//pm.getWsHost().setHost("127.0.0.1", 8088);
		
		// pm.setReSendTimes(3);// 设置mt下行重发次数
		// pm.setReMoQueryTimes(3);// 设置mo重新查询次数

		// /**代理上网设置,如果需要*/
		// HostInfo proxyHost = new HostInfo("192.168.0.47", 1080);
		// proxyHost.setType(HostInfo.ConnectionType.SOCKET4); //设置连接类型
		// proxyHost.setUsername("004"); //设置用户名
		// proxyHost.setPassword("123"); //设置密码
		// pm.getProxy().setProxy(proxyHost); //设置代理

		//singlePackMsgId("admin", "123456", "13333333333", "content", "ABCD123");// 一个批次，一个扩展码
		//singleTicketMsgId("admin", "123456");// 每条短信，一个扩展码
		//template("admin", "123456", "13333333333");// 动态模板
		doSendSms("admin", "123456", "18620004383", "lll"); // 短信下行
//		doSendCompositeMsg("admin", "123456"); // 组合消息下行
		//doSendVoiceVarTemplate("admin", "123456");
		//doSendMms("admin", "123456"); // 彩信下行
		//doSendWechatTemplate("admin", "123456", "openID", "www.baidu.com");
		//doSendVoiceCode("admin", "123456", "13333333333", "123456");
		//doSendEmail("admin", "123456", "123@163.com", "邮件主题", "邮件正文");
		//doSendEmailStaticTemplate("admin", "123456", "templateNo", "123@163.com");
		//doSendEmailVarTemplate("admin", "123456");
		//doGetAccountInfo("admin", "123456"); // 获取账号信息
		//doModifyPwd("admin", "123456", "a123456"); //修改密码

		//doFindResps("admin", "123456"); //查询提交报告
		//doFindReports("admin", "123456"); //查询状态报告

		//doGetMos("admin", "123456"); //获取上行信息
		//doGetResps("admin", "123456"); //获取提交报告
		//doGetReports("admin", "123456"); //获取状态报告

		//doGetWechatMos("admin", "123456"); //获取微信上行消息
	}

	/**
	 * 短信下发范例 一个批次，一个扩展码
	 * 
	 * @param account
	 *            发送账号
	 * @param password
	 *            发送密码
	 * @param phone
	 *            接收方手机号
	 * @param content
	 *            短信内容
	 * @param cusnomNum
	 *            扩展码
	 */
	public static void singlePackMsgId(String account, String password, String phone, String content, String cusnomNum)
			throws Exception {

		MTPack pack = buildDefaultSMSPack();
		pack.setCustomNum(cusnomNum);

		MessageData messageData1 = MessageData.getInstance(phone, content);
		MessageData messageData2 = MessageData.getInstance(phone, content);
		pack.addMsg(messageData1);
		pack.addMsg(messageData2);

		Account ac = new Account(account, password);
		GsmsResponse resp = pm.post(ac, pack);
		System.out.println(resp);
	}

	/**
	 * 短信下发范例 每个话单，一个扩展码
	 * 
	 * @param account
	 *            发送账号
	 * @param password
	 *            发送密码
	 */
	public static void singleTicketMsgId(String account, String password) throws Exception {
		MTPack pack = buildDefaultSMSPack();

		String content = "短信群发送测试群发测试群发群发";
		String phone = "13430258222";
		MessageData messageData1 = MessageData.getInstance(phone, content);
		messageData1.setCustomMsgID("004");
		MessageData messageData2 = MessageData.getInstance(phone, content);
		messageData2.setCustomMsgID("005");

		pack.addMsg(messageData1);
		pack.addMsg(messageData2);

		Account ac = new Account(account, password);
		GsmsResponse resp = pm.post(ac, pack);
		System.out.println(resp);
	}

	/**
	 * 短信下发范例
	 * 
	 * @param account
	 *            发送账号
	 * @param password
	 *            发送密码
	 * @param phone
	 *            接收方手机号
	 * @param content
	 *            短信内容
	 */
	public static void doSendSms(String account, String password, String phone, String content) throws Exception {
		MTPack pack = buildDefaultSMSPack();
		pack.addMsg(MessageData.getInstance(phone, content));

		/**
		 * 设置模板编号(静态模板将以模板内容发送;
		 * 动态模板需要发送变量值，JSON格式：[{"key":"变量名1","value":"变量值1"},{"key":"变量名2","
		 * value":"变量值2"}])
		 */
		Account ac = new Account(account, password);
		GsmsResponse resp = pm.post(ac, pack);
		System.out.println(resp);
	}
	
	/**
	 * 发送组合消息范例
	 * @param account
	 * @param password
	 * @param phone
	 * @param content
	 * @throws Exception
	 */
	public static void doSendCompositeMsg(String account, String password) throws Exception {
		
		List<MsgType> msgTypes = new ArrayList<MsgType>();
		msgTypes.add(MsgType.SMS);
		msgTypes.add(MsgType.WECHAT);
		MTPack pack = buildCompositeMsgMtPack("短信组合消息测试批次", "zh001", 15, msgTypes);
		
		for(int i = 0; i < 10; i++){
			Map<String, String> variableMap = new HashMap<String, String>();
			variableMap.put("name", "name"+i);
			variableMap.put("remark", "remark"+i);
			String content = JsonUtil.serialize(variableMap);
			pack.addMsg(MessageData.getInstance("18620004386", content));
		}
	
		Account ac = new Account(account, password);
		GsmsResponse resp = pm.post(ac, pack);
		System.out.println(resp);
	}
	
	/**
	 * 邮件下发范例
	 * 
	 * @param account
	 *            发送账号
	 * @param password
	 *            发送密码
	 * @param email
	 *            接收方邮件地址
	 * @param content
	 *            邮件内容
	 * @param subject
	 *            邮件主题
	 */
	public static void doSendEmail(String account, String password, String email, String content, String subject)
			throws Exception {

		MTPack pack = buildMtPack("邮件测试范例", MsgType.EMAIL, SendType.MASS, 4, false);

		// 如果有图片附件
		String path = UmpPostMsgTest.class.getClassLoader().getResource("email_test").getPath();
		path = URLDecoder.decode(path, "utf-8");
		pack.setMedias(MediaUtil.getMediasFromFolder(path));

		EmailContent emailContent = new EmailContent();
		emailContent.setContent(content);
		emailContent.setSubject(subject);

		MessageData msg = MessageData.getEmailMsgInstance(email, "");
		msg.setEmailContent(emailContent);
		pack.addMsg(msg);

		Account ac = new Account(account, password);
		GsmsResponse resp = pm.post(ac, pack);
		System.out.println(resp);
	}

	/**
	 * 语音验证码下发范例
	 * 
	 * @param account
	 *            发送账号
	 * @param password
	 *            发送密码
	 * @param phone
	 *            接收方手机号
	 * @param code
	 *            验证码
	 */
	private static void doSendVoiceCode(String account, String password, String phone, String code) throws Exception {
		MTPack pack = buildMtPack("语音验证码测试范例", MsgType.VOICE_CODE, SendType.MASS, 4, false);
		MessageData msg = MessageData.getInstance(phone, code);
		pack.addMsg(msg);

		Account ac = new Account(account, password);
		GsmsResponse resp = pm.post(ac, pack);
		System.out.println(resp);
	}

	/**
	 * 模板下发范例 
	 * 假设模板由多个固定变量，这里以2个为例
	 * 设置模板编号(静态模板将以模板内容发送;动态模板需要发送变量值，JSON格式：[{"key":"变量名1","value":"变量值1"},{"
	 * key":"变量名2","value":"变量值2"}]) 模板目前除微信,语音外支持三种格式 第一种兼容旧的sdk格式 为数组类型json
	 * [{\ "key\":\"key1\",\"value\":\"value1\"},{\"key\":\"key2\",\"value\":\"
	 * value2\" }] 第二种为新的可扩展json对象格式
	 * {\"key1\":{\"value\":\"value1\"},\"key2\":{\"value\":\"value2\"}} 第三种同第二种
	 * 为map形式组装 map的键为变量名称，值为TemplateData对象 map.put("key1", new
	 * TemplateData("value1")); map.put("key2", new TemplateData("value2"));
	 * 语音微信不支持老版sdk类型
	 * 
	 * 
	 * @param account
	 *            发送账号
	 * @param password
	 *            发送密码
	 * @param phone
	 *            接收方手机号
	 * @param code
	 *            验证码
	 */
	private static void template(String account, String password, String phone) throws Exception {
		MTPack pack = buildDefaultSMSPack();
		pack.setSendType(SendType.GROUP);// 如果是模板发送只能用组发方式发送

		/**
		 * 假设模板由多个固定变量，这里以2个为例 设置模板编号(静态模板将以模板内容发送;
		 * 动态模板需要发送变量值，JSON格式：[{"key":"变量名1","value":"变量值1"}
		 * ,{"key":"变量名2","value":"变量值2"}])
		 */

		Map<String, TemplateData> templateValue = new HashMap<String, TemplateData>();
		templateValue.put("name", new TemplateData("aaa"));
		templateValue.put("remark", new TemplateData("bbb"));

		pack.addMsg(MessageData.getTemplateInstance("18620020561", templateValue));// map形式
		pack.addMsg(MessageData.getInstance("18620020562",
				"{\"name\":{\"value\":\"aaa\"},\"remark\":{\"value\":\"bbb\"}}"));// json对象形式
		pack.addMsg(MessageData.getInstance("18620020563",
				"[{\"key\":\"name\",\"value\":\"aaa\"},{\"key\":\"remark\",\"value\":\"bbb\"}]"));// json数组形式

		pack.setTemplateNo("test2");// "test2"模板编号
		Account ac = new Account(account, password);
		GsmsResponse resp = pm.post(ac, pack);
		System.out.println(resp);
	}

	/**
	 * 微信下发范例
	 * 
	 * @param account
	 *            发送账号
	 * @param password
	 *            发送密码
	 * @param openID
	 *            接收方openid
	 * @param url
	 *            跳转地址
	 */
	public static void doSendWechatTemplate(String account, String password, String openID,String url) throws Exception {
		MTPack pack = buildMtPack("微信测试范例", MsgType.WECHAT, SendType.MASS, 4, false);

		Map<String, TemplateData> templateValue = new HashMap<String, TemplateData>();
		templateValue.put("name", new TemplateData("您好，xxx", "#153177"));
		templateValue.put("remark", new TemplateData("订单号：15456456454645bbb我们将在24小时内为您退款。退款事宜请联系客服", "#153177"));
		pack.setTemplateNo("-42TyELcAiJml_B2vGx63IReU3F9yicZ7E5DxZVzgCA");

		pack.addMsg(MessageData.getWechatInstance(openID, templateValue, url));
		Account ac = new Account(account, password);
		GsmsResponse resp = pm.post(ac, pack);
		System.out.println(resp);
	}

	/**
	 * 邮件静态模板下发范例
	 * 
	 * @param account
	 *            发送账号
	 * @param password
	 *            发送密码
	 * @param templateNO
	 *            模板编号
	 * @param email
	 *            接收方邮件地址
	 */
	private static void doSendEmailStaticTemplate(String account, String password,String templateNO,String email) throws Exception {

		// 静态模板发送
		MTPack pack = buildMtPack("邮件测试范例", MsgType.EMAIL, SendType.MASS, 4, false);

		MessageData msg = MessageData.getEmailMsgInstance(email, "");
		pack.addMsg(msg);
		pack.setTemplateNo(templateNO);// 模板编码

		Account ac = new Account(account, password);
		GsmsResponse resp = pm.post(ac, pack);
		System.out.println(resp);
	}

	/**
	 * 邮件动态变量模板
	 * 
	 * @param account
	 *            发送账号
	 * @param password
	 *            发送密码
	 */
	private static void doSendEmailVarTemplate(String account, String password) throws Exception {

		MTPack pack = buildMtPack("邮件测试范例", MsgType.EMAIL, SendType.GROUP, 4, false);
		pack.setTemplateNo("emailNo1");

		Map<String, TemplateData> templateValue = new HashMap<String, TemplateData>();
		templateValue.put("name", new TemplateData("aaa"));
		templateValue.put("remark", new TemplateData("bbb"));

		pack.addMsg(MessageData.getEmailMsgInstance("541014729@qq.com",
				"{\"name\":{\"value\":\"aaa\"},\"remark\":{\"value\":\"bbb\"}}"));
		pack.addMsg(MessageData.getEmailMsgInstance("541014729@qq.com",
				"[{\"key\":\"name\",\"value\":\"aaa\"},{\"key\":\"remark\",\"value\":\"bbb\"}]"));
		pack.addMsg(MessageData.getEmailTemplateInstance("541014729@qq.com", templateValue));

		Account ac = new Account(account, password);
		GsmsResponse resp = pm.post(ac, pack);
		System.out.println(resp);
	}

	/**
	 * 语音动态变量模板
	 * 
	 * @param account
	 *            发送账号
	 * @param password
	 *            发送密码
	 */
	private static void doSendVoiceVarTemplate(String account, String password) throws Exception {

		MTPack pack = buildMtPack("语音测试范例", MsgType.VOICE_NOTICE, SendType.GROUP, 4, false);

		Map<String, TemplateData> templateValue = new HashMap<String, TemplateData>();
		templateValue.put("text:11", new TemplateData("aaa"));
		templateValue.put("text:10", new TemplateData("bbb"));
		pack.setTemplateNo("voiceNo1");

		MessageData msg = MessageData.getTemplateInstance("18620020569", templateValue);
		pack.addMsg(msg);
		pack.addMsg(MessageData.getInstance("18620020562",
				"{\"text:11\":{\"value\":\"aaa\"},\"text:10\":{\"value\":\"bbb\"}}"));

		Account ac = new Account(account, password);
		GsmsResponse resp = pm.post(ac, pack);
		System.out.println(resp);
	}


	/**
	 * 彩信下发范例
	 * 
	 * @param account
	 *            发送账号
	 * @param password
	 *            发送密码
	 */
	public static void doSendMms(String account, String password) throws Exception {
		MTPack pack = buildMtPack("彩信测试范例", MsgType.MMS, SendType.MASS, 4, false);

		// 设置公共彩信资源
		String path = UmpPostMsgTest.class.getClassLoader().getResource("mms_test").getPath();
		path = URLDecoder.decode(path, "utf-8");
		pack.setMedias(MediaUtil.getMediasFromFolder(path));

		/** 群发，多号码一内容 */
		pack.addMsg(MessageData.getInstance("13430258111", "123"));
		pack.addMsg(MessageData.getInstance("13430258222", "123"));
		pack.addMsg(MessageData.getInstance("13430258333", "123"));

		// /** 组发，多号码多内容 */
		// pack.setSendType(MTPack.SendType.GROUP);
		// //设置私有彩信资源
		// MessageData msg1 = MessageData.getInstance("13430258111", null);
		// msg1.setMedias(MediaUtil.getMediasFromFolder("F:/mms_test/msg1"));
		// pack.addMsg(msg1);
		// MessageData msg2 = MessageData.getInstance("13430258222", null);
		// msg2.setMedias(MediaUtil.getMediasFromFolder("F:/mms_test/msg2"));
		// pack.addMsg(msg2);
		// MessageData msg3 = MessageData.getInstance("13430258333", null);
		// msg3.setMedias(MediaUtil.getMediasFromFolder("F:/mms_test/msg3"));
		// pack.addMsg(msg3);

		Account ac = new Account(account, password);
		GsmsResponse resp = pm.post(ac, pack);
		System.out.println(resp);
	}

	/**
	 * 获取账号信息
	 * 
	 * @param pm
	 * @param account
	 * @throws Exception
	 */
	public static void doGetAccountInfo(String account,String password) throws Exception {
		Account ac = new Account(account, password);
		System.out.println(pm.getAccountInfo(ac)); // 获取账号详细信息
		BusinessType[] bizTypes = pm.getBizTypes(ac); // 获取账号绑定业务类型
		if (bizTypes != null) {
			for (BusinessType bizType : bizTypes) {
				System.out.println(bizType);
			}
		}
	}

	/**
	 * 获取上行信息
	 * 
	 * @param pm
	 * @param account
	 * @throws Exception
	 */
	public static void doGetMos(String account,String password) throws Exception {
		Account ac = new Account(account, password);
		MOMsg[] mos;
		mos = pm.getMOMsgs(ac, 100);
		if (mos != null) {
			for (MOMsg mo : mos) {
				System.out.println(mo);
			}
		}
	}

	/**
	 * 获取微信上行信息
	 * 
	 * @param pm
	 * @param account
	 * @throws Exception
	 */
	public static void doGetWechatMos(String account,String password) throws Exception {
		Account ac = new Account(account, password);
		WechatMOMsg[] mos;
		mos = pm.getWechatMOMsgs(ac, 100);
		if (mos != null) {
			for (WechatMOMsg mo : mos) {
				System.out.println(mo);
			}
		}
	}

	/**
	 * 查询提交报告
	 * 
	 * @param pm
	 * @param ac
	 * @throws Exception
	 */
	public static void doFindResps(String account,String password) throws Exception {
		Account ac = new Account(account, password);
		UUID batchID = UUID.fromString("0e3ed6f4-8b95-4cfc-88de-2616fe2317a1"); // 如果需要按批次ID来查询
		try {
			MTResponse[] foundMtResps = pm.findResps(ac, 1, batchID, null, 0);
			if (foundMtResps != null) {
				for (MTResponse resp : foundMtResps) {
					System.out.println(resp);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 获取提交报告
	 * 
	 * @param pm
	 * @param ac
	 * @throws Exception
	 */
	public static void doGetResps(String account,String password) throws Exception {
		Account ac = new Account(account, password);
		try {
			MTResponse[] mtResps = pm.getResps(ac, 100);
			if (mtResps != null) {
				for (MTResponse resp : mtResps) {
					System.out.println(resp);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 查询状态报告
	 * 
	 * @param pm
	 * @param ac
	 * @throws Exception
	 */
	public static void doFindReports(String account,String password) throws Exception {
		Account ac = new Account(account, password);
		UUID batchID = UUID.fromString("0e3ed6f4-8b95-4cfc-88de-2616fe2317a1"); // 如果需要按批次ID来查询
		try {
			MTReport[] foundMtReports = pm.findReports(ac, 1, batchID, null, 0);
			if (foundMtReports != null) {
				for (MTReport report : foundMtReports) {
					System.out.println(report);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 获取状态报告
	 * 
	 * @param pm
	 * @param ac
	 * @throws Exception
	 */
	public static void doGetReports(String account,String password) throws Exception {
		Account ac = new Account(account, password);
		try {
			MTReport[] mtReports = pm.getReports(ac, 2);
			if (mtReports != null) {
				for (MTReport report : mtReports) {
					System.out.println(report);
				}
			} else {
				System.out.println("reports is null!");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
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

	/**
	 * 创建默认下行批次 消息类型：短信； 发送方式；群发； 业务类型：0； 是否去重：否
	 */
	private static MTPack buildDefaultSMSPack() {
		return buildMtPack("测试批次", MsgType.SMS, SendType.MASS, 0, false);
	}

	/**
	 * 创建组合消息批次包
	 * @param batchName 批次名称
	 * @param templateNo         模板编号   发送的各个消息类型应该有一个该编号的模板 并且属于发送用户所有
	 * @param waitResendMinute   渠道等待重发时间分钟数   即指定多少分钟后如果有未成功的号码进行重发
	 * @param msgTypes   		   消息类型列表
	 * @return
	 */
	private static MTPack buildCompositeMsgMtPack(String batchName, String templateNo, int waitResendMinute, List<MsgType> msgTypes) {
		MTPack pack = new MTPack();
		pack.setBatchID(UUID.randomUUID());
		pack.setBatchName(batchName);
		pack.setDistinctFlag(false);
		pack.setSendType(SendType.GROUP);
		pack.setWaitResendMinute(waitResendMinute);
		pack.setTemplateNo(templateNo);
		List<MessageData> msgs = new ArrayList<MessageData>();
		pack.setMsgs(msgs);
		pack.setMsgTypes(msgTypes);
		
		return pack;
	}

	/**
	 * 修改密码
	 * 
	 * @param pm
	 * @param ac
	 * @throws Exception
	 */
	public static void doModifyPwd(String account, String oldPws, String newPwd) throws Exception {
		Account ac = new Account(account, oldPws);
		System.out.println(pm.modifyPassword(ac, newPwd));
	}

	// 兼容接口范例
	public static void compatibility() throws ConnectException {

		// 设置部分
		PostMsg pm = new PostMsg("admin", "123456"); // 创建实例时，需输入用户名与密码
		pm.getCmHost().setHost("127.0.0.1", 8089); // 设置网关的IP和port
		pm.getWsHost().setHost("127.0.0.1", 8088); // 设置WebService的 IP和port
		// pm.getProxy().setProxy(ProxyServer.PROXY_TYPE_SOCKS4, "192.168.0.47",
		// 1080); //设置代理
		//
		//
		int resp = -1;
		/** 单发，一号码一内容 */
		resp = pm.post("13430258111", "短信单发测试", "");
		//
		// /** 群发，多号码一内容 */
		// resp = pm.post(new String[]{"13430258111", "13430258222",
		// "13430258333"}, "短信群发测试", "");
		//
		// /** 组发，多号码多内容 */
		// MessageData[] msgs = new MessageData[3];
		// msgs[0] = new MessageData("13430258111", "短信组发测试111");
		// msgs[1] = new MessageData("13430258222", "短信组发测试222");
		// msgs[2] = new MessageData("13430258333", "短信组发测试333");
		// resp = pm.post(msgs, "");

		System.out.println("响应：" + resp);

		// 修改密码
		// System.out.println(pm.modifyPassword("123456"));

		// System.out.println(pm.getConfigInfo()); //获取用户详细信息
		// MOMsg[] momsgs = pm.getMOMsg(); //获取上行信息
		// if(momsgs != null){
		// for(MOMsg momsg : momsgs){
		// System.out.println(momsg);
		// }
		// }
	}
}
