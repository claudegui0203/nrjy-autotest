package com.nari.jydw.jytest.apptest;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class appiumdemo {

    @Test
    public void appiumdemo() throws MalformedURLException, InterruptedException {
        AndroidDriver driver = getAndroidDriver();
        Thread.sleep(1000);
//        int i = 10;
//        int sum_install =0;
//        int sum_remove =0;
//        while(i>0)
//        {
//            if (driver.isAppInstalled("com.youdao.calculator"))
//            {
//                driver.removeApp("com.youdao.calculator");
//                sum_remove++;}
//            else {
//                driver.installApp("C:\\Users\\Sonia\\Desktop\\Youdaocalculator.apk");
//                sum_install++;
//            }
//            i=i-1;
//        }
//        System.out.printf("卸载%d次",sum_remove);
//        System.out.printf("安装%d次",sum_install);
//        driver.quit();
//        driver.executeDriverScript("mobile:deviceInfo");

        System.out.println("Time = " + driver.getDeviceTime());
    }

    private static AndroidDriver getAndroidDriver() throws MalformedURLException {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "127.0.0.1:5555");
    capabilities.setCapability("udid", "127.0.0.1:5555");
    capabilities.setCapability("automationName", "Appium");
    capabilities.setCapability("platformVersion", "14.0.0");
    capabilities.setCapability("newCommandTimeout", "10");
    capabilities.setCapability("nosign", "True");

    AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    return driver;
    }
}
