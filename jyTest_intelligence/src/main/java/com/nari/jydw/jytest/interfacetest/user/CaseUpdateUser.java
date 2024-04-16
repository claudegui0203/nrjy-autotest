package com.nari.jydw.jytest.interfacetest.user;

import com.nari.jydw.jytest.CommonTestCases;
import com.nari.jydw.jytest.common.HttpStatusEnum;
import com.nari.jydw.jytest.common.InterfaceEnum;
import com.nari.jydw.jytest.common.TestParametersUtil;
import com.nari.jydw.jytest.common.business.body.Update;
import com.nari.jydw.jytest.common.business.response.ResponseBody;
import com.nari.jydw.jytest.interfaceTest.actions.ActionBuilder;
import com.nari.jydw.jytest.interfaceTest.actions.ActionHttpEnum;
import com.nari.jydw.jytest.interfaceTest.actions.ActionParameterBuilderMap4Http;
import com.nari.jydw.jytest.interfaceTest.actions.ActionSendHttpRequest;
import com.nari.jydw.jytest.interfaceTest.utils.JsonUtil;
import org.testng.annotations.Test;

public class CaseUpdateUser extends CommonTestCases {
    @Test(priority=1)
    public void updateUser() {
        String testUrl = TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.UPDATEUSER.getApi();

        Update update = new Update();
        update.setId(0L);

        ActionSendHttpRequest actionSendHttpRequest = ActionBuilder.createActionParameterBuilder(ActionParameterBuilderMap4Http.ACTION_SEND_HTTP_REQUEST)
                .paramRequestUrl(testUrl).paramHttpProtocol(ActionHttpEnum.POST).paramRequestHeader(generateHeaders()).paramRequestBody(update)
                .expectedStatusCode(HttpStatusEnum.OK).expectedResult("code", 200).expectedBodyObject(new ResponseBody())
                .buildAction();
        actionSendHttpRequest.perform();

        String httpResponseBody = (String) actionSendHttpRequest.getResult("responseBody");
        ResponseBody responseBody = JsonUtil.getGson().fromJson(httpResponseBody, ResponseBody.class);

    }
}
