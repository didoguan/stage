package com.deepspc.stage.shiro.common;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author gzw
 * @date 2020/11/19
 **/
public class CustomJwtToken extends UsernamePasswordToken {

	private String token;

	public CustomJwtToken(String token) {
		this.token = token;
	}

	/**
	 * 用户身份信息(用户名)
	 * @return
	 */
	@Override
	public Object getPrincipal() {
		return token;
	}

	/**
	 * 用户凭证信息(密码)
	 * @return
	 */
	@Override
	public Object getCredentials() {
		return token;
	}
}
