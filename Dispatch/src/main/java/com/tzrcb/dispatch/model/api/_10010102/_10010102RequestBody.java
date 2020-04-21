/**
 * File Name: _10010102RequestBody.java
 * Date: 2020-04-16 18:08:31
 */
package com.tzrcb.dispatch.model.api._10010102;

import java.util.List;

/**
 * Description: 模板短信发送请求体
 * @author shenzulun
 * @date 2020-04-16
 * @version 1.0
 */
public class _10010102RequestBody {
	
	private String templateId;
	
	private List<_10010102RequestItem> items;
	
	public _10010102RequestBody() {}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public List<_10010102RequestItem> getItems() {
		return items;
	}

	public void setItems(List<_10010102RequestItem> items) {
		this.items = items;
	}
	
	

}
