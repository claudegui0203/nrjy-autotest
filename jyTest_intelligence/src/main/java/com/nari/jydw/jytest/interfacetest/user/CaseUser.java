package com.nari.jydw.jytest.interfacetest.user;

import com.nari.jydw.jytest.common.InterfaceEnum;
import com.nari.jydw.jytest.common.TestParametersUtil;
import com.nari.jydw.jytest.interfaceTest.utils.JsonUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class CaseUser extends UserBusiness {
    @Test
    public void searchUser() {
        Response response = (Response) RestAssured.
        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .param("pageNum", 1).param("pageSize", 25).param("search", "5YzMiCE0VlEKKmc").
        when()
                .get(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.QUERYUSER.getApi()).
        then()
                .log().body()
                .statusCode(200)
                .body("code", is(200))
                .body("msg", is("用户列表查询成功")).extract();

        Assert.assertEquals((Integer) response.jsonPath().get("data.total"), response.jsonPath().getList("data.items").size());
        verifySearchUserResponseBody(response, this.getRegister(), response.jsonPath().get("data.total"), this.getUserId());
    }

    @Test
    public void checkUserName() {
        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .param("username", "5YzMiCE0VlEKKmc").
        when()
                .get(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKUSERNAME.getApi()).
        then()
                .log().body()
                .statusCode(200)
                .body("code", is(200)).body("msg", is("校验用户名是否可用成功")).body("data", is(false));
    }

    @Test
    public void checkUserNameWhenUpdate() {
        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .param("username", "5YzMiCE0VlEKKmc").param("userId", this.getUserId()).
        when()
                .get(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATECHECKUSERNAME.getApi()).
        then()
                .log().body()
                .statusCode(200)
                .body("code", is(200)).body("msg", is("校验用户名是否可用成功")).body("data", is(false));
    }

    @Test
    public void updateUserInfo() {
        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(JsonUtil.getGson().toJson(this.getRegister())).
        when()
                .put(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEUSERINFO.getApi()).
        then()
                .log().body().statusCode(200)
                .body("code", is(200)).body("msg", is("校验用户名是否可用成功")).body("data", is(false));
    }

    @Test
    public void updatePassword() {
        String password = generateString(0);
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
                .log().body()
                .statusCode(200)
                .body("code", is(200)).body("msg", is("密码修改成功"));

        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("id", this.getUserId()).formParam("password", password).
                when()
        .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body()
                .statusCode(200)
                .body("code", is(200)).body("msg", is("密码正确"));

        this.getRegister().setPassword(password);
    }

    @Test
    public void checkPassword() {
        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("id", this.getUserId()).formParam("password", this.getRegister().getPassword()).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body()
                .statusCode(200)
                .body("code", is(200)).body("msg", is("密码正确"));
    }

    @Test
    public void resetPassword() {
        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .param("id", this.getUserId()).
        when()
                .get(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.RESETPASSWORD.getApi()).
        then()
                .log().body()
                .statusCode(200)
                .body("code", is(200)).body("msg", is("密码重置成功"));

        this.getRegister().setPassword("AHdl@2020");

        given().log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8").header("token", getToken())
                .formParam("id", this.getUserId()).formParam("password", this.getRegister().getPassword()).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKPASSWD.getApi()).
        then()
                .log().body()
                .statusCode(200)
                .body("code", is(200)).body("msg", is("密码正确"));
    }
}
