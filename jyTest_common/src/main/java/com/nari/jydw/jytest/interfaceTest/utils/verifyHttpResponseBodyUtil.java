package com.nari.jydw.jytest.interfaceTest.utils;

import org.testng.Assert;
import java.lang.reflect.Field;

public class verifyHttpResponseBodyUtil {
    private static verifyHttpResponseBodyUtil instance;

    public static verifyHttpResponseBodyUtil getInstance() {
        if (instance == null) {
            instance = new verifyHttpResponseBodyUtil();
        }
        return instance;
    }

    private verifyHttpResponseBodyUtil() {

    }

    public verifyHttpResponseBodyUtil verifyField(Object responseBody, String result, String fieldName, Object expect) throws NoSuchFieldException, IllegalAccessException {
        Object response = JsonUtil.getGson().fromJson(result, responseBody.getClass());

        if (expect instanceof String) {
            String expectedValue = (String) expect;
            Field privateField = response.getClass().getDeclaredField(fieldName);
            privateField.setAccessible(true);
            String value = (String) privateField.get(response);
            Assert.assertEquals(value, expectedValue, "Value is differ with expect! value = " + value + ", but expect = " + expect);
        } else if (expect instanceof Integer) {
            int expectedValue = (int) expect;
            Field privateField = response.getClass().getDeclaredField(fieldName);
            privateField.setAccessible(true);
            int value = (int) privateField.get(response);
            Assert.assertEquals(value, expectedValue, "Value is differ with expect! value = " + value + ", but expect = " + expect);
        }

        return this;
    }
}
