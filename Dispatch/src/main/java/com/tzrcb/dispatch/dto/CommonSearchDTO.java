/**
 * File Name: CommonSearchDTO.java
 * Date: 2020-04-20 08:52:51
 */
package com.tzrcb.dispatch.dto;

/**
 * Description: HTTP请求的通用DTO
 * @author shenzulun
 * @date 2020-04-20
 * @version 1.0
 */
public class CommonSearchDTO {
	
	/**
	 * 查询值
	 */
	private String queryValue;
	/**
	 * 排序字段
	 */
	private String orderBy;
	/**
	 * 排序条件
	 */
	private String orderDir;
	/**
	 * 分页条数
	 */
	private int pageSize = 10;
	/**
	 * 当前页数
	 */
	private int pageNumber = 1;
	
	public String getQueryValue() {
		return queryValue;
	}
	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getOrderDir() {
		return orderDir;
	}
	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}
