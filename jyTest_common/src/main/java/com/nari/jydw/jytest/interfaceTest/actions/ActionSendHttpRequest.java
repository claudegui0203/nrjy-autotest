package com.nari.jydw.jytest.interfaceTest.actions;

import com.nari.jydw.jytest.common.HttpResponse;
import com.nari.jydw.jytest.common.HttpStatusEnum;
import com.nari.jydw.jytest.common.TestParametersUtil;
import com.nari.jydw.jytest.interfaceTest.utils.HttpUtil;
import com.nari.jydw.jytest.interfaceTest.utils.JsonUtil;
import com.nari.jydw.jytest.interfaceTest.utils.LogUtil;
import com.nari.jydw.jytest.interfaceTest.utils.verifyHttpResponseBodyUtil;
import org.apache.commons.httpclient.Header;
import org.testng.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ActionSendHttpRequest extends AbstractTestAction {
    private ActionHttpEnum paramHttpProtocol = ActionHttpEnum.POST;
    private String paramRequestUrl = "";
    private Header[] paramRequestHeader;
    private Object paramRequestBody;
    private String paramBody = "";
    private HttpResponse httpResponse;
    private HttpStatusEnum expectedStatusCode;
    private Object expectedBodyObject;
    private Map<String, Object> expectedResult = new HashMap<>();

    @Override
    protected void prepareParameters() {
        if ((this.paramRequestUrl == null) || (this.paramRequestUrl.isEmpty())) {
            Assert.fail("ActionSendHttpRequest: paramRequestUrl cannot be null or empty!!!");
        }

        if (this.paramRequestBody != null) {
            paramBody = JsonUtil.getGson().toJson(this.paramRequestBody);
        }

        paramRequestUrl = TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + paramRequestUrl;
    }

    @Override
    protected void sendRequest() {
        LogUtil.info("ActionSendHttpRequest: Test url = " + this.paramRequestUrl + ". Header = " + paramRequestHeader.toString() + " and Request body = " + paramBody);
        try {
            httpResponse = HttpUtil.sendHttpRequest(this.paramHttpProtocol, paramRequestUrl, paramBody, paramRequestHeader);
        } catch (IOException e) {
            LogUtil.error("sendRequest: Send delete request fail. Reason = " + e.getMessage());
        }

        actionResult.put("statusCode", httpResponse.getStatusCode());
        actionResult.put("responseBody", httpResponse.getResponseBody());
        LogUtil.info("ActionSendHttpRequest: Test response status code = " + httpResponse.getStatusCode() + " and response body = " + httpResponse.getResponseBody());
    }

    @Override
    protected void verify() {
        Assert.assertEquals(httpResponse.getStatusCode(), this.expectedStatusCode.getCode());

        expectedResult.forEach((key, value) -> {
            try {
                verifyHttpResponseBodyUtil.getInstance().verifyField(expectedBodyObject, httpResponse.getResponseBody(), key, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                Assert.fail("ActionSendHttpRequest::Verify http response fail. Reason = " + e.getMessage());
            }
        });
    }

    private void getApiActionType() {
        try {
            // 获取枚举的Class对象
            Class<?> enumClass = Class.forName("InterfaceEnum$api");

            // 使用反射获取所有枚举值
            Object[] enumConstants = enumClass.getEnumConstants();

            // 遍历并打印枚举值
            for (Object enumConst : enumConstants) {
                System.out.println(enumConst.toString());
            }
        } catch (ClassNotFoundException e) {
            LogUtil.error("getApiActionType: Cannot find the class. Reason = " + e.getMessage());
        }
    }
}
