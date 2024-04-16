package com.nari.jydw.jytest.interfaceTest.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.io.File;

public class LogUtil {
    public static Logger Logger = org.apache.log4j.Logger.getLogger(LogUtil.class.getName());

    public LogUtil() {
        File file = new File("src/main/resources/log4.properties");
        if (! file.exists()) {
            file =  new File("log4.properties");
        }

        PropertyConfigurator.configure(file.getPath());
    }

    public static void startTestCase(String sTestCaseName) {
        Logger.info("Begin test case " + sTestCaseName + " running...");
    }

    public static void endTestCase(String sTestCaseName) {
        Logger.info("End test case " + sTestCaseName + " running...");
    }

    public static void info (String message) {
        Logger.info(message);
    }

    public static void warn(String message) {
        Logger.warn(message);
    }

    public static void error (String message) {
        Logger.error(message);
    }

    public static void fatal (String message) {
        Logger.fatal(message);
    }

    public static void debug (String message) {
        Logger.debug(message);
    }
}
