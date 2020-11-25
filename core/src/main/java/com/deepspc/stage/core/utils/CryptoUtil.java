package com.deepspc.stage.core.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.deepspc.stage.core.common.CryptoKey;
import com.deepspc.stage.core.exception.CoreExceptionCode;
import com.deepspc.stage.core.exception.StageException;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author 加解密工具类
 * @date 2020/9/19 14:45
 */
public class CryptoUtil {

    private static final String PUBLIC_KEY = "com.deepspc.GD2020.pubkey";

    private static final String PRIVATE_KEY = "com.deepspc.GD2020.prikey";

    /**
     * 获取自定义密钥对
     * @return
     */
    public static CryptoKey getCustomCryptoKey() {
        try {
            KeyFactory pubFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec pubSpec = new X509EncodedKeySpec((new BASE64Decoder()).decodeBuffer(PUBLIC_KEY));
            RSAPublicKey publicKey = (RSAPublicKey) pubFactory.generatePublic(pubSpec);

            PKCS8EncodedKeySpec priSpec = new PKCS8EncodedKeySpec((new BASE64Decoder()).decodeBuffer(PRIVATE_KEY));
            KeyFactory priFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) priFactory.generatePrivate(priSpec);

            String publicStr = Base64.encode(publicKey.getEncoded());
            String privateStr = Base64.encode(privateKey.getEncoded());
            return new CryptoKey(publicStr, privateStr);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            throw new StageException(CoreExceptionCode.INSTANCE_CRYPTO_KEY_EXCEPTION.getCode(),
                                    CoreExceptionCode.INSTANCE_CRYPTO_KEY_EXCEPTION.getMessage());
        }
    }
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
