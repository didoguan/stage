package com.deepspc.stage.dataplatform.mqtt.service.impl;

import cn.hutool.core.util.StrUtil;
import com.deepspc.stage.dataplatform.mqtt.entity.TopicSubscribe;
import com.deepspc.stage.dataplatform.mqtt.mapper.TopicSubscribeMapper;
import com.deepspc.stage.dataplatform.mqtt.model.SubscribeTopic;
import com.deepspc.stage.dataplatform.mqtt.service.ITopicService;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.codec.mqtt.MqttTopicSubscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订阅主题服务实现类
 * @author gzw
 * @date 2022/3/30 15:50
 */
@Service
@Slf4j
public class TopicServiceImpl implements ITopicService {
    @Resource
    private TopicSubscribeMapper topicSubscribeMapper;

    @Override
    public List<Integer> subscribeTopic(String clientId, List<MqttTopicSubscription> topicSubscriptions) {
        List<Integer> mqttQoSList = new ArrayList<>();
        List<TopicSubscribe> subs = new ArrayList<>();
        Date current = new Date();
        //检查主题是否合法，保存客户端要订阅的主题
        for (MqttTopicSubscription topicSubscription : topicSubscriptions) {
            String topicName = topicSubscription.topicName();
            MqttQoS mqttQoS = topicSubscription.option().qos();
            TopicSubscribe sub = new TopicSubscribe();
            sub.setClientCode(clientId);
            sub.setTopicId(-1L);
            sub.setQosLevel(mqttQoS.value());
            sub.setSubscribeDate(current);
            subs.add(sub);
            mqttQoSList.add(mqttQoS.value());
        }
        if (!subs.isEmpty()) {
            for (TopicSubscribe sub : subs) {
                topicSubscribeMapper.insert(sub);
            }
        }
        return mqttQoSList;
    }

    @Override
    public boolean checkValidTopic(String topicName) {
        String oneLevel = "#";
        String allLevel = "*";
        return StrUtil.isNotBlank(topicName) && !topicName.contains(oneLevel) && !topicName.contains(allLevel);
    }

    @Override
    public List<SubscribeTopic> searchSubscribeTopic(String topic) {
        return null;
    }
}
