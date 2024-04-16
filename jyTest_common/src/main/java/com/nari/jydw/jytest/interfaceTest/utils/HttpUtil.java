package com.nari.jydw.jytest.interfaceTest.utils;

import com.nari.jydw.jytest.common.HttpResponse;
import com.nari.jydw.jytest.interfaceTest.actions.ActionHttpEnum;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Map;

public class HttpUtil {
    private static boolean isEasySsl = false;
    private static int timeout = 30000;// will move to constant class

    private static int getTimeout() {
        return HttpUtil.timeout;
    }

    public static void setTimeout(int timeout){
        HttpUtil.timeout = timeout;
    }

    public static void setHttpRequestTime(int ms) {
        HttpUtil.timeout = ms;
    }

    /**
     *
     * Add the POST method
     * @param url
     * @param postBody
     * @param headers
     *
     */
    public static HttpResponse post (String url, String postBody, Header[] headers) {
        HttpResponse httpResponse = null;

        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestBody(postBody);
        if (headers != null) {
            for (Header header:headers) {
                postMethod.setRequestHeader(header);
            }
        }

        HttpClientParams clientParams = new HttpClientParams();
        clientParams.setSoTimeout(getTimeout());
        httpClient.setParams(clientParams);

        try {
            int responseCode = httpClient.executeMethod(postMethod);
            String responseBody = getResponseBodyAsString(postMethod);
            if (responseCode < 200 || responseCode > 299) {
                LogUtil.error("Http call failed. url is " + url + ", body = " + postBody + ", response status is " + responseCode + " responseBody " + responseBody);
            }
            httpResponse = new HttpResponse(responseCode, responseBody);
        } catch (IOException e) {
            LogUtil.error("Http call failed. url is " + url + ", post body is " + postBody + ", error is " + e.getMessage());
        }

        return httpResponse;
    }

    /**
     *
     * Add the DELETE method
     * @param url
     * @param deleteBody
     * @param headers
     *
     */
    public static HttpResponse sendHttpRequest (ActionHttpEnum actionType, String url, String deleteBody, Header[] headers) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(actionType.toString());

        connection.setDoOutput(true);
        if (headers != null) {
            for (Header header : headers) {
                connection.setRequestProperty(header.getName(), header.getValue());
            }
        }

        OutputStream outputStream = connection.getOutputStream();
        byte[] inputBytes = deleteBody.getBytes(StandardCharsets.UTF_8);
        outputStream.write(inputBytes, 0, inputBytes.length);

        int responseCode = connection.getResponseCode();
        BufferedReader reader;

        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader((connection.getErrorStream())));
        }

        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        connection.disconnect();
        reader.close();
        return new HttpResponse(responseCode, stringBuilder.toString());
    }

    public static CloseableHttpResponse postUploadFile(String url, File file, Map<String, String> parameters, Header[] headers) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

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

        if ((parameters != null) && (! parameters.isEmpty())) {
            parameters.forEach((key, values) -> {
                try {
                    builder.addPart(key, new StringBody(values));
                } catch (UnsupportedEncodingException e) {
                    LogUtil.error("postUploadFile::Upload file fail. Reason = " + e.getMessage());
                    throw new RuntimeException(e);
                }
            });
        }

        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);

        return httpClient.execute(httpPost);
    }

        /**
     *
     * Add the GET method
     * @param url
     * @param headers
     *
     */
    public static HttpResponse get (String url, String getBody, Header[] headers) {
        HttpResponse httpResponse = null;

        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        if (headers != null) {
            for (Header header:headers) {
                getMethod.setRequestHeader(header);
            }
        }

        HttpClientParams clientParams = new HttpClientParams();
        clientParams.setSoTimeout(getTimeout());
        httpClient.setParams(clientParams);

        try {
            int responseCode = httpClient.executeMethod(getMethod);
            String responseBody = getResponseBodyAsString(getMethod);

            if (responseCode < 200 || responseCode > 299) {
                LogUtil.error("Http call failed. url is " + url + ", response status is " + responseCode + " responseBody " + responseBody);
            }
            httpResponse = new HttpResponse(responseCode, responseBody);
        } catch (IOException e) {
            LogUtil.error("Http call failed. url is " + url + ", error is " + e.getMessage());
        }

        return httpResponse;
    }

    /**
     *
     * Add the PUT method
     * @param url
     * @param putBody
     * @param headers
     *
     */
    public static HttpResponse put (String url, String putBody, Header[] headers) {
        HttpResponse httpResponse = null;

        HttpClient httpClient = new HttpClient();
        PutMethod putMethod = new PutMethod(url);

        if (headers != null) {
            for (Header header:headers) {
                putMethod.setRequestHeader(header);
            }
        }

        HttpClientParams clientParams = new HttpClientParams();
        clientParams.setSoTimeout(getTimeout());
        httpClient.setParams(clientParams);

        try {
            putMethod.setRequestEntity(new StringRequestEntity(putBody,"application/json", null));
            int responseCode = httpClient.executeMethod(putMethod);
            String responseBody = getResponseBodyAsString(putMethod);

            if (responseCode < 200 || responseCode > 299) {
                LogUtil.error("Http call failed. url is " + url + ", response status is " + responseCode + " responseBody " + responseBody);
            }
            httpResponse = new HttpResponse(responseCode, responseBody);
        } catch (IOException e) {
            LogUtil.error("Http call failed. url is " + url + ", put body is " + putBody + ", error is " + e.getMessage());
        }

        return httpResponse;
    }

    /**
     *
     * @param httpMethod
     * @return
     * @throws IOException
     */
    private static String getResponseBodyAsString(HttpMethodBase httpMethod) throws IOException {
        String content = "";
        BufferedReader reader = null;
        try {
            if (httpMethod.getResponseContentLength() != -1) {
                content = httpMethod.getResponseBodyAsString();
            } else { // unknown size
                reader = new BufferedReader(new InputStreamReader(
                        httpMethod.getResponseBodyAsStream(), httpMethod.getResponseCharSet()));
                StringBuffer strBuffer = new StringBuffer();

                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                content = stringBuilder.toString();
                reader.close();
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return content;
    }

    private void getResponseBody() {

    }

    private static InputStream readFile(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        while (true) {
            int b = inputStream.read();
            if (b == -1) {
                break;
            }
        }

        return inputStream;
    }
}

