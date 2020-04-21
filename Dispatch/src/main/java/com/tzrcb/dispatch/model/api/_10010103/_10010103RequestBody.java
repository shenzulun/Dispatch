/**
 * File Name: _10010103RequestBody.java
 * Date: 2020-04-21 10:49:52
 */
package com.tzrcb.dispatch.model.api._10010103;

import com.tzrcb.dispatch.util.check.DataCheck;

/**
 * Description: 运维提醒
 * @author shenzulun
 * @date 2020-04-21
 * @version 1.0
 */
public class _10010103RequestBody {

	@DataCheck(required=true)
	private String phone;
	
	@DataCheck(required=true)
	private String sysName;
	
	@DataCheck(required=true)
	private String codeName;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
}
