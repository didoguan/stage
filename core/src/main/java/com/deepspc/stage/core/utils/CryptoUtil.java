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

    private static String PUBLIC_KEY;

    private static String PRIVATE_KEY;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCzKs3dNindQKgnfeVa+bOVwmI3yGS6mKrqT/")
        .append("N2zfZqbwuelavyE46oo0PwAllqA6Bz3SpNX06FP51F504ekuUU9GbmCWVVyIBZ6b3lCIhhrtFKe")
        .append("SQYiAeoyCS3GRDqWZB4v9GHsxazz7XAMG91m27DDwKcpehe1x0M7JSpQIQVrwIDAQAB");
        PUBLIC_KEY = sb.toString();

        sb = new StringBuilder();
        sb.append("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALMqzd02Kd1AqCd95V")
        .append("r5s5XCYjfIZLqYqupP83bN9mpvC56Vq/ITjqijQ/ACWWoDoHPdKk1fToU/nUXnTh6S5RT")
        .append("0ZuYJZVXIgFnpveUIiGGu0Up5JBiIB6jIJLcZEOpZkHi/0YezFrPPtcAwb3WbbsMPApyl6")
        .append("F7XHQzslKlAhBWvAgMBAAECgYEAhFKurBvem2uvRUtz4tbcTVS82VoFGtK6GEdoFmxS+5R")
        .append("7zmnYDfdnuyCpgk8Z3nRDQo8rOO2UvlCXRRNp3Ka8zJf8DBBTqGMHSqBtp2W4CD7cER3wj")
        .append("bfhs4b2en9z2QLQseUkJxuQdmNeVeloiS9DJSaMkln94oAqLNnPhACwazECQQD0gcxhc6U")
        .append("ozJmrCAfIUiTI+zDZqXfj1Ba/BWnmnzyr7apsXru/zZA9qNrviUJbBuBiotQdVWFdEkpNn")
        .append("r62qan7AkEAu5bCPKGJPO+1lXBFo3rDSZtpvcQKiUXSHTXC+/nIuQ/Ulsx8oy4iJLNzfNf")
        .append("CC8yeRvFE+JLW5fIF+LwbpA2I3QJBAIUGuXOrv4fbCSAMVm+egXT3dTR3B0tk8JstDtjye")
        .append("cfwnnAnem54IKnrXHJGc1ui+iGwBUeQVFCWyuZAH/KxjFECQAPApUwPMy6b4PcHUu1NRGD")
        .append("RkhDwvgE2+1gIPklKGuDQ10DZAFlHT/mJ+XJy4nfX9QaYRvfuAAyDhekO4kKq3qUCQDBBxi")
        .append("gXZW2o1L9UWTWMV5NH8ibBANo1FQFi06SOVb2YrHcrNHuvLAy3kd7lbbp1sExOR/H4FI9jpemudpQbOGw=");
        PRIVATE_KEY = sb.toString();

        sb = null;
    }

    public static String getPublicKey() {
        return PUBLIC_KEY;
    }

    public static String getPrivateKey() {
        return PRIVATE_KEY;
    }

    /**
     * 获取AES随机密钥
     */
    public static String getAesRandomKey() {
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        return new String(key);
    }

    /**
     * 根据密钥加密内容
     * @param key 密钥
     * @param content 加密内容
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
     * @param key 密钥
     * @param encrypt 解密内容
     */
    public static String aesDecrypt(String key, String encrypt) {
        byte[] decrypt = SecureUtil.aes(key.getBytes()).decrypt(encrypt);
        return new String(decrypt);
    }

    /**
     * 获取公私钥
     */
    public static CryptoKey getRSACryptoKey() {
        RSA rsa = new RSA();
        return new CryptoKey(rsa.getPublicKeyBase64(), rsa.getPrivateKeyBase64());
    }

    /**
     * 公钥加密
     * @param privateKey 私钥
     * @param publicKey 公钥
     * @param content 加密内容
     */
    public static String publicKeyEncrypt(String privateKey, String publicKey, String content) {
        RSA rsa = new RSA(privateKey, publicKey);
        return rsa.encryptBase64(content, KeyType.PublicKey);
    }

    /**
     * 公钥加密(固定密钥)
     * @param content 加密内容
     */
    public static String publicKeyEncrypt(String content) {
        RSA rsa = new RSA(PRIVATE_KEY, PUBLIC_KEY);
        return rsa.encryptBase64(content, KeyType.PublicKey);
    }

    /**
     * 公钥解密
     * @param publicKey 公钥
     * @param privateKey 私钥
     * @param content 解密内容
     */
    public static String publicKeyDecrypt(String privateKey, String publicKey, String content) {
        RSA rsa = new RSA(privateKey, publicKey);
        return rsa.decryptStr(content, KeyType.PublicKey);
    }

    /**
     * 公钥解密(固定密钥)
     * @param content 解密内容
     */
    public static String publicKeyDecrypt(String content) {
        RSA rsa = new RSA(PRIVATE_KEY, PUBLIC_KEY);
        return rsa.decryptStr(content, KeyType.PublicKey);
    }

    /**
     * 私钥加密
     * @param privateKey 私钥
     * @param publicKey 公钥
     * @param content 加密内容
     */
    public static String privateKeyEncrypt(String privateKey, String publicKey, String content) {
        RSA rsa = new RSA(privateKey, publicKey);
        return rsa.encryptBase64(content, KeyType.PrivateKey);
    }

    /**
     * 私钥加密(固定密钥)
     * @param content 加密内容
     */
    public static String privateKeyEncrypt(String content) {
        RSA rsa = new RSA(PRIVATE_KEY, PUBLIC_KEY);
        return rsa.encryptBase64(content, KeyType.PrivateKey);
    }

    /**
     * 私钥解密
     * @param publicKey 公钥
     * @param privateKey 私钥
     * @param content 解密内容
     */
    public static String privateKeyDecrypt(String privateKey, String publicKey, String content) {
        RSA rsa = new RSA(privateKey, publicKey);
        return rsa.decryptStr(content, KeyType.PrivateKey);
    }

    /**
     * 私钥解密(固定密钥)
     * @param content 解密内容
     */
    public static String privateKeyDecrypt(String content) {
        RSA rsa = new RSA(PRIVATE_KEY, PUBLIC_KEY);
        return rsa.decryptStr(content, KeyType.PrivateKey);
    }

    public static void main(String[] args) {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKBhYo6vTJipqQ+IwoA3BMVTnbrwBsl2qeMW4MEiGKwt9kgeoNTQazxaV9/IQLHuEHSM0MXlf7zAbcGR720mClv7hmGNzbzOxMV11qwfDnF3Lzd1iFuBqAmwg7akFOrOvEXaQPZw8lJ5mZ5sj9jdPUyK8Zgq2ZEUJVEV6IoTgQEQIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIoGFijq9MmKmpD4jCgDcExVOduvAGyXap4xbgwSIYrC32SB6g1NBrPFpX38hAse4QdIzQxeV/vMBtwZHvbSYKW/uGYY3NvM7ExXXWrB8OcXcvN3WIW4GoCbCDtqQU6s68RdpA9nDyUnmZnmyP2N09TIrxmCrZkRQlURXoihOBARAgMBAAECgYBjnjL+Q1573Drk6U8XTiwOG/EPesZ+meaM2LZJpghpg28CtrV79h0hQUTJoqiW8imOPWApTiqIhOBqmQL9OCgiQuNEmVxA71XzzXSdPu5hkHjAnM6IuS/LhiDgKeDUmk9AnW5A8tlLNJsi2/xHB32H6QfxEKyLK9oXiQCNIvZ2BQJBAOu8D5+pyRQdre4tdr6GDzsd4Dibauwy6RtganIzAfMXs9m62MWzKfMWMBw+rH7ZSTWZw7ngQfvnQios0QIFxosCQQCV46Z9F3VxEzpTxkyBIftw4LlwMxNnr2NhTVLXFyro+A5Cl61mOvNdDGiOeb04ttIilPKWUzts2j8TmFHswTNTAkEA2Y43ERA9ve3iLjURKKZKejGJVFTC6ffWQGjL9Fb0zqeqWR/inRpLdcedYQPKaEsaEHi3NN0tqFB2NLwlrGsuHwI/WP7R23QSRxEjkJos0n9ay6qib/vjiayZVSyUAyG+WJTv4posWoal+JllezLNJt9l5SKK5raEkh0DVp8aBXixAkEAkf/SvNIAt2kEfdRkzCsUERvG5Oy8V5Dcgw298oAib8njq3VVMExx237b/Ju1UJ0diGzOyPUbtua8JESzE39wwQ==";
        String content = "{\"account\":\"admin\",\"password\":\"000000\"}";
        String str = publicKeyEncrypt(privateKey, publicKey, content);
        System.out.println(str);
    }

}
