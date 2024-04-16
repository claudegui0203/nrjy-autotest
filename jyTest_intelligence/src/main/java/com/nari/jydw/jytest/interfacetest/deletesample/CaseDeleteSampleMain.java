package com.nari.jydw.jytest.interfacetest.deletesample;

import com.nari.jydw.jytest.CommonTestCases;
import com.nari.jydw.jytest.common.HttpStatusEnum;
import com.nari.jydw.jytest.common.InterfaceEnum;
import com.nari.jydw.jytest.common.business.body.DeleteSampleMain;
import com.nari.jydw.jytest.interfaceTest.actions.ActionBuilder;
import com.nari.jydw.jytest.interfaceTest.actions.ActionHttpEnum;
import com.nari.jydw.jytest.interfaceTest.actions.ActionParameterBuilderMap4Http;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CaseDeleteSampleMain extends CommonTestCases {

    @Test
    public void deleteOneSample() {
        List<String> sampleIdList = new ArrayList<>();
        sampleIdList.add("08929ff3334e1000");

        DeleteSampleMain deleteSampleMain = new DeleteSampleMain();
        deleteSampleMain.setSysType(65535);
        deleteSampleMain.setSampleIdList(sampleIdList);

        ActionBuilder.createActionParameterBuilder(ActionParameterBuilderMap4Http.ACTION_SEND_HTTP_REQUEST)
                .paramRequestUrl(InterfaceEnum.DELETESAMPLEMAIN.getApi()).paramHttpProtocol(ActionHttpEnum.POST).paramRequestHeader(generateHeaders()).paramRequestBody(deleteSampleMain)
                .expectedStatusCode(HttpStatusEnum.OK)
                .buildAction()
                .perform();
    }

    @Test
    public void deleteMultiSamples() {
        List<String> sampleIdList = new ArrayList<>();
        sampleIdList.add("0892c8bfe20e1000");
        sampleIdList.add("08919359af0e1000");
        sampleIdList.add("08918f2307ce1000");

        DeleteSampleMain deleteSampleMain = new DeleteSampleMain();
        deleteSampleMain.setSysType(1);
        deleteSampleMain.setSampleIdList(sampleIdList);

        ActionBuilder.createActionParameterBuilder(ActionParameterBuilderMap4Http.ACTION_SEND_HTTP_REQUEST)
                .paramRequestUrl(InterfaceEnum.DELETESAMPLEMAIN.getApi()).paramHttpProtocol(ActionHttpEnum.POST).paramRequestHeader(generateHeaders()).paramRequestBody(deleteSampleMain)
                .expectedStatusCode(HttpStatusEnum.OK)
                .buildAction()
                .perform();
    }

    @Test
    public void deleteOneCorrectSample() {
        List<String> sampleIdList = new ArrayList<>();
        sampleIdList.add("08918f230cce1000");
        sampleIdList.add("08918f2309991000");

        DeleteSampleMain deleteSampleMain = new DeleteSampleMain();
        deleteSampleMain.setSysType(1);
        deleteSampleMain.setSampleIdList(sampleIdList);

        ActionBuilder.createActionParameterBuilder(ActionParameterBuilderMap4Http.ACTION_SEND_HTTP_REQUEST)
                .paramRequestUrl(InterfaceEnum.DELETESAMPLEMAIN.getApi()).paramHttpProtocol(ActionHttpEnum.POST).paramRequestHeader(generateHeaders()).paramRequestBody(deleteSampleMain)
                .expectedStatusCode(HttpStatusEnum.OK)
                .buildAction()
                .perform();
    }

    @Test
    public void deleteTwoSameSample() {
        List<String> sampleIdList = new ArrayList<>();
        sampleIdList.add("08918f23168e1000");
        sampleIdList.add("08918f23168e1000");

        DeleteSampleMain deleteSampleMain = new DeleteSampleMain();
        deleteSampleMain.setSysType(1);
        deleteSampleMain.setSampleIdList(sampleIdList);

        ActionBuilder.createActionParameterBuilder(ActionParameterBuilderMap4Http.ACTION_SEND_HTTP_REQUEST)
                .paramRequestUrl(InterfaceEnum.DELETESAMPLEMAIN.getApi()).paramHttpProtocol(ActionHttpEnum.POST).paramRequestHeader(generateHeaders()).paramRequestBody(deleteSampleMain)
                .expectedStatusCode(HttpStatusEnum.OK)
                .buildAction()
                .perform();
    }

    @Test
    public void sysTypeNotExist() {
        List<String> sampleIdList = new ArrayList<>();
        sampleIdList.add("088d81b96a8e1000");

        DeleteSampleMain deleteSampleMain = new DeleteSampleMain();
        deleteSampleMain.setSysType(3);
        deleteSampleMain.setSampleIdList(sampleIdList);

        ActionBuilder.createActionParameterBuilder(ActionParameterBuilderMap4Http.ACTION_SEND_HTTP_REQUEST)
                .paramRequestUrl(InterfaceEnum.DELETESAMPLEMAIN.getApi()).paramHttpProtocol(ActionHttpEnum.POST).paramRequestHeader(generateHeaders()).paramRequestBody(deleteSampleMain)
                .expectedStatusCode(HttpStatusEnum.OK)
                .buildAction()
                .perform();
    }

    @Test
    public void sampleIdLong() {
        List<String> sampleIdList = new ArrayList<>();
        sampleIdList.add("905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a");
        sampleIdList.add("905c99999204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9ab");
        sampleIdList.add("905c99999204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9ac");
        sampleIdList.add("905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b91");
        sampleIdList.add("905c99999204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a2");
        sampleIdList.add("905c99999204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a3");
        sampleIdList.add("905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b94");
        sampleIdList.add("905c99999204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a5");
        sampleIdList.add("905c99999204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a6");
        sampleIdList.add("905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b97");
        sampleIdList.add("905c99999204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a8");
        sampleIdList.add("905c99999204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a9");
        sampleIdList.add("905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b10");
        sampleIdList.add("905c99999204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b911");
        sampleIdList.add("905c99999204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b912");
        sampleIdList.add("905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b13");
        sampleIdList.add("905c99999204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b914");
        sampleIdList.add("905c99999204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b915");

        DeleteSampleMain deleteSampleMain = new DeleteSampleMain();
        deleteSampleMain.setSysType(1);
        deleteSampleMain.setSampleIdList(sampleIdList);

        ActionBuilder.createActionParameterBuilder(ActionParameterBuilderMap4Http.ACTION_SEND_HTTP_REQUEST)
                .paramRequestUrl(InterfaceEnum.DELETESAMPLEMAIN.getApi()).paramHttpProtocol(ActionHttpEnum.POST).paramRequestHeader(generateHeaders()).paramRequestBody(deleteSampleMain)
                .expectedStatusCode(HttpStatusEnum.OK)
                .buildAction()
                .perform();
    }

    @Test
    public void sysTypeAndSampleIdNotExist() {
        List<String> sampleIdList = new ArrayList<>();
        sampleIdList.add("088d81b977ce1000");

        DeleteSampleMain deleteSampleMain = new DeleteSampleMain();
        deleteSampleMain.setSysType(65535);
        deleteSampleMain.setSampleIdList(sampleIdList);

        ActionBuilder.createActionParameterBuilder(ActionParameterBuilderMap4Http.ACTION_SEND_HTTP_REQUEST)
                .paramRequestUrl(InterfaceEnum.DELETESAMPLEMAIN.getApi()).paramHttpProtocol(ActionHttpEnum.POST).paramRequestHeader(generateHeaders()).paramRequestBody(deleteSampleMain)
                .expectedStatusCode(HttpStatusEnum.OK)
                .buildAction()
                .perform();
    }

    @Test
    public void sampleIdEmpty() {
        List<String> sampleIdList = new ArrayList<>();
        sampleIdList.add("");

        DeleteSampleMain deleteSampleMain = new DeleteSampleMain();
        deleteSampleMain.setSysType(1);
        deleteSampleMain.setSampleIdList(sampleIdList);

        ActionBuilder.createActionParameterBuilder(ActionParameterBuilderMap4Http.ACTION_SEND_HTTP_REQUEST)
                .paramRequestUrl(InterfaceEnum.DELETESAMPLEMAIN.getApi()).paramHttpProtocol(ActionHttpEnum.POST).paramRequestHeader(generateHeaders()).paramRequestBody(deleteSampleMain)
                .expectedStatusCode(HttpStatusEnum.OK)
                .buildAction()
                .perform();
    }

    @Test
    public void sampleIdListNull() {
        DeleteSampleMain deleteSampleMain = new DeleteSampleMain();
        deleteSampleMain.setSysType(1);
        deleteSampleMain.setSampleIdList(null);

        ActionBuilder.createActionParameterBuilder(ActionParameterBuilderMap4Http.ACTION_SEND_HTTP_REQUEST)
                .paramRequestUrl(InterfaceEnum.DELETESAMPLEMAIN.getApi()).paramHttpProtocol(ActionHttpEnum.POST).paramRequestHeader(generateHeaders()).paramRequestBody(deleteSampleMain)
                .expectedStatusCode(HttpStatusEnum.OK)
                .buildAction()
                .perform();
    }
}
