package com.deepspc.stage.sys.utils;

import com.deepspc.stage.core.exception.StageException;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * ehcache缓存工具类
 * @author gzw
 * @date 2020/11/28 10:57
 */
@Slf4j
public class EhCacheUtil {

    public static final String IDLE_SECONDS = "idle";
    public static final String LIVE_SECONDS = "live";

    private static CacheManager manager;

    static {
        try {
            manager = CacheManager.create(EhCacheUtil.class.getClassLoader().getResourceAsStream("ehcache.xml"));
        } catch (CacheException e) {
            log.error("初始化ehcache缓存工具类时找不到ehcache.xml配置文件", e);
        }
    }

    /**
     * 检查是否存在对应缓存
     * @param cacheName 缓存名称
     * @return Cache
     */
    private static Cache checkCache(String cacheName) {
        Cache cache = manager.getCache(cacheName);
        if (null == cache) {
            throw new StageException("找不到对应缓存名称:" + cacheName);
        }
        return cache;
    }

    /**
     * 添加对象到指定缓存
     * @param cacheName 缓存名称
     * @param key 键值
     * @param obj 要缓存的对象
     */
    public static void put(String cacheName, String key, Object obj) {
        Cache cache = checkCache(cacheName);
        Element element = new Element(key, obj);
        cache.put(element);
    }

    /**
     * 添加对象到指定缓存
     * @param cacheName 缓存名称
     * @param key 键值
     * @param obj 要缓存的对象
     * @param secondType 失效时间类型
     * @param seconds 失效时间，单位秒
     */
    public static void put(String cacheName, String key, Object obj, String secondType, int seconds) {
        if (!IDLE_SECONDS.equals(secondType) && !LIVE_SECONDS.equals(secondType)) {
            put(cacheName, key, obj);
        } else {
            Cache cache = checkCache(cacheName);
            Element element = new Element(key, obj);
            if (IDLE_SECONDS.equals(secondType)) {
                element.setTimeToIdle(seconds);
            } else if (LIVE_SECONDS.equals(secondType)) {
                element.setTimeToLive(seconds);
            }
            cache.put(element);
        }
    }

    /**
     * 添加对象到指定缓存
     * @param cacheName 缓存名称
     * @param key 键值
     * @param obj 要缓存的对象
     * @param idleSeconds 最后一次访问缓存之时至失效之时的时间间隔
     * @param liveSeconds 缓存自创建之时起至失效时的间隔时间
     */
    public static void put(String cacheName, String key, Object obj, int idleSeconds, int liveSeconds) {
        Cache cache = checkCache(cacheName);
        Element element = new Element(key, obj);
        element.setTimeToIdle(idleSeconds);
        element.setTimeToLive(liveSeconds);
        cache.put(element);
    }

    /**
     * 获取缓存对象
     * @param cacheName 缓存名称
     * @param key 键值
     * @return 缓存的对象
     */
    public static <T> T get(String cacheName, String key) {
        Cache cache = checkCache(cacheName);
        Element element = cache.get(key);
        if (null != element) {
            return (T) element.getObjectValue();
        } else {
            return null;
        }
    }

    /**
     * 删除缓存对象
     * @param cacheName 缓存名称
     * @param key 键值
     */
    public static void remove(String cacheName, String key) {
        Cache cache = checkCache(cacheName);
        cache.remove(key);
    }
}
