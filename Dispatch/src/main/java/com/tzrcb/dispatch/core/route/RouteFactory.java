/**
 * File Name: RouteFactory.java
 * Date: 2020-04-17 08:41:46
 */
package com.tzrcb.dispatch.core.route;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tzrcb.dispatch.config.CodeConfig;
import com.tzrcb.dispatch.dto.MessageDTO;
import com.tzrcb.dispatch.model.CommonRequestModel;
import com.tzrcb.dispatch.model.CommonResponseModel;
import com.tzrcb.dispatch.util.JsonUtils;
import com.tzrcb.dispatch.util.check.DataCheckUtil;
import me.belucky.easytool.util.StringUtils;

/**
 * Description: 路由工厂
 * @author shenzulun
 * @date 2020-04-17
 * @version 1.0
 */
public class RouteFactory {
	protected static Logger log = LoggerFactory.getLogger(RouteFactory.class);
	private static ConcurrentMap<String, Boolean> routeMap = new ConcurrentHashMap<String, Boolean>();
	
	public static CommonResponseModel route(MessageDTO messageDTO) {
		CommonResponseModel resp = new CommonResponseModel();
		CommonRequestModel<?> req = JsonUtils.toObject(messageDTO.getJsonStr(), CommonRequestModel.class);
		
		resp = check(messageDTO, req);
		if(StringUtils.isNotNull(resp.getCode())) {
			return resp;
		}
		String transNo = req.getTransNo();
		//分发
		String className = "com.tzrcb.dispatch.core.route.handle._" + transNo + "_handler";
		boolean isRouteExist = false;
		try {
			Boolean v = routeMap.get(className);
			if(v != null && !v.booleanValue()) {
				//不存在该路由
				resp.setCode("1001");
				resp.setMessage("该交易码[" + transNo + "]未实现...");
			}else {
				IHander hanlder = (IHander) Class.forName(className).newInstance();
				resp = hanlder.handle(messageDTO);
				isRouteExist = true;
			}
		} catch (InstantiationException e) {
			log.error("", e);
			resp.setCode("1001");
			resp.setMessage("该交易码[" + transNo + "]未实现...");
		} catch (IllegalAccessException e) {
			log.error("", e);
			resp.setCode("1001");
			resp.setMessage("该交易码[" + transNo + "]未实现...");
		} catch (ClassNotFoundException e) {
			log.error("", e);
			resp.setCode("1001");
			resp.setMessage("该交易码[" + transNo + "]未实现...");
		} finally {
			routeMap.put(className, isRouteExist);
		}
		return resp;
	}
	
	/**
	 * 校验
	 * @param messageDTO
	 * @param req
	 * @return
	 */
	private static CommonResponseModel check(MessageDTO messageDTO, CommonRequestModel<?> req) {
		CommonResponseModel resp = new CommonResponseModel();
		String checkMsg = DataCheckUtil.checkData(req);
		if(StringUtils.isNotNull(checkMsg)) {
			resp.setCode(CodeConfig.ERR_1001);
			resp.setMessage(checkMsg);
			return resp;
		}
		//验签
		return resp;
	}
}
