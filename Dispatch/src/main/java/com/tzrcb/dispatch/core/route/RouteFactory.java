/**
 * File Name: RouteFactory.java
 * Date: 2020-04-17 08:41:46
 */
package com.tzrcb.dispatch.core.route;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.aop.Aop;
import com.tzrcb.dispatch.config.CodeConfig;
import com.tzrcb.dispatch.config.ConstConfig;
import com.tzrcb.dispatch.dto.MessageDTO;
import com.tzrcb.dispatch.model.CommonRequestModel;
import com.tzrcb.dispatch.model.CommonResponseModel;
import com.tzrcb.dispatch.model.table.dispatch.DispatchRecord;
import com.tzrcb.dispatch.model.table.dispatch.ServiceConfig;
import com.tzrcb.dispatch.service.DispatchRecordService;
import com.tzrcb.dispatch.util.CommonUtils;
import com.tzrcb.dispatch.util.JsonUtils;
import com.tzrcb.dispatch.util.check.DataCheckUtil;
import com.tzrcb.dispatch.util.db.DispatchRecordUtil;

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
	
	private static DispatchRecordService dispatchRecordService = Aop.get(DispatchRecordService.class);
	
	/**
	 * 路由分发
	 * @param messageDTO
	 * @return
	 */
	public static CommonResponseModel route(MessageDTO messageDTO) {
		CommonResponseModel resp = new CommonResponseModel();
		CommonRequestModel<?> req = JsonUtils.toObject(messageDTO.getJsonStr(), CommonRequestModel.class);
		String transNo = "";
		String className = "";
		boolean isRouteExist = false;
		try {
			req.setTransMsg(messageDTO.getJsonStr());
		
			resp = check(messageDTO, req);
			if(StringUtils.isNotNull(resp.getCode())) {
				return resp;
			}
			transNo = req.getTransNo();
			//分发
			className = "com.tzrcb.dispatch.core.route.handle._" + transNo + "_handler";
		
			Boolean v = routeMap.get(className);
			if(v != null && !v.booleanValue()) {
				//不存在该路由
				resp.setCode("1001");
				resp.setMessage("该交易码[" + transNo + "]未实现...");
			}else {
				IHander hanlder = (IHander) Class.forName(className).newInstance();
				resp = hanlder.handle(messageDTO);
				isRouteExist = true;
				resp.setTransMsg(JsonUtils.toJson(resp));
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
			DispatchRecordUtil.saveAsyn(req, resp);
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
		//1. 检查必输字段
		if(StringUtils.isNotNull(checkMsg)) {
			resp.setCode(CodeConfig.ERR_1001);
			resp.setMessage(checkMsg);
			return resp;
		}
		if(StringUtils.isNull(messageDTO.getSign())) {
			resp.setCode(CodeConfig.ERR_1001);
			resp.setMessage("签名不能为空,请在Header中输入,字段名sign");
			return resp;
		}
		//2. 检查是否有权限
		String transNo = req.getTransNo();
		String source = req.getSource();
		String target = req.getTarget();
		ServiceConfig serviceConfig = ConstConfig.getService(source);
		if(serviceConfig == null) {
			resp.setCode(CodeConfig.ERR_1001);
			String msg = CommonUtils.formatStr("鉴权失败,消费者编号[{}]未配置", source);
			resp.setMessage(msg);
			return resp;
		}else if(ConstConfig.getService(target) == null) {
			resp.setCode(CodeConfig.ERR_1001);
			String msg = CommonUtils.formatStr("鉴权失败,服务端编号[{}]未配置", source);
			resp.setMessage(msg);
			return resp;
		}else if(ConstConfig.getRoute(transNo + "-" + source + "-" + target) == null) {
			resp.setCode(CodeConfig.ERR_1001);
			String msg = CommonUtils.formatStr("鉴权失败,交易码[{}]未配置[{}->{}]路由", transNo, source, target);
			resp.setMessage(msg);
			return resp;
		}
		//3. 验签
		String encodeStr = CommonUtils.encodeSHA256(messageDTO.getJsonStr(), serviceConfig.getLicense());
		if(!encodeStr.equals(messageDTO.getSign())) {
			resp.setCode(CodeConfig.ERR_1001);
			resp.setMessage("鉴权失败,签名不符");
			return resp;
		}
		
		//4. 判断是否重复交易
		DispatchRecord dispatchRecord = dispatchRecordService.queryDispatchRecordBySerno(source, req.getSerno());
		if(dispatchRecord != null) {
			resp.setCode(CodeConfig.ERR_2222);
			String msg = CommonUtils.formatStr("流水重复,该流水号已在[{}]交易成功", dispatchRecord.getCreateTime());
			resp.setMessage(msg);
		}
		return resp;
	}
}
