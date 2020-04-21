/**
 * File Name: CommonResponseModel.java
 * Date: 2020-04-16 16:22:16
 */
package com.tzrcb.dispatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-04-16
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class CommonResponseModel extends CommonHeader{
	private static final long serialVersionUID = 3963352443091454033L;
	
	private String code;
	
	private String message;
	
	private Object data;
	
	private PageItem page;
	
	public CommonResponseModel() {}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public PageItem getPage() {
		return page;
	}

	public void setPage(PageItem page) {
		this.page = page;
	}
	
}
