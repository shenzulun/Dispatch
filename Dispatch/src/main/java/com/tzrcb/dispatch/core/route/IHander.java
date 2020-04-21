/**
 * File Name: IHander.java
 * Date: 2020-04-17 08:32:42
 */
package com.tzrcb.dispatch.core.route;

import com.tzrcb.dispatch.dto.MessageDTO;
import com.tzrcb.dispatch.model.CommonResponseModel;

/**
 * Description: 服务接口
 * @author shenzulun
 * @date 2020-04-17
 * @version 1.0
 */
public interface IHander {

	public CommonResponseModel handle(MessageDTO messageDTO);
}
