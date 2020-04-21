/**
 * File Name: _10010103_handler.java
 * Date: 2020-04-21 10:51:44
 */
package com.tzrcb.dispatch.core.route.handle;

import java.util.Map;
import com.tzrcb.dispatch.config.CodeConfig;
import com.tzrcb.dispatch.core.route.AbstractRouter;
import com.tzrcb.dispatch.core.server.sms.SmsFactory;
import com.tzrcb.dispatch.dto.MessageDTO;
import com.tzrcb.dispatch.model.CommonRequestModel;
import com.tzrcb.dispatch.model.CommonResponseModel;
import com.tzrcb.dispatch.model.api._10010103._10010103RequestBody;
import com.tzrcb.dispatch.util.JsonUtils;
import com.tzrcb.dispatch.util.check.DataCheckUtil;

import me.belucky.easytool.util.CollectionUtils;
import me.belucky.easytool.util.ParserUtils;
import me.belucky.easytool.util.StringUtils;

/**
 * Description: 运维提醒-短信发送接口类
 * @author shenzulun
 * @date 2020-04-21
 * @version 1.0
 */
public class _10010103_handler extends AbstractRouter{

	public CommonResponseModel execute(MessageDTO messageDTO) {
		CommonResponseModel resp = new CommonResponseModel();
		String jsonStr = messageDTO.getJsonStr();
		CommonRequestModel<_10010103RequestBody> req = JsonUtils.toObject(jsonStr, CommonRequestModel.class, _10010103RequestBody.class);
		
		String checkMsg = DataCheckUtil.checkData(req.getData());
		//1. 检查必输字段
		if(StringUtils.isNotNull(checkMsg)) {
			resp.setCode(CodeConfig.ERR_1001);
			resp.setMessage(checkMsg);
			return resp;
		}
		
		resp.setSource(req.getSource());
		resp.setTarget(req.getTarget());
		resp.setTransNo(req.getTransNo());
		resp.setTransTime(req.getTransTime());
		resp.setVersion(req.getVersion());
		resp.setSerno(req.getSerno());

		//短信内容需要组装成模板形式
		String template = "<cmppTemplate><template>${templateId}</template><sys_name>${sysName}</sys_name><code_name>${codeName}</code_name></cmppTemplate>";
		Map<String, String> map = CollectionUtils.buildMap("templateId", "a23ed24b0f1f493fa95f5d9ace7aedf2");
		map.put("sysName", req.getData().getSysName());
		map.put("codeName", req.getData().getCodeName());
		String content = ParserUtils.replaceString(template, map);
		try {
			MessageDTO mess = SmsFactory.sendSms(req.getData().getPhone(), content, req.getSerno());
			resp.setCode(mess.getCode());
			resp.setMessage(mess.getMessage());
		} catch (Exception e) {
			log.error("短信发送失败", e);
			resp.setCode(CodeConfig.ERR_9999);
			resp.setMessage(e.getMessage());
		}
		return resp;
	}

}
