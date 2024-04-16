package com.nari.jydw.jytest.interfaceTest.utils;

import com.nari.jydw.jytest.common.TestParametersUtil;
import com.nari.jydw.jytest.interfaceTest.helps.MqttHelp;
import lombok.Getter;
import org.eclipse.paho.client.mqttv3.*;

public class MqttUtil {
    private static MqttUtil instance;
    @Getter
    private MqttHelp mqttHelp;
    @Getter
    private MqttClient mqttClient;

    private MqttUtil() {
        String clientId = "sender-" + System.currentTimeMillis();
        mqttHelp = new MqttHelp();

        try {
            mqttClient = new MqttClient(TestParametersUtil.getInstance().getTestParameters().getMqttHostUrl(), clientId);
            mqttClient.setCallback(mqttHelp);
            mqttClient.connect(getMqttConnectOptions());
        } catch (MqttException e) {
            LogUtil.error("MqttUtil::Create connection fail. Reason = " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setServerURIs(new String[] { TestParametersUtil.getInstance().getTestParameters().getMqttHostUrl() });
        mqttConnectOptions.setUserName(TestParametersUtil.getInstance().getTestParameters().getMqttUsername());
        mqttConnectOptions.setPassword(TestParametersUtil.getInstance().getTestParameters().getMqttPassword().toCharArray());
        mqttConnectOptions.setConnectionTimeout(60);
        mqttConnectOptions.setKeepAliveInterval(60);
        mqttConnectOptions.setCleanSession(true);
        return mqttConnectOptions;
    }

    private void subscribe(Integer qos, String topic) {
        try {
            mqttClient.subscribe(topic, qos);
        }catch (MqttException e){
            LogUtil.error("subscribe::Subscribe message fail. Reason = " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static MqttUtil getInstance() {
        if (instance == null) {
            instance = new MqttUtil();
        }
        return instance;
    }

    public void sendMessage(Integer qos, String topic, String message) {
        subscribe(qos, topic);
        MqttMessage mqttMsg = new MqttMessage(message.getBytes());
        mqttMsg.setQos(qos);
        mqttMsg.setRetained(false);

        try {
            mqttClient.publish(topic, mqttMsg);
        } catch (MqttException e) {
            LogUtil.error("sendMessage::Send MQTT message fail. Reason = " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
