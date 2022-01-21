package com.deepspc.stage.core.utils;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工具类
 * @author gzw
 * @date 2020/11/20 13:41
 */
public class StageUtil {

    /**
     * 线程安全集合
     */
    public static final ConcurrentHashMap<String, Object> CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();

    /**
     * 密钥串
     */
    public static String SECRET_KEY = "com.deepspc.kp5y2A";

    /**
     * 获取指定位数随机字符串
     * @param length 字符串长度
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }

}
