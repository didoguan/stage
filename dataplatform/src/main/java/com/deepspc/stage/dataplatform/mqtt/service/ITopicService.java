package com.deepspc.stage.dataplatform.mqtt.service;

import com.deepspc.stage.dataplatform.mqtt.model.SubscribeTopic;
import io.netty.handler.codec.mqtt.MqttTopicSubscription;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 订阅主题服务类
 * @author gzw
 * @date 2022/3/30 15:40
 */
public interface ITopicService {

    ConcurrentHashMap<String, Object> topicMap = new ConcurrentHashMap<>();

    /**
     * 客户端订阅主题
     * @param clientId 客户端标识(设备编码)
     * @param topicSubscriptions 要订阅的主题
     * @return 服务质量等级Qos
     */
    List<Integer> subscribeTopic(String clientId, List<MqttTopicSubscription> topicSubscriptions);

    boolean checkValidTopic(String topicName);

    List<SubscribeTopic> searchSubscribeTopic(String topic);
}
