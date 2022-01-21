package com.deepspc.stage.dataplatform.websocket;

import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket服务类
 * @author gzw
 * @date 2022/1/21 15:08
 */
@Slf4j
@ServerEndpoint("/websocket/{frame}")
@Component
public class WebsocketServer {

    /**
     * 存放每个连接对象
     */
    private static ConcurrentHashMap<String, Session> connected = new ConcurrentHashMap<>();

    /**
     * 建立连接后调用
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "frame") String frame) {
        ShiroUser user = ShiroKit.getShiroUser();
        WebsocketServer.connected.put(user.getAccount() + "_" + frame, session);
        log.info("==========建立websocket连接========="+frame);
    }

    /**
     * 断开连接后调用
     */
    @OnClose
    public void onClose(Session session) {
        ShiroUser user = ShiroKit.getShiroUser();
        WebsocketServer.connected.remove(user.getAccount());
        log.info("==========断开websocket连接=========");
    }

    /**
     * 接收客户端信息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("websocket接收客户端信息：{}", message);
    }

    /**
     * 出错时调用
     */
    @OnError
    public void onError(Session session, Throwable throwable){
        throwable.printStackTrace();
    }

    /**
     * 发送实现序列化的数据
     * @param data 要发送的数据
     */
    public void sendMessage(Serializable data) throws IOException, EncodeException {
        ShiroUser user = ShiroKit.getShiroUser();
        Session session = WebsocketServer.connected.get(user.getAccount());
        if (null != session) {
            session.getBasicRemote().sendObject(data);
        }
    }

    /**
     * 发送字符串数据
     * @param msg 要发送的信息
     */
    public void sendMessage(String msg) throws IOException {
        ShiroUser user = ShiroKit.getShiroUser();
        Session session = WebsocketServer.connected.get(user.getAccount());
        if (null != session) {
            session.getBasicRemote().sendText(msg);
        }
    }
}
