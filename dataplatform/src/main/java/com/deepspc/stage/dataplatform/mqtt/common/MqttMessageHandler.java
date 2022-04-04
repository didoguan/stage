package com.deepspc.stage.dataplatform.mqtt.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.*;

import java.util.List;

/**
 * 消息处理
 * QoS 0：消息最多传递一次，如果当时客户端不可用，则会丢失该消息。
 * QoS 1：消息传递至少 1 次。
 * QoS 2：消息仅传送一次。
 * @author gzw
 * @date 2022/3/29 13:53
 */
public class MqttMessageHandler {

    /**
     * 取消订阅报文确认信息处理(服务端到客户端)
     * @param messageId 信息标识
     */
    public static MqttUnsubAckMessage unSubAckMessage(int messageId) {
        return (MqttUnsubAckMessage) MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.UNSUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                MqttMessageIdVariableHeader.from(messageId), null);
    }

    /**
     * 订阅请求报文确认信息(服务端到客户端)
     * @param messageId 信息标识
     * @param mqttQoSList 服务质量列表
     */
    public static MqttSubAckMessage subAckMessage(int messageId, List<Integer> mqttQoSList) {
        return (MqttSubAckMessage) MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.SUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                MqttMessageIdVariableHeader.from(messageId), new MqttSubAckPayload(mqttQoSList));
    }

    /**
     * QoS 2消息发布完成（保证交互第三步）
     * @param messageId 信息标识
     */
    public static MqttMessage pubCompMessage(int messageId) {
        return MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PUBCOMP, false, MqttQoS.AT_MOST_ONCE, false, 0),
                MqttMessageIdVariableHeader.from(messageId), null);
    }

    /**
     * QoS 1消息发布收到确认。QoS1只有固定报头和可变报头，没有有效载荷
     * @param messageId 信息标识
     */
    public static MqttPubAckMessage pubAckMessage(int messageId) {
        return (MqttPubAckMessage) MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                MqttMessageIdVariableHeader.from(messageId), null);
    }

    /**
     * 发布收到（保证交付第一步）
     * @param messageId 信息标识
     */
    public static MqttMessage pubRecMessage(int messageId) {
        return MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PUBREC, false, MqttQoS.AT_MOST_ONCE, false, 0),
                MqttMessageIdVariableHeader.from(messageId), null);
    }

    /**
     * 发布释放（保证交付第二步）
     * @param messageId 信息标识
     * @param isDup 控制报文的重复分发标志(是否全新会话)
     */
    public static MqttMessage pubRelMessage(int messageId, boolean isDup) {
        return MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PUBREL, isDup, MqttQoS.AT_LEAST_ONCE, false, 0),
                MqttMessageIdVariableHeader.from(messageId), null);
    }

    /**
     * 心跳响应
     */
    public static MqttMessage pingRespMessage() {
        return MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PINGRESP, false, MqttQoS.AT_MOST_ONCE, false, 0), null, null);
    }

    /**
     * 连接报文确认
     * @param code 连接响应编码
     * @param sessionPresent 当前会话标志。
     *                       sessionPresent=true则已存在会话，此时无需再重复订阅topic（订阅关系已保存到session中，若再重复订阅则收不到之前的离线消息）。
     *                       sessionPresent=false则不存在session（又或者session已超期），此时需要重新订阅topic，且之前离线的消息都已接收不到，
     *                       只能通过其他方式获取离线消息（例如IM后端服务的全量同步服务）。
     */
    public static MqttConnAckMessage connAckMessage(MqttConnectReturnCode code, boolean sessionPresent) {
        return (MqttConnAckMessage) MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                new MqttConnAckVariableHeader(code, sessionPresent), null);
    }

    /**
     * 连接出现异常时返回呼应码
     * @param cause 异常
     * @return 0x00-连接已被服务端接受
     *         0x01-服务端不支持客户端请求的MQTT协议级别、版本
     *         0x02-客户端标识符是正确的UTF-8编码，但服务端不允许使用
     *         0x03-网络连接已建立，但MQTT服务不可用
     *         0x04-用户名或密码的数据格式无效
     *         0x05-客户端未被授权连接到此服务器
     */
    public static MqttConnectReturnCode connectReturnCodeForException(Throwable cause) {
        MqttConnectReturnCode code;
        if (cause instanceof MqttUnacceptableProtocolVersionException) {
            // 不支持的协议版本
            code = MqttConnectReturnCode.CONNECTION_REFUSED_UNACCEPTABLE_PROTOCOL_VERSION;
        } else if (cause instanceof MqttIdentifierRejectedException) {
            // 不合格的clientId
            code = MqttConnectReturnCode.CONNECTION_REFUSED_IDENTIFIER_REJECTED;
        } else {
            code = MqttConnectReturnCode.CONNECTION_REFUSED_SERVER_UNAVAILABLE;
        }
        return code;
    }

    /**
     * 发布消息
     * @param topic 主题
     * @param isDup 控制报文的重复分发标志,用来在保证消息传输可靠的，若是设置为1，
     *              则在下面的变长头部里多加MessageId,并须要回复确认，保证消息传输完成，但不能用于检测消息重复发送。
     * @param qosValue 服务质量等级
     * @param isRetain PUBLISH报文的保留标志,表示服务器要保留此次推送的信息，
     *                 若是有新的订阅者出现，就把这消息推送给它。若是不设那么推送至当前订阅的就释放了
     * @param messageId 信息标识
     * @param payload 有效载荷
     */
    public static MqttPublishMessage publishMessage(String topic, boolean isDup, int qosValue, boolean isRetain,
                                                    int messageId, byte[] payload) {
        return (MqttPublishMessage) MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PUBLISH, isDup, MqttQoS.valueOf(qosValue), isRetain, 0),
                new MqttPublishVariableHeader(topic, messageId), Unpooled.buffer().writeBytes(payload));
    }

    /**
     * 发布消息
     * @param topic 主题
     * @param payload 消息载荷
     * @param qosValue 服务质量等级
     * @param messageId 信息标识
     * @param isRetain PUBLISH报文的保留标志,表示服务器要保留此次推送的信息，
     *                 若是有新的订阅者出现，就把这消息推送给它。若是不设那么推送至当前订阅的就释放了
     */
    public static MqttPublishMessage publishMessage(String topic, byte[] payload, int qosValue, int messageId,
                                                    boolean isRetain) {
        return publishMessage(topic, false, qosValue, isRetain, messageId, payload);
    }

    /**
     * 发布消息
     * @param topic 主题
     * @param payload 消息载荷
     * @param qosValue 服务质量等级
     * @param isRetain PUBLISH报文的保留标志,表示服务器要保留此次推送的信息，
     *                 若是有新的订阅者出现，就把这消息推送给它。若是不设那么推送至当前订阅的就释放了
     * @param messageId 信息标识
     * @param isDup 控制报文的重复分发标志,用来在保证消息传输可靠的，若是设置为1，
     *              则在下面的变长头部里多加MessageId,并须要回复确认，保证消息传输完成，但不能用于检测消息重复发送。
     */
    public static MqttPublishMessage publishMessage(String topic, byte[] payload, int qosValue, boolean isRetain,
                                                    int messageId, boolean isDup) {
        return publishMessage(topic, isDup, qosValue, isRetain, messageId, payload);
    }

    /**
     * 客户端订阅请求(客户端到服务端)
     * @param mqttTopicSubscriptions 要订阅的主题列表
     * @param messageId 信息标识
     */
    public static MqttSubscribeMessage subscribeMessage(List<MqttTopicSubscription> mqttTopicSubscriptions, int messageId) {
        MqttSubscribePayload mqttSubscribePayload = new MqttSubscribePayload(mqttTopicSubscriptions);
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.SUBSCRIBE, false, MqttQoS.AT_LEAST_ONCE, false, 0);
        MqttMessageIdVariableHeader mqttMessageIdVariableHeader = MqttMessageIdVariableHeader.from(messageId);
        return new MqttSubscribeMessage(mqttFixedHeader, mqttMessageIdVariableHeader, mqttSubscribePayload);
    }

    /**
     * 客户端取消订阅请求(客户端到服务端)
     * @param topic 要取消订阅的主题
     * @param messageId 信息标识
     */
    public static MqttUnsubscribeMessage unSubscribeMessage(List<String> topic, int messageId) {
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.UNSUBSCRIBE, false, MqttQoS.AT_MOST_ONCE, false, 0x02);
        MqttMessageIdVariableHeader variableHeader = MqttMessageIdVariableHeader.from(messageId);
        MqttUnsubscribePayload mqttUnsubscribeMessage = new MqttUnsubscribePayload(topic);
        return new MqttUnsubscribeMessage(mqttFixedHeader, variableHeader, mqttUnsubscribeMessage);
    }

    /**
     * 断开连接
     */
    public static MqttMessage disConnectMessage() {
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.DISCONNECT, false, MqttQoS.AT_MOST_ONCE,
                false, 0x02);
        return new MqttMessage(mqttFixedHeader);
    }

    public static byte[] copyByteBuf(ByteBuf byteBuf) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return bytes;
    }

}
