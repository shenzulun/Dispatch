/**
 * File Name: _10010101RequestBody.java
 * Date: 2020-04-16 16:19:14
 */
package com.tzrcb.dispatch.model.api._10010101;

/**
 * Description: 普通短信发送-请求报文体
 * @author shenzulun
 * @date 2020-04-16
 * @version 1.0
 */
public class _10010101RequestBody {

	private String phone;
	
	private String content;

	public _10010101RequestBody() {}
	
	public _10010101RequestBody(String phone, String content) {
		super();
		this.phone = phone;
		this.content = content;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
