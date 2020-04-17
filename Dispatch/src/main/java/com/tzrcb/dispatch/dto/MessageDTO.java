/**
 * File Name: RequestDTO.java
 * Date: 2020-04-16 19:53:54
 */
package com.tzrcb.dispatch.dto;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-04-16
 * @version 1.0
 */
public class MessageDTO {
	
	private String jsonStr;
	
	private String sign;
	
	public MessageDTO() {}
	
	public MessageDTO(String jsonStr, String sign) {
		super();
		this.jsonStr = jsonStr;
		this.sign = sign;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}


}
