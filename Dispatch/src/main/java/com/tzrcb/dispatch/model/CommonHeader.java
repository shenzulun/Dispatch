/**
 * File Name: CommonHeader.java
 * Date: 2020-04-16 15:57:42
 */
package com.tzrcb.dispatch.model;

import com.tzrcb.dispatch.util.check.DataCheck;

/**
 * Description: 通用报文头
 * @author shenzulun
 * @date 2020-04-16
 * @version 1.0
 */
public class CommonHeader implements java.io.Serializable{
	private static final long serialVersionUID = -8459049437445635777L;
	/**
	 * 交易码
	 */
	@DataCheck(required=true)
	private String transNo;
	/**
	 * 消费者
	 */
	@DataCheck(required=true)
	private String source;
	/**
	 * 生产者(服务端)
	 */
	@DataCheck(required=true)
	private String target;
	/**
	 * 交易时间
	 */
	private String transTime;
	/**
	 * 版本号(备用字段)
	 */
	private String version;
	
	public CommonHeader() {}
	
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	
	

}
