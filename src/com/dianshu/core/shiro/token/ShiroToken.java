package com.dianshu.core.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

public class ShiroToken extends UsernamePasswordToken{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7690182047449952807L;
	private String pswd;
	
	public ShiroToken(String username,String pswd) {
		super(username,pswd);
		this.setPswd(pswd);
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
}
