package com.deepspc.stage.core.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.deepspc.stage.core.common.CryptoKey;

import java.io.UnsupportedEncodingException;

/**
 * @author 加解密工具类
 * @date 2020/9/19 14:45
 */
public class CryptoUtil {
    /**
     * 获取AES随机密钥
     * @return
     */
    public static String getAesRandomKey() {
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        return new String(key);
    }

    /**
     * 根据密钥加密内容
     * @param key
     * @param content
     * @return
     */
    public static String aesEncrypt(String key, String content) {
        byte[] encrypt = SecureUtil.aes(key.getBytes()).encrypt(content);
        try {
            return new String(encrypt, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据密钥解密
     * @param key
     * @param encrypt
     * @return
     */
    public static String aesDecrypt(String key, String encrypt) {
        byte[] decrypt = SecureUtil.aes(key.getBytes()).decrypt(encrypt);
        return new String(decrypt);
    }

    /**
     * 获取公私钥
     * @return
     */
    public static CryptoKey getRSACryptoKey() {
        RSA rsa = new RSA();
        return new CryptoKey(rsa.getPublicKeyBase64(), rsa.getPrivateKeyBase64());
    }

    /**
     * 公钥加密
     * @param privateKey
     * @param publicKey
     * @param content
     * @return
     */
    public static String publicKeyEncrypt(String privateKey, String publicKey, String content) {
        RSA rsa = new RSA(privateKey, publicKey);
        return rsa.encryptBase64(content, KeyType.PublicKey);
    }

    /**
     * 公钥解密
     * @param publicKey
     * @param privateKey
     * @param content
     * @return
     */
    public static String publicKeyDecrypt(String privateKey, String publicKey, String content) {
        RSA rsa = new RSA(privateKey, publicKey);
        return rsa.decryptStr(content, KeyType.PublicKey);
    }

    /**
     * 私钥加密
     * @param privateKey
     * @param publicKey
     * @param content
     * @return
     */
    public static String privateKeyEncrypt(String privateKey, String publicKey, String content) {
        RSA rsa = new RSA(privateKey, publicKey);
        return rsa.encryptBase64(content, KeyType.PrivateKey);
    }

    /**
     * 私钥解密
     * @param publicKey
     * @param privateKey
     * @param content
     * @return
     */
    public static String privateKeyDecrypt(String privateKey, String publicKey, String content) {
        RSA rsa = new RSA(privateKey, publicKey);
        return rsa.decryptStr(content, KeyType.PrivateKey);
    }
}
