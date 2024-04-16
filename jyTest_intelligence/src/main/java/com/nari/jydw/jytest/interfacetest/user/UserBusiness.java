package com.nari.jydw.jytest.interfacetest.user;

import com.nari.jydw.jytest.CommonTestCases;
import com.nari.jydw.jytest.common.InterfaceEnum;
import com.nari.jydw.jytest.common.TestParametersUtil;
import com.nari.jydw.jytest.common.business.body.Register;
import com.nari.jydw.jytest.interfaceTest.utils.JsonUtil;
import com.nari.jydw.jytest.interfaceTest.utils.LogUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import lombok.Getter;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.util.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@Getter
public class UserBusiness extends CommonTestCases {
    private Register register = null;
    private Integer userId = 0;

    public String generateString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*,.";
        Random random = new Random();
        int randomNumber = 0;
        if (length == 0) {
            randomNumber = random.nextInt(129);
        } else {
            randomNumber = length;
        }

        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0; i < randomNumber; i++) {
            int number=random.nextInt(62);
            stringBuffer.append(str.charAt(number));
        }

        return stringBuffer.toString();
    }

    public String generatePhoneNumber() {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer phoneNumber = new StringBuffer();
        for (int i=0; i < random.nextInt(11); i++){
            int number=random.nextInt(10);
            phoneNumber.append(str.charAt(number));
        }

        return phoneNumber.toString();
    }

    public Register generateUserInfo(List<String> roleNames, List<Integer> roleIds) {
        if ((roleNames.isEmpty()) || (roleIds.isEmpty())) {
            roleNames.add("超级管理员");
            roleIds.add(5);
        }

        Register register = new Register();
        String username = generateUsername();
        register.setUsername(username);
        register.setPassword(generateString(12));
        register.setCompany(generateString(10));
        register.setRealName(generateString(20));
        register.setPhone(generatePhoneNumber());
        register.setRoleIds(roleIds);
        register.setRoleNames(roleNames);
        register.setIsDisplay("0");
        register.setStatus("1");

        return register;
    }

    public Boolean userIsExist(String username) {
        Response response = RestAssured
                .given()
                    .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                    .param("username", username)
                .when()
                    .get(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.CHECKUSERNAME.getApi()).
                then()
                    .log().all().
                    extract().response();

        LogUtil.info("Body = " + response.jsonPath().get("data"));
        return response.jsonPath().get("data");
    }

    private String generateUsername() {
        while (true) {
            String username = generateString(20);
            if (userIsExist(username)) {
                return username;
            }
        }
    }

    public void verifySearchUserResponseBody(ResponseBody responseBody, Register register, int total, Integer userId) {
        List<Map<String, Object>> items = responseBody.jsonPath().getList("data.items");
        for (int i = 0; i < total; i++) {
            Map<String, Object> item = items.get(i);
            if ((Objects.equals(item.get("id"), userId)) && (item.get("username").equals(register.getUsername()))) {
                Assert.assertEquals(item.get("realName"), register.getRealName());
                Assert.assertEquals(item.get("company"), register.getCompany());
                Assert.assertEquals(item.get("phone"), register.getPhone());
                Assert.assertEquals(item.get("sysType"), register.getIsDisplay());
                Assert.assertEquals(item.get("roleName"), getRoleName(register.getRoleNames()));
                Assert.assertEquals(item.get("roleId"), getUserId(register.getRoleIds()));
            }
        }
    }

    private String getRoleName(List<String> roleNames) {
        StringBuilder roleName = new StringBuilder();
        for (String role : roleNames) {
            roleName.append(role).append(",");
        }

        return roleName.substring(0, roleName.length() - 1);
    }

    private Integer getUserId(List<Integer> roleIds) {
        int userId = 65535;
        for (Integer id : roleIds) {
            if (userId < id) {
                userId = id;
            }
        }

        return userId;
    }

    @BeforeClass
    public void registerUser() {
        this.register = generateUserInfo(new ArrayList<>(), new ArrayList<>());

        Response response = RestAssured.
        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(JsonUtil.getGson().toJson(this.register)).
        when()
                .post(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.REGISTER.getApi()).
        then()
                .statusCode(200)
                .body("code", is(200))
                .body("msg", is("用户注册成功"))
                .log().all().extract().response();

        userId = response.jsonPath().get("data");
        LogUtil.info("user id = " + userId);
    }

    @AfterClass
    public void deleteUser() {
        Map<String, List<Long>> del = new HashMap<>();
        List<Long> ids = new ArrayList<>();
        ids.add(this.userId.longValue());
        del.put("ids", ids);

        given().log().all()
                .header("Content-Type", "application/json; charset=utf-8").header("token", getToken())
                .body(del).
        when()
                .delete(TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.DELETEUSER.getApi()).
        then()
                .log().all().statusCode(200)
                .body("code", is(200)).body("msg", is("删除用户成功"));
    }
}
