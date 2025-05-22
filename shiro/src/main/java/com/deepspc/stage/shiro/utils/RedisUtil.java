package com.deepspc.stage.shiro.utils;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * @author gzw
 * @date 2021/11/25 10:34
 */
@Component
@ConditionalOnProperty(value = "spring.cache.type", havingValue = "redis")
public final class RedisUtil {

    /**
     * 默认失效时长
     */
    public final int DEFAULT_EXPIRE_SECONDS = 60;

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<String, Object> redisTemplate, RedisTemplate<String, String> stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 指定缓存失效时间
     * @param key 缓存key(不能为空)
     * @param time 失效时间(秒)-小于等于0则默认3分钟
     */
    public Boolean expire(String key, long time) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        if (time <= 0) {
            time = DEFAULT_EXPIRE_SECONDS;
        }
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 获取失效时间
     * @param key 缓存key(不能为空)
     * @return 失效时间(秒)
     */
    public Long getExpire(String key) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 要检查的key
     * @return true-存在
     */
    public boolean hasKey(String key) {
        if (StrUtil.isBlank(key)) {
            return false;
        }
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     * @param keys 要删的缓存key
     */
    public void remove(List<String> keys) {
        if (null != keys && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除缓存
     * @param keys 要删的缓存key
     */
    public void remove(String... keys) {
        if (null != keys && keys.length > 0) {
            redisTemplate.delete(Arrays.asList(keys));
        }
    }

    /**
     * 获取普通缓存
     * @param key 要获取的缓存
     * @return 缓存值
     */
    public Object normalGet(String key) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 存入普通缓存，有效期为无限
     * @param key 缓存键
     * @param value 缓存值
     */
    public void normalSet(String key, Object value) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 存入普通缓存并设置失效时间
     * @param key 缓存键
     * @param value 缓存值
     * @param time 失效时间(秒)，小于等于0则为无限
     */
    public void normalSet(String key, Object value, long time) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            normalSet(key, value);
        }
    }

    /**
     * 缓存递增
     * @param key 缓存键
     * @param delta 递增因子，大于0
     * @return 递增后的值
     */
    public Long normalIncr(String key, long delta) {
        if (StrUtil.isBlank(key) || delta <= 0) {
            return null;
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 缓存递减
     * @param key 缓存键
     * @param delta 递减因子，大于0
     * @return 递减后的值
     */
    public Long normalDecr(String key, long delta) {
        if (StrUtil.isBlank(key) || delta <= 0) {
            return null;
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 设置字符串类型缓存
     * @param key 缓存键
     * @param value 缓存值
     */
    public void stringSet(String key, String value) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置字符串类型缓存
     * @param key 缓存键
     * @param value 缓存值
     * @param time 有效期(秒)，如果小于0则永不过期
     */
    public void stringSet(String key, String value, long time) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        if (time > 0) {
            stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            stringSet(key, value);
        }
    }

    /**
     * 获取字符串类型缓存
     * @param key 缓存键
     * @return 缓存值
     */
    public String stringGet(String key) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 字符串类型缓存递增
     * @param key 缓存键
     * @param delta 递增因子，大于0
     * @return 递增后的值
     */
    public Long stringIncr(String key, long delta) {
        if (StrUtil.isBlank(key) || delta <= 0) {
            return null;
        }
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 字符串类型缓存递减
     * @param key 缓存键
     * @param delta 递减因子，大于0
     * @return 递减后的值
     */
    public Long stringDecr(String key, long delta) {
        if (StrUtil.isBlank(key) || delta <= 0) {
            return null;
        }
        return stringRedisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 获取指定map键的值
     * @param key 缓存键
     * @param item map键
     * @return map键的值
     */
    public Object hashGet(String key, String item) {
        if (StrUtil.isBlank(key) || StrUtil.isBlank(item)) {
            return null;
        }
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取所有键值对
     * @param key hash缓存键
     * @return 返回多个键值
     */
    public Map<Object, Object> mapGet(String key) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 以汇合方式增加键值对
     * @param key hash缓存键
     * @param map map对象
     */
    public void mapSet(String key, Map<String, Object> map) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 以汇合方式增加键值对并设置失效时间
     * @param key 缓存键
     * @param map map对象
     * @param time 失效时间(秒)，小于等于0则为无限
     */
    public void mapSet(String key, Map<String, Object> map, long time) {
        mapSet(key, map);
        expire(key, time);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 缓存键
     * @param item 缓存项
     * @param value 项值
     */
    public void hashSet(String key, String item, Object value) {
        if (StrUtil.isBlank(key) || StrUtil.isBlank(item)) {
            return;
        }
        redisTemplate.opsForHash().put(key, item, value);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建，并设置失效时间
     * @param key 缓存键
     * @param item 缓存项
     * @param value 项值
     */
    public void hashSet(String key, String item, Object value, long time) {
        hashSet(key, item, value);
        expire(key, time);
    }

    /**
     * 删除hash表中多个值
     * @param key 缓存键
     * @param item 要删的值
     */
    public void hashDelete(String key, Object... item) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断是否存在hash键
     * @param key 缓存键
     * @param item 缓存项
     * @return true-存在 false-不存在
     */
    public boolean hasHashKey(String key, String item) {
        if (StrUtil.isBlank(key)) {
            return false;
        }
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 缓存键
     * @param item 缓存项
     * @param delta 递增因子，大于0
     * @return 递增后的值
     */
    public Double hashIncr(String key, String item, double delta) {
        if (StrUtil.isBlank(key) || delta <= 0) {
            return null;
        }
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * hash递减 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 缓存键
     * @param item 缓存项
     * @param delta 递减因子，大于0
     * @return 递减后的值
     */
    public Double hashDecr(String key, String item, double delta) {
        if (StrUtil.isBlank(key) || delta <= 0) {
            return null;
        }
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }

    /**
     * 根据key获取set中的值
     * @param key 缓存键
     * @return Set<Object>
     */
    public Set get(String key) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 判断Set是否存在value
     * @param key 缓存键
     * @param value 要判断的值
     * @return true-存在 false-不存在
     */
    public boolean hasSetKey(String key, Object value) {
        if (StrUtil.isBlank(key)) {
            return false;
        }
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 把值缓存到Set中
     * @param key 缓存键
     * @param values 缓存值
     */
    public void set(String key, Object... values) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 把值缓存到set中，并设置失效时间
     * @param key 缓存键
     * @param time 失效时间(秒)
     * @param values 缓存值
     */
    public void set(String key, long time, Object... values) {
        set(key, values);
        expire(key, time);
    }

    /**
     * 获取set缓存中对应key缓存值的长度
     * @param key 缓存
     * @return 值数量
     */
    public long getSetSize(String key) {
        if (StrUtil.isBlank(key)) {
            return -1L;
        }
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 删除指定key中Set值，如果指定值不传则删除指定key的所有Set值
     * @param key 缓存键
     * @param values 要删除的值
     */
    public void setDelete(String key, Object... values) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        if (null != values && values.length > 0) {
            redisTemplate.opsForSet().remove(key, values);
        } else {
            Set<Object> members = redisTemplate.opsForSet().members(key);
            redisTemplate.opsForSet().remove(key, members);
        }
    }

    /**
     * 获取list缓存的内容
     * @param key 缓存键
     * @param start 开始
     * @param end 结束 0 到 -1代表所有值
     * @return 缓存的list内容
     */
    public List listGet(String key, long start, long end) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     * @param key 缓存键
     * @return list的大小
     */
    public Long getListSize(String key) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     * @param key 缓存键
     * @param index 下标
     * @return list中的值
     */
    public Object getListByIndex(String key, long index) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 放入list缓存
     * @param key 缓存键
     * @param value 缓存值
     */
    public void listSet(String key, Object value) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 放入list缓存并设置失效时间
     * @param key 缓存键
     * @param value 缓存值
     * @param time 失效时间
     */
    public void listSet(String key, Object value, long time) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        listSet(key, value);
        expire(key, time);
    }

    /**
     * 放入list缓存
     * @param key 缓存键
     * @param value 缓存值
     */
    public void listSet(String key, List<Object> value) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 放入list缓存并设置失效时间
     * @param key 缓存键
     * @param value 缓存值
     * @param time 失效时间
     */
    public void listSet(String key, List<Object> value, long time) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        listSet(key, value);
        expire(key, time);
    }

    /**
     * 更新list缓存指定index的值
     * @param key 缓存键
     * @param index 下标
     * @param value 要修改的值
     */
    public void updateListByIndex(String key, long index, Object value) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 从存储在键中的列表中删除等于值的元素的第一个计数事件。
     * count<0时删除了整个list，count>=0是删除的是等于value的所有元素
     * @param key 缓存键
     * @param count 删除数量
     * @param value 缓存的值
     */
    public void listRemove(String key, long count, Object value) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        redisTemplate.opsForList().remove(key, count, value);
    }

}
