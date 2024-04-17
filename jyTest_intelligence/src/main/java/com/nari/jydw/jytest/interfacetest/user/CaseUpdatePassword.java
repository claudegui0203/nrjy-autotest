package com.nari.jydw.jytest.interfacetest.user;

import com.nari.jydw.jytest.common.InterfaceEnum;
import com.nari.jydw.jytest.common.TestParametersUtil;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class CaseUpdatePassword extends UserBusiness {

    @Test
    public void updatePassword() {
        String password = randomGenerateSpecialCharacters(128);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", this.getUserId());
        userInfo.put("password", this.getRegister().getPassword());
        userInfo.put("newPassword", password);

        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(userInfo).
        when()
                .put(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEPASSWORD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(200)).body("msg", is("密码修改成功"));

        this.getRegister().setPassword(password);
        verifyUserPassword();
    }

    @Test(priority = 3)
    public void newPasswordEmpty() {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", this.getUserId());
        userInfo.put("password", this.getRegister().getPassword());
        userInfo.put("newPassword", "");

        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(userInfo).
        when()
                .put(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEPASSWORD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("缺少必要参数"));

        verifyUserPassword();
    }

    @Test
    public void newPasswordNull() {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", this.getUserId());
        userInfo.put("password", this.getRegister().getPassword());
        userInfo.put("newPassword", null);

        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(userInfo).
        when()
                .put(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEPASSWORD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("缺少必要参数"));

        verifyUserPassword();
    }

    @Test
    public void userNotExist() {
        String password = randomGenerateSpecialCharacters(0);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", 65535);
        userInfo.put("password", this.getRegister().getPassword());
        userInfo.put("newPassword", password);

        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(userInfo).
        when()
                .put(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEPASSWORD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("当前用户不存在"));

        verifyUserPassword();
    }

    @Test
    public void userNotExistAndPasswordIncorrect() {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", 65535);
        userInfo.put("password", randomGenerateSpecialCharacters(0));
        userInfo.put("newPassword", randomGenerateSpecialCharacters(128));

        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(userInfo).
        when()
                .put(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEPASSWORD.getApi()).
        then()
                .log().body()
                .statusCode(200)
                .body("code", is(500)).body("msg", is("当前用户不存在"));

        verifyUserPassword();
    }

    @Test
    public void passwordIncorrect() {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", this.getUserId());
        userInfo.put("password", randomGenerateSpecialCharacters(0));
        userInfo.put("newPassword", randomGenerateSpecialCharacters(0));

        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(userInfo).
        when()
                .put(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEPASSWORD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("密码错误"));

        verifyUserPassword();
    }

    @Test
    public void noIdParameter() {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("password", this.getRegister().getPassword());
        userInfo.put("newPassword", randomGenerateSpecialCharacters(0));

        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(userInfo).
        when()
                .put(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEPASSWORD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("缺少必要参数"));

        verifyUserPassword();
    }

    @Test
    public void noPasswordParameter() {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", this.getUserId());
        userInfo.put("newPassword", this.randomGenerateSpecialCharacters(0));

        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(userInfo).
        when()
                .put(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEPASSWORD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("缺少必要参数"));

        verifyUserPassword();
    }

    @Test
    public void noNewPasswordParameterWithCorrectPassword() {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", this.getUserId());
        userInfo.put("password", this.randomGenerateSpecialCharacters(0));

        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(userInfo).
        when()
                .put(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEPASSWORD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("缺少必要参数"));

        verifyUserPassword();
    }

    @Test
    public void noNewPasswordParameter() {
        String password = randomGenerateSpecialCharacters(0);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", this.getUserId());
        userInfo.put("password", this.getRegister().getPassword());

        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(userInfo).
        when()
                .put(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEPASSWORD.getApi()).
        then()
                .log().body()
                .statusCode(200)
                .body("code", is(500)).body("msg", is("缺少必要参数"));

        verifyUserPassword();
    }

    @Test
    public void noToken() {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", this.getUserId());
        userInfo.put("password", this.getRegister().getPassword());
        userInfo.put("newPassword", this.randomGenerateSpecialCharacters(0));

        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8")
                .body(userInfo).
        when()
                .put(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEPASSWORD.getApi()).
        then()
                .log().body().statusCode(401)
                .body("error", is("Unauthorized")).body("message", is("当前token为空"));

        verifyUserPassword();
    }

    @Test
    public void idParameterFieldIncorrect() {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", this.getUserId());
        userInfo.put("password", this.getRegister().getPassword());
        userInfo.put("newPassword", this.randomGenerateSpecialCharacters(0));

        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(userInfo).
        when()
                .put(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEPASSWORD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("缺少必要参数"));

        verifyUserPassword();
    }

    @Test
    public void passwordParameterFieldIncorrect() {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", this.getUserId());
        userInfo.put("passwd", this.getRegister().getPassword());
        userInfo.put("newPassword", this.randomGenerateSpecialCharacters(0));

        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(userInfo).
        when()
                .put(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEPASSWORD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("缺少必要参数"));

        verifyUserPassword();
    }

    @Test
    public void newPasswordParameterFieldIncorrect() {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", this.getUserId());
        userInfo.put("password", this.getRegister().getPassword());
        userInfo.put("newPasswd", this.randomGenerateSpecialCharacters(0));

        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(userInfo).
        when()
                .put(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEPASSWORD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(500)).body("msg", is("缺少必要参数"));

        verifyUserPassword();
    }

    private void verifyUserPassword() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("id", this.getUserId()).formParam("password", this.getRegister().getPassword()).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(200)).body("msg", is("密码正确"));
    }
}
