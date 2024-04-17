package com.nari.jydw.jytest.interfacetest.user;

import com.nari.jydw.jytest.common.InterfaceEnum;
import com.nari.jydw.jytest.common.TestParametersUtil;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class CaseCheckUserPassword extends UserBusiness {

    @Test
    public void UserNotExist() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("id", 65535).formParam("password", this.getRegister().getPassword()).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("用户信息查询为空"));
    }

    @Test
    public void UserNotExistAndPasswordIncorrect() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("id", 65535).formParam("password", this.randomGenerateSpecialCharacters(0)).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("用户信息查询为空"));
    }

    @Test
    public void passwordIncorrect() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("id", this.getUserId()).formParam("password", this.randomGenerateSpecialCharacters(0)).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("密码错误"));
    }

    @Test
    public void userIdZero() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("id", 0).formParam("password", this.getRegister().getPassword()).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("用户信息查询为空"));
    }

    @Test
    public void userIdLessThanZero() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("id", -1).formParam("password", this.getRegister().getPassword()).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("用户信息查询为空"));
    }

    @Test
    public void passwordEmpty() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("id", this.getUserId()).formParam("password", "").
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("传入参数为空"));
    }

    @Test
    public void passwordNull() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("id", this.getUserId()).formParam("password", "null").
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("密码错误"));
    }

    @Test
    public void passwordSizeBiggerThan128() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("id", this.getUserId()).formParam("password", this.randomGenerateSpecialCharacters(128)).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("密码错误"));
    }

    @Test
    public void noIdParameter() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("password", this.getRegister().getPassword()).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body().statusCode(400)
                .body("status", is(400)).body("error", is("Bad Request"));
    }

    @Test
    public void noPasswordParameter() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("id", this.getUserId()).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body().statusCode(400)
                .body("status", is(400)).body("error", is("Bad Request"));
    }

    @Test
    public void noAnyParameter() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken()).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body().statusCode(400)
                .body("status", is(400)).body("error", is("Bad Request"));
    }

    @Test
    public void idFieldIncorrect() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("userId", this.getUserId()).formParam("password", this.getRegister().getPassword()).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body().statusCode(400)
                .body("status", is(400)).body("error", is("Bad Request"));
    }

    @Test
    public void passwordFieldIncorrect() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("id", this.getUserId()).formParam("pwd", this.getRegister().getPassword()).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body().statusCode(400)
                .body("status", is(400)).body("error", is("Bad Request"));
    }

    @Test
    public void allFieldIncorrect() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("userId", this.getUserId()).formParam("pwd", this.getRegister().getPassword()).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body().statusCode(400)
                .body("status", is(400)).body("error", is("Bad Request"));
    }
}
