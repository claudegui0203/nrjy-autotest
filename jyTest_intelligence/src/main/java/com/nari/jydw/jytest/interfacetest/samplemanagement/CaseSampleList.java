package com.nari.jydw.jytest.interfacetest.samplemanagement;

import com.nari.jydw.jytest.CommonTestCases;
import com.nari.jydw.jytest.common.HttpStatusEnum;
import com.nari.jydw.jytest.common.InterfaceEnum;
import com.nari.jydw.jytest.common.business.body.SampleList;
import com.nari.jydw.jytest.interfaceTest.actions.ActionBuilder;
import com.nari.jydw.jytest.interfaceTest.actions.ActionHttpEnum;
import com.nari.jydw.jytest.interfaceTest.actions.ActionParameterBuilderMap4Http;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;

public class CaseSampleList extends CommonTestCases {

    @Test
    public void SmpleList() {
        List<Integer> labelIds = new ArrayList<>();
        labelIds.add(1);
        labelIds.add(2);

        SampleList samplelistObject = new SampleList();
        samplelistObject.setLabelIds(labelIds);

        ActionBuilder.createActionParameterBuilder(ActionParameterBuilderMap4Http.ACTION_SEND_HTTP_REQUEST)
                .paramRequestUrl(InterfaceEnum.SAMPLELIST.getApi()).paramHttpProtocol(ActionHttpEnum.POST).paramRequestHeader(generateHeaders()).paramRequestBody(samplelistObject)
                .expectedStatusCode(HttpStatusEnum.OK).expectedResult("code", 200)
                .buildAction()
                .perform();
    }
}
