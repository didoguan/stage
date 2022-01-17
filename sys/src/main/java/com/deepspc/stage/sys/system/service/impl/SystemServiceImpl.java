package com.deepspc.stage.sys.system.service.impl;

import com.deepspc.stage.core.common.CryptoKey;
import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.core.utils.CryptoUtil;
import com.deepspc.stage.core.utils.JsonUtil;
import com.deepspc.stage.shiro.utils.RedisUtil;
import com.deepspc.stage.sys.common.SysPropertiesConfig;
import com.deepspc.stage.sys.constant.Const;
import com.deepspc.stage.sys.system.service.ISystemService;
import com.deepspc.stage.sys.utils.EhCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gzw
 * @date 2020/11/26 14:54
 */
@Service
public class SystemServiceImpl implements ISystemService {

    private final SysPropertiesConfig sysPropertiesConfig;

    @Autowired
    public SystemServiceImpl(SysPropertiesConfig sysPropertiesConfig) {
        this.sysPropertiesConfig = sysPropertiesConfig;
    }

    @Override
    public CryptoKey refreshClockCryptoKey() {
        CryptoKey cryptoKey = null;
        String cacheType = sysPropertiesConfig.getCacheType();
        if (Const.cacheEhcache.equals(cacheType)) {
            cryptoKey = EhCacheUtil.get(Const.clockCryptoKey, "ClockPublicKey");
            if (null == cryptoKey) {
                cryptoKey = CryptoUtil.getRSACryptoKey();
                EhCacheUtil.put(Const.clockCryptoKey, "ClockPublicKey", cryptoKey);
            }
        } else if (Const.cacheRedis.equals(cacheType)) {
            RedisUtil redisUtil = ApplicationContextUtil.getBean(RedisUtil.class);
            Object str = redisUtil.normalGet("ClockPublicKey");
            if (null == str) {
                cryptoKey = CryptoUtil.getRSACryptoKey();
                //有效期30天
                redisUtil.normalSet("ClockPublicKey", cryptoKey.toString(), 30 * 24 * 60);
            } else {
                cryptoKey = JsonUtil.parseSimpleObj(str.toString(), CryptoKey.class);
            }
        }
        return cryptoKey;
    }
}
