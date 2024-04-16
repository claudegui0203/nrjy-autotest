package com.nari.jydw.jytest.interfacetest.deletesample;

import com.nari.jydw.jytest.CommonTestCases;
import com.nari.jydw.jytest.common.HttpResponse;
import com.nari.jydw.jytest.common.TestParametersUtil;
import com.nari.jydw.jytest.common.InterfaceEnum;
import com.nari.jydw.jytest.common.business.body.DeleteUpload;
import com.nari.jydw.jytest.interfaceTest.utils.HttpUtil;
import com.nari.jydw.jytest.interfaceTest.utils.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CaseDeleteUpload extends CommonTestCases {
    private String testUrl = TestParametersUtil.getInstance().getTestParameters().getSiteUrl() + InterfaceEnum.DELETEUPLOAD.getApi();

    @Test
    public void deleteOneUpload() {
        DeleteUpload deleteUpload = new DeleteUpload();
        deleteUpload.setSysType(1);
        deleteUpload.setUploadId("905c9e76eb204a8b85e4495bb2231b9a");

        System.out.println("url = " + testUrl + ", body = " + JsonUtil.getGson().toJson(deleteUpload));
        HttpResponse httpResponse = HttpUtil.get(testUrl, JsonUtil.getGson().toJson(deleteUpload), generateHeaders());
        System.out.println("code = " + httpResponse.getStatusCode() + ", body = " + httpResponse.getResponseBody());
        Assert.assertEquals(httpResponse.getStatusCode(), 200);
    }

    @Test
    public void uploadIdEmpty() {
        DeleteUpload deleteUpload = new DeleteUpload();
        deleteUpload.setSysType(1);
        deleteUpload.setUploadId("");

        System.out.println("url = " + testUrl + ", body = " + JsonUtil.getGson().toJson(deleteUpload));
        HttpResponse httpResponse = HttpUtil.post(testUrl, JsonUtil.getGson().toJson(deleteUpload), generateHeaders());
        System.out.println("code = " + httpResponse.getStatusCode() + ", body = " + httpResponse.getResponseBody());
        Assert.assertEquals(httpResponse.getStatusCode(), 200);
    }

    @Test
    public void uploadIdLong() {
        DeleteUpload deleteUpload = new DeleteUpload();
        deleteUpload.setSysType(1);
        deleteUpload.setUploadId("905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a905c9e76eb204a8b85e4495bb2231b9a");

        System.out.println("url = " + testUrl + ", body = " + JsonUtil.getGson().toJson(deleteUpload));
        HttpResponse httpResponse = HttpUtil.post(testUrl, JsonUtil.getGson().toJson(deleteUpload), generateHeaders());
        System.out.println("code = " + httpResponse.getStatusCode() + ", body = " + httpResponse.getResponseBody());
        Assert.assertEquals(httpResponse.getStatusCode(), 200);
    }

    @Test
    public void uploadIdNull() {
        DeleteUpload deleteUpload = new DeleteUpload();
        deleteUpload.setSysType(1);
        deleteUpload.setUploadId(null);

        System.out.println("url = " + testUrl + ", body = " + JsonUtil.getGson().toJson(deleteUpload));
        HttpResponse httpResponse = HttpUtil.post(testUrl, JsonUtil.getGson().toJson(deleteUpload), generateHeaders());
        System.out.println("code = " + httpResponse.getStatusCode() + ", body = " + httpResponse.getResponseBody());
        Assert.assertEquals(httpResponse.getStatusCode(), 200);
    }

    @Test
    public void sysTypeNotExist() {
        DeleteUpload deleteUpload = new DeleteUpload();
        deleteUpload.setSysType(3);
        deleteUpload.setUploadId("905c9e76eb204a8b85e4495bb2231b9a");

        System.out.println("url = " + testUrl + ", body = " + JsonUtil.getGson().toJson(deleteUpload));
        HttpResponse httpResponse = HttpUtil.post(testUrl, JsonUtil.getGson().toJson(deleteUpload), generateHeaders());
        System.out.println("code = " + httpResponse.getStatusCode() + ", body = " + httpResponse.getResponseBody());
        Assert.assertEquals(httpResponse.getStatusCode(), 200);
    }

    @Test
    public void sysTypeNotInLimit() {
        DeleteUpload deleteUpload = new DeleteUpload();
        deleteUpload.setSysType(-1);
        deleteUpload.setUploadId("905c9e76eb204a8b85e4495bb2231b9a");

        System.out.println("url = " + testUrl + ", body = " + JsonUtil.getGson().toJson(deleteUpload));
        HttpResponse httpResponse = HttpUtil.post(testUrl, JsonUtil.getGson().toJson(deleteUpload), generateHeaders());
        System.out.println("code = " + httpResponse.getStatusCode() + ", body = " + httpResponse.getResponseBody());
        Assert.assertEquals(httpResponse.getStatusCode(), 200);
    }

    @Test
    public void sampleIdNull() {
        DeleteUpload deleteUpload = new DeleteUpload();
        deleteUpload.setSysType(null);
        deleteUpload.setUploadId("905c9e76eb204a8b85e4495bb2231b9a");

        System.out.println("url = " + testUrl + ", body = " + JsonUtil.getGson().toJson(deleteUpload));
        HttpResponse httpResponse = HttpUtil.post(testUrl, JsonUtil.getGson().toJson(deleteUpload), generateHeaders());
        System.out.println("code = " + httpResponse.getStatusCode() + ", body = " + httpResponse.getResponseBody());
        Assert.assertEquals(httpResponse.getStatusCode(), 200);
    }
}
