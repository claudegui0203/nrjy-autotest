package com.nari.jydw.jytest.common;

import com.google.gson.Gson;
import com.nari.jydw.jytest.interfaceTest.helps.FileHelp;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestParametersUtil {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private static TestParametersUtil instance;
    @Getter
    private TestParameters testParameters = null;

    private TestParametersUtil() {
    }

    public static TestParametersUtil getInstance() {
        if (instance == null) {
            instance = new TestParametersUtil();
        }
        return instance;
    }

    public void parseConfiguration(String localConfigFile) throws IOException {
        logger.info("Initial test parameter, localConfigFile: " + localConfigFile);
        String configFile = localConfigFile;
        if ((localConfigFile == null) || (localConfigFile.isEmpty())) {
            logger.info("parseConfiguration::Initial test parameter, Cannot get anything from local configuration! Use default configuration.");
            configFile = "src/main/resources/TAConfiguration.conf";
        }

        parseConfigByLocalFile(configFile);
    }

    private void parseConfigByLocalFile(String configFile) throws IOException {
        String content = FileHelp.readFileAsString(configFile);
        Gson gson = new Gson();
        testParameters = gson.fromJson(content, TestParameters.class);
    }

    private String getTaJobParamsValue(String taJobParameters, String paramKey) {
        String value = "";
        taJobParameters = taJobParameters.replace("{", "").replace("}", "");

        if (! taJobParameters.contains(paramKey)) {
            return value;
        }

        for (String item : taJobParameters.split(",")) {
            if (! item.trim().startsWith(paramKey + "="))
                continue;
            String[] kv = item.trim().split("=");
            if (kv.length == 2) {
                value = kv[1];
                break;
            }
        }

        return value;
    }
}
