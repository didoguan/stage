package com.deepspc.stage.sys.system.model;

import java.io.Serializable;

/**
 * @author gzw
 * @date 2020/12/7 15:48
 */
public class ModifyPassword implements Serializable {
    private static final long serialVersionUID = -815122368392378965L;

    private String oldPassword;

    private String newPassword;

    public ModifyPassword() {

    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
