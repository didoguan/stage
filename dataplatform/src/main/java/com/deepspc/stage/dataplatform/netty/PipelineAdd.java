package com.deepspc.stage.dataplatform.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author gzw
 * @date 2022/1/21 11:47
 */
public class PipelineAdd {

    public void websocketAdd(ChannelHandlerContext ctx){
        // HttpServerCodec：将请求和应答消息解码为HTTP消息
        ctx.pipeline().addBefore("inboundHandler","http-codec",new HttpServerCodec());
        // HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消息
        ctx.pipeline().addBefore("inboundHandler","aggregator",new HttpObjectAggregator(65535));
        // ChunkedWriteHandler：向客户端发送HTML5文件
        ctx.pipeline().addBefore("inboundHandler","http-chunked",new ChunkedWriteHandler());
        ctx.pipeline().addBefore("inboundHandler","WebSocketAggregator",new WebSocketFrameAggregator(65535));
        //用于处理websocket, /wst为访问websocket时的uri
        ctx.pipeline().addBefore("inboundHandler","ProtocolHandler", new WebSocketServerProtocolHandler("/wst"));
    }

}
