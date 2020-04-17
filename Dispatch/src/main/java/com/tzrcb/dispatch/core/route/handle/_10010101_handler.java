/**
 * File Name: _10010101_handler.java
 * Date: 2020-04-17 09:04:47
 */
package com.tzrcb.dispatch.core.route.handle;

import com.tzrcb.dispatch.core.route.AbstractRouter;
import com.tzrcb.dispatch.dto.MessageDTO;
import com.tzrcb.dispatch.model.CommonRequestModel;
import com.tzrcb.dispatch.model.CommonResponseModel;
import com.tzrcb.dispatch.model.api._10010101._10010101RequestBody;
import com.tzrcb.dispatch.util.JsonUtils;

/**
 * Description: 单笔短信发送
 * @author shenzulun
 * @date 2020-04-17
 * @version 1.0
 */
public class _10010101_handler extends AbstractRouter{

	public CommonResponseModel execute(MessageDTO messageDTO) {
		CommonResponseModel resp = new CommonResponseModel();
		String jsonStr = messageDTO.getJsonStr();
		CommonRequestModel<_10010101RequestBody> req = JsonUtils.toObject(jsonStr, CommonRequestModel.class, _10010101RequestBody.class);
		
		resp.setSource(req.getSource());
		resp.setTarget(req.getTarget());
		resp.setTransNo(req.getTransNo());
		resp.setTransTime(req.getTransTime());
		resp.setVersion(req.getVersion());
		resp.setCode("0000");
		resp.setMessage("交易成功");
		return resp;
	}

}
