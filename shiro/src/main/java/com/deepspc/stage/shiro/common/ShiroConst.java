package com.deepspc.stage.shiro.common;

/**
 * @author gzw
 * @date 2020/11/24 16:53
 */
public interface ShiroConst {

    String HASH_ALGORITHM_NAME = "md5";

    //循环次数
    int HASH_ITERATIONS = 1024;

    String CACHE_REDIS = "redis";

    String CACHE_EHCACHE = "ehcache";

    String COOKIE_USER_ID = "stage_user_id";

}
