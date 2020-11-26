package com.deepspc.stage.manager.system.service.impl;

import com.deepspc.stage.core.common.CryptoKey;
import com.deepspc.stage.core.utils.CryptoUtil;
import com.deepspc.stage.manager.system.service.ISystemService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author gzw
 * @date 2020/11/26 14:54
 */
@Service
public class SystemService implements ISystemService {

    @Cacheable(value="ClockCryptoKey",key="'ClockPublicKey'")
    @Override
    public CryptoKey refreshClockCryptoKey() {
        return CryptoUtil.getRSACryptoKey();
    }
}
