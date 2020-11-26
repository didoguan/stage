package com.deepspc.stage.manager.system.model;

import java.io.Serializable;

/**
 * @author gzw
 * @date 2020/11/26 15:04
 */
public class LoginParam implements Serializable {
    private static final long serialVersionUID = -505556349721295864L;

    private String account;

    private String password;

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
}
