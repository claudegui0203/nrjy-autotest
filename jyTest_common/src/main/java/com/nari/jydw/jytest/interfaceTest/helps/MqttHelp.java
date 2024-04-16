package com.nari.jydw.jytest.interfaceTest.helps;

import com.nari.jydw.jytest.interfaceTest.utils.LogUtil;
import lombok.Getter;
import org.eclipse.paho.client.mqttv3.*;

public class MqttHelp implements MqttCallback {
    private MqttClient mqttClient;

    @Getter
    private MqttMessage mqttMessage;
    @Getter
    private IMqttDeliveryToken iMqttDeliveryToken;

    @Override
    public void connectionLost(Throwable throwable) {
        if (mqttClient == null || ! mqttClient.isConnected()) {
            LogUtil.info("connectionLost::Connection lost. Now is connecting...");
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        this.mqttMessage = mqttMessage;
        LogUtil.info("messageArrived::Receive message. Content: " + new String(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        this.iMqttDeliveryToken = iMqttDeliveryToken;
        try {
            LogUtil.info("deliveryComplete::Receive response message. status = " + iMqttDeliveryToken.isComplete() + " and content = " + iMqttDeliveryToken.getMessage());
        } catch (MqttException e) {
            LogUtil.error("deliveryComplete::Delivery complete. And get response fail. Reason = " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}