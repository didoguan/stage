package com.deepspc.stage.core.utils;

import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;

/**
 * @author gzw
 * @date 2020/12/5 17:03
 */
public class StringUtil extends StrUtil {

    public static String nullToDefault(Object obj) {
        return nullToDefault(obj, null);
    }

    public static String nullToDefault(Object obj, String defStr) {
        return obj == null ? defStr : obj.toString();
    }

    public static <T> T toOriginType(Object obj, Class<T> clazz) {
        if (clazz == Integer.class) {
            return obj == null ? null : (T) Integer.valueOf(obj.toString());
        } else if (clazz == Long.class) {
            return obj == null ? null : (T) Long.valueOf(obj.toString());
        } else if (clazz == Float.class) {
            return obj == null ? null : (T) Float.valueOf(obj.toString());
        } else if (clazz == Double.class) {
            return obj == null ? null : (T) Double.valueOf(obj.toString());
        } else if (clazz == BigDecimal.class) {
            return obj == null ? null : (T) new BigDecimal(obj.toString());
        } else {
            return null;
        }
    }
}
