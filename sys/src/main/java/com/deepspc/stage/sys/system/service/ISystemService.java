package com.deepspc.stage.sys.system.service;

import com.deepspc.stage.core.common.CryptoKey;

public interface ISystemService {

    /**
     * 刷新固定公私钥
     * @return
     */
    CryptoKey refreshClockCryptoKey();
}
