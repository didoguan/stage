package com.deepspc.stage.dataplatform.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 判断请求所使用的协议(socket/websocket)
 * 解码
 * 在服务端收到数据的时候，将字节流转换为实体对象Message
 * @author gzw
 * @date 2022/1/19 10:28
 */
@Slf4j
public class SwitchProtocolHandle extends ByteToMessageDecoder {

    //默认暗号长度
    private static final int MAX_LENGTH = 23;
    /**
     * websocket握手的协议前缀
     */
    private static final String WEBSOCKET_PREFIX = "GET /";

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        String protocol = getBufStart(byteBuf);
        if (protocol.startsWith(WEBSOCKET_PREFIX)) {
            PipelineAdd pipelineAdd = new PipelineAdd();
            pipelineAdd.websocketAdd(ctx);
        } else if (protocol.contains("MQTT")) {
            PipelineAdd pipelineAdd = new PipelineAdd();
            pipelineAdd.mqttAdd(ctx);
        }
        byteBuf.resetReaderIndex();
        ctx.pipeline().remove(this.getClass());
    }

    private String getBufStart(ByteBuf in){
        int length = in.readableBytes();
        if (length > MAX_LENGTH) {
            length = MAX_LENGTH;
        }
        //标记读位置
        in.markReaderIndex();
        byte[] content = new byte[length];
        in.readBytes(content);
        return new String(content);
    }
}
