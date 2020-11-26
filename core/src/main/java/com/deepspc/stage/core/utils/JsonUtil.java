package com.deepspc.stage.core.utils;

import com.deepspc.stage.core.exception.CoreExceptionCode;
import com.deepspc.stage.core.exception.StageException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author gzw
 * @date 2020/11/26 15:12
 */
public class JsonUtil {

    /**
     * json串转普通对象，对象只具有普通属性
     * @param json 要转换的json串
     * @param clazz 要转换的对象类型
     */
    public static <T> T parseSimpleObj(String json, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new StageException(CoreExceptionCode.JSON_PARSE_EXCEPTION.getCode(),
                        CoreExceptionCode.JSON_PARSE_EXCEPTION.getMessage());
        }
    }

    /**
     * 对象转换为json字符串
     * @param obj 要转换的对象
     */
    public static String obj2json(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new StageException(CoreExceptionCode.JSON_PARSE_EXCEPTION.getCode(),
                        CoreExceptionCode.JSON_PARSE_EXCEPTION.getMessage());
        }
    }

    /**
     * json串转复杂对象
     * @param json 要转换的字符串
     */
    public static <T> T parseComplexObj(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, new TypeReference<T>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new StageException(CoreExceptionCode.JSON_PARSE_EXCEPTION.getCode(),
                        CoreExceptionCode.JSON_PARSE_EXCEPTION.getMessage());
        }
    }

    /**
     * json串转复杂对象
     * @param json 要转换的字符串
     * @param type 对象内复杂类型
     */
    public static  <T> T parseComplexObj(String json, TypeReference<T> type) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new StageException(CoreExceptionCode.JSON_PARSE_EXCEPTION.getCode(),
                    CoreExceptionCode.JSON_PARSE_EXCEPTION.getMessage());
        }
    }
}
