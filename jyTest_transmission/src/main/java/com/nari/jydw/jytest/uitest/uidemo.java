package com.nari.jydw.jytest.uitest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class uidemo {
    protected Logger logger = LoggerFactory.getLogger("uidemo");

    @Test
    public void transmissionDemo() throws InterruptedException {
        String url = "http://10.139.200.97:18091/panoWeb/login.html";
        String userName = "ah_xtgly";
        String password = "Admin@8384";

        Path path = Paths.get("src","drivers", "chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", path.toAbsolutePath().toString());
        WebDriver driver = new ChromeDriver();
        driver.get(url);


        Assert.assertEquals(driver.getCurrentUrl(), url);
        Assert.assertEquals(driver.getTitle(), "login");

        driver.findElement(By.id("loginName")).sendKeys(userName);
        driver.findElement(By.id("loginPwd")).sendKeys(password);
        driver.findElement(By.id("loginBtn")).click();
        TimeUnit.SECONDS.sleep(2);

        logger.info("url = " + driver.getCurrentUrl());
        Assert.assertEquals(driver.getTitle(), "输电全景智慧平台系统");
        TimeUnit.SECONDS.sleep(3);

        driver.close();
    }

    @Test
    public void intelligenceDemo() throws InterruptedException {
        String url = "http://10.139.8.245:30088/#/home/allRevel";
        String userName = "sadmin";
        String password = "AHdl@2020";

        Path path = Paths.get("src","drivers", "chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", path.toAbsolutePath().toString());
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        TimeUnit.SECONDS.sleep(2);

        logger.info("title = " + driver.getTitle());
        logger.info("url = " + driver.getCurrentUrl());
//        Assert.assertEquals(driver.getCurrentUrl(), url);
        Assert.assertEquals(driver.getTitle(), "人工智能服务中心");

//        driver.findElement(By.id("loginName")).sendKeys(userName);
//        driver.findElement(By.id("loginPwd")).sendKeys(password);
        driver.findElement(By.className("cms_login_ipt")).sendKeys(userName);
        driver.findElement(By.className("cms_login_ipt")).sendKeys(password);
        driver.findElement(By.className("login_btn")).click();
        TimeUnit.SECONDS.sleep(5);

        logger.info("title = " + driver.getTitle());
        logger.info("url = " + driver.getCurrentUrl());
//        Assert.assertEquals(driver.getTitle(), "输电全景智慧平台系统");
        TimeUnit.SECONDS.sleep(3);
        logger.info("page resource = " + driver.getPageSource());

        driver.close();
    }

    @AfterTest
    public void cleanUp() {

    }
}
