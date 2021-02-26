package com.deepspc.stage.sys.system.model;

import java.io.Serializable;

/**
 * @author gzw
 * @date 2020/11/26 15:04
 */
public class LoginParam implements Serializable {
    private static final long serialVersionUID = -505556349721295864L;

    private String account;

    private String password;

    private String verifyCode;

    private String remember;

    public LoginParam() {

    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getRemember() {
        return remember;
    }

    public void setRemember(String remember) {
        this.remember = remember;
    }
}
