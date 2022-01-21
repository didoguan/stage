package com.deepspc.stage.dataplatform.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Netty服务端，用于与硬件交互
 * @author gzw
 * @date 2022/1/18 16:58
 */
@Slf4j
@Component
public class NettyServer {

    @Value("${netty.port}")
    private int port;

    private final NettyInboundHandler nettyInboundHandler;

    @Autowired
    public NettyServer(NettyInboundHandler nettyInboundHandler) {
        this.nettyInboundHandler = nettyInboundHandler;
    }

    public void start() throws Exception {
        //主线程
        EventLoopGroup boss = new NioEventLoopGroup();
        //工作线程
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //允许重复使用本地地址和端口
                .childOption(ChannelOption.SO_REUSEADDR, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        //心跳检测，每分钟检测一次是否有中断连接
                        //sc.pipeline().addLast("idleStateHandler", new IdleStateHandler(60, 0, 0));
                        sc.pipeline().addLast("switchProtocolHandle", new SwitchProtocolHandle());
                        sc.pipeline().addLast("inboundHandler", nettyInboundHandler);
                    }
                });
        try {
            ChannelFuture cf = bootstrap.bind(port).sync();
            log.info("==========开启Netty服务=========");
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw e;
        } finally {
            //释放资源
            worker.shutdownGracefully().sync();
            boss.shutdownGracefully().sync();
        }
    }
}
