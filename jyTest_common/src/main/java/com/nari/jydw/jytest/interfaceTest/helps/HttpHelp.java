package com.nari.jydw.jytest.interfaceTest.helps;

import org.apache.commons.httpclient.Header;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.swing.text.AbstractDocument;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.LinkedList;
import java.util.UUID;

public class HttpHelp {
    public static CloseableHttpResponse doPostFormData(String url, File file, Header[] headers) throws IOException {
        String boundary = UUID.randomUUID().toString();
        // 创建Http实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建HttpPost实例
        HttpPost httpPost = new HttpPost(url);
        // 请求参数配置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000)
                .setConnectionRequestTimeout(10000).build();
        httpPost.setConfig(requestConfig);

//        if (headers != null) {
//            for (Header header : headers) {
//                httpPost.setHeader(header.getName(), header.getValue());
//            }
//        }

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(StandardCharsets.UTF_8);
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        httpPost.setHeader("token", "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6OCwidXNlcm5hbWUiOiJzYWRtaW4iLCJleHAiOjE3MDY4ODUwNDJ9.QCe8lbXqcI7rhtan4jneEO51DIhajNYyF871US4pMuhFcuTq0Mn8K5t4wNbWLJBgF0phv73LklwgKFy1uzk62wDxieqQFcQD89QdR2i4RDpiRCY3zyCfSDTHyCPTSYLxBtT6Zg29TKtvgFBL_YLe7JwUSmOWop15PW7sWUutIEg");
        httpPost.setHeader("Content-Type", "multipart/form-data; boundary=--------------------------" + boundary);
//        builder.setBoundary("-------------------------" + boundary);
        builder.addTextBody("picPath", "upload_pic/220kV/2023/12/11/20231211_143912_/profile_jydw_upload/capturepicpath/2023-12-11/00133f11fd324292bb576bd2c51c0963.jpg", ContentType.DEFAULT_TEXT);

//      builder.addPart("file", new FileBody(file));
        builder.addBinaryBody("file", file, ContentType.APPLICATION_OCTET_STREAM, file.getName());

        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);
        return httpClient.execute(httpPost);
    }

    public static CloseableHttpResponse postFormData(String url, File file, Header[] headers) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建HttpPost实例
        HttpPost httpPost = new HttpPost(url);
        // 请求参数配置
//        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000)
//                .setConnectionRequestTimeout(10000).build();
//        httpPost.setConfig(requestConfig);

//        if (headers != null) {
//            for (Header header : headers) {
//                httpPost.setHeader(header.getName(), header.getValue());
//            }
//        }

        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
        FileInputStream fileInputStream = new FileInputStream(file);
        ContentType contentType = ContentType.create("multipart/form-data", Consts.UTF_8);
        builder.addBinaryBody("file", fileInputStream, ContentType.MULTIPART_FORM_DATA, file.getName());
        ContentType contentType1 = ContentType.APPLICATION_JSON;
        LinkedList<FormBodyPart> partLinkedList = new LinkedList<>();
        builder.addPart("picPath", new StringBody("upload_pic/220kV/2023/12/11/20231211_143912_/profile_jydw_upload/capturepicpath/2023-12-11/example.jpg"));
//        builder.addPart("file", new StringBody("D:\\example.JPG"));
        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);

        return httpClient.execute(httpPost);
    }
}
