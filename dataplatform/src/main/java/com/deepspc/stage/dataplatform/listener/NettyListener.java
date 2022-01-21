package com.deepspc.stage.dataplatform.listener;

import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.dataplatform.netty.NettyServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 项目启动后通过线程启动netty服务
 * @author gzw
 * @date 2022/1/19 13:49
 */
@WebListener
public class NettyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        NettyServer nettyServer = ApplicationContextUtil.getBean(NettyServer.class);
        new Thread(() -> {
            try {
                nettyServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
