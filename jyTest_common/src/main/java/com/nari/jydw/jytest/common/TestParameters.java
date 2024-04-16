package com.nari.jydw.jytest.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestParameters {
    private String taskId = "";
    private SiteEntry siteEntry;
    private DBInfo dbInfo;
    private Mqtt mqtt;

    public String getSiteUrl() {
        return "http://" + siteEntry.getHostname() + ":" + siteEntry.getPort();
    }

    public String getSiteUsername() {
        return siteEntry.getAccount();
    }

    public String getSitePassword() {
        return siteEntry.getPassword();
    }

    public String getDBUrl() {
        return dbInfo.getIp() + ":" + dbInfo.getPort();
    }

    public String getDBUser() {
        return dbInfo.getUsername();
    }

    public String getDBPassword() {
        return dbInfo.getPassword();
    }

    public String getMqttHostUrl() {
        return "tcp://" + mqtt.getIp() + ":" + mqtt.getPort();
    }

    public String getMqttUsername() {
        return mqtt.getUsername();
    }

    public String getMqttPassword() {
        return mqtt.getPassword();
    }

//    public String[] getMqttTopic() {
//        return mqtt.getTopic();
//    }
}

@Setter
@Getter
class SiteEntry {
    public SiteEntry(String hostname, String port, String account, String password) {
        hostname = hostname;
        port = port;
        account = account;
        password = password;
    }

    private String hostname = "";
    private String port = "";
    private String account = "";
    private String password = "";
}

@Setter
@Getter
class DBInfo {
    private String ip = "";
    private String port = "";
    private String username = "";
    private String password = "";

    public DBInfo(String ip, String port, String username, String password) {
        ip = ip;
        port = port;
        username = username;
        password = password;
    }
}

@Setter
@Getter
class Mqtt {
    private String ip = "";
    private String port = "";
    private String username = "";
    private String password = "";
//    private String[] topic;

    public Mqtt(String ip, String port, String username, String password) {
        ip = ip;
        port = port;
        username = username;
        password = password;
//        topic = topic;
    }
}