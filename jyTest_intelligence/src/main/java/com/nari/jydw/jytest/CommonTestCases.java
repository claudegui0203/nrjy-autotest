package com.nari.jydw.jytest;

import com.nari.jydw.jytest.common.HttpResponse;
import com.nari.jydw.jytest.common.MainTestCases;
import com.nari.jydw.jytest.common.TestParametersUtil;
import com.nari.jydw.jytest.common.InterfaceEnum;
import com.nari.jydw.jytest.common.business.response.LoginResponse;
import com.nari.jydw.jytest.interfaceTest.actions.ActionHttpEnum;
import com.nari.jydw.jytest.interfaceTest.utils.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.httpclient.Header;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;

public class CommonTestCases extends MainTestCases {
    private String token = "";

    @Parameters({ "LOCAL_CONFIG_FILE" })
    @BeforeSuite
    public void beforeSuite(String localConfigFile) {
        try {
            TestParametersUtil.getInstance().parseConfiguration(localConfigFile);
        } catch (IOException e) {
            LogUtil.error("beforeSuite::Analysis configuration file failed. message = " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @BeforeTest
    public void getTokenByLogin() {
        for (int i = 0; i < 3; i++) {
            Response response = RestAssured.
                    given().log().all()
                        .header("Content-Type", "application/json; charset=utf-8")
                        .queryParam("username", TestParametersUtil.getInstance().getTestParameters().getSiteUsername())
                        .queryParam("password", TestParametersUtil.getInstance().getTestParameters().getSitePassword())
                    .when()
                        .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.LOGIN.getApi())
                    .then()
                        .statusCode(200)
                        .body("code", is(200))
                        .body("msg", is("登录成功"))
                        .extract().response();

            token = response.jsonPath().get("data.token");
            LogUtil.info("getToken: Token = " + token);

            if (! this.token.isEmpty()) {
                return;
            }
        }
    }

    protected Header[] generateHeaders() {
        if (this.token.isEmpty()) {
            Assert.fail("CommonTestCases::getToken: Token length is 0!!");
        }

        Header header1 = new Header("Content-Type", "application/json; charset=utf-8");
        Header header2 = new Header("Token", this.token);

        return new Header[] { header1, header2 };
    }

    @AfterTest
    public void teardown() {
        try {
            if (DBUtil.getInstance().getConnection() != null) {
                DBUtil.getInstance().getConnection().close();
            }

            if (DBUtil.getInstance().getStatement() != null) {
                DBUtil.getInstance().getStatement().close();
            }

            if (MqttUtil.getInstance().getMqttClient() != null) {
                MqttUtil.getInstance().getMqttClient().disconnect();
                MqttUtil.getInstance().getMqttClient().close();
            }
        } catch (SQLException | MqttException e) {
            LogUtil.error("teardown::Release DB connection fail. message = " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getToken() {
        return this.token;
    }
}
