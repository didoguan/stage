package com.deepspc.stage.core.common;

/**
 * 公、私钥对象
 * @author gzw
 * @date 2020/9/19 15:32
 */
public class CryptoKey {

    private String privateKey;

    private String publicKey;

    public CryptoKey() {

    }

    public CryptoKey(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
