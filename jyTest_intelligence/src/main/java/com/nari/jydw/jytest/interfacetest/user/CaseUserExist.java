package com.nari.jydw.jytest.interfacetest.user;

import com.nari.jydw.jytest.common.InterfaceEnum;
import com.nari.jydw.jytest.common.TestParametersUtil;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class CaseUserExist extends UserBusiness {

    @Test
    public void UserNotExist() {
        given()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .param("username", "test").
        when()
                .get(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKUSERNAME.getApi()).
        then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("msg", is("校验用户名是否可用成功"))
                .body("data", is(true));
    }

    @Test
    public void UserExist() {
//        given()
//                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
//                .param("username", "sadmin").
//        when()
//                .get(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKUSERNAME.getApi()).
//        then()
//                .log().all()
//                .statusCode(200)
//                .body("code", is(200))
//                .body("msg", is("校验用户名是否可用成功"))
//                .body("data", is(false));
        userIsExist("sadmin");
    }
}
