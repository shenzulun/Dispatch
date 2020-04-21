/**
 * File Name: SmsConfig.java
 * Date: 2020-04-21 10:07:22
 */
package com.tzrcb.dispatch.core.server.sms;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-04-21
 * @version 1.0
 */
public class SmsConfig {

	private String remoteHost;
	
	private int remotePort;
	
	private String userName;
	
	private String password;
	
	public SmsConfig() {};

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
