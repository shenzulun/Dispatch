/**
 * File Name: AbstractRouter.java
 * Date: 2020-04-17 08:32:08
 */
package com.tzrcb.dispatch.core.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tzrcb.dispatch.dto.MessageDTO;
import com.tzrcb.dispatch.model.CommonResponseModel;

/**
 * Description: 抽象路由层
 * @author shenzulun
 * @date 2020-04-17
 * @version 1.0
 */
public abstract class AbstractRouter implements IHander{
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	public CommonResponseModel handle(MessageDTO messageDTO) {
		log.info("开始报文分发...");
		CommonResponseModel resp = execute(messageDTO);
		log.info("报文分发成功");
		return resp;
	}
	
	public abstract CommonResponseModel execute(MessageDTO messageDTO);
	
}
