/**
 * File Name: CommonRequestModel.java
 * Date: 2020-04-16 16:22:56
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
public class CommonRequestModel<T> extends CommonHeader{
	private static final long serialVersionUID = 7026137484909499579L;
	
	private T data;
	
	private PageItem page;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public PageItem getPage() {
		return page;
	}

	public void setPage(PageItem page) {
		this.page = page;
	}
	
	
}
