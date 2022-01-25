package com.deepspc.stage.dataplatform.utils;

import cn.hutool.core.util.StrUtil;
import com.deepspc.stage.dataplatform.netty.model.NettyRespData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;

/**
 * @author gzw
 * @date 2022/1/20 15:02
 */
public class DataPlatformUtil {

    public static ByteBuf writeSockData(String code, String msg) throws UnsupportedEncodingException {
        NettyRespData respData = new NettyRespData();
        respData.setCode(code);
        respData.setMsg(msg);
        String back = respData.toString();
        byte[] strByte = back.getBytes("UTF-8");
        return Unpooled.wrappedBuffer(strByte);
    }

    public static ByteBuf writeSockStr(String msg) throws UnsupportedEncodingException {
        if (StrUtil.isNotBlank(msg)) {
            byte[] strByte = msg.getBytes("UTF-8");
            return Unpooled.wrappedBuffer(strByte);
        }
        return null;
    }

}
