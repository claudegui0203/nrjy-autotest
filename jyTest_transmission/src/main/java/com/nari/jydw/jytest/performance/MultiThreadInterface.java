package com.nari.jydw.jytest.performance;

import com.nari.jydw.jytest.common.HttpResponse;
import com.nari.jydw.jytest.interfaceTest.utils.HttpUtil;
import com.nari.jydw.jytest.interfaceTest.utils.LogUtil;
import org.apache.commons.httpclient.Header;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MultiThreadInterface {
    private int threadsCount = 2;
    private String token = "WebToken:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpbnRpbWUiOiIyMDI0LTAzLTE5IDE1OjMxOjA5IiwiZXhwIjoxNzEwOTE5ODY5LCJ1c2VySWQiOiIxIn0.NJFp2hhTruYN5C3YGjesMn9yen4nUcUbXYDGcairpD8";

    @Test
    public void uploadOneThousandPicturesOneByOne() throws InterruptedException {
        String url = "http://10.139.12.239:18091/panoSdgk/disaster/new/getAlarm";
        String body = "{\n" +
                "    \"startTime\": \"2024-03-12\",\n" +
                "    \"endTime\": \"2024-03-19\",\n" +
                "    \"dealStatus\": null,\n" +
                "    \"unit\": null,\n" +
                "    \"lineId\": null,\n" +
                "    \"wpType\": null,\n" +
                "    \"wpy\": null,\n" +
                "    \"dataSource\": null,\n" +
                "    \"pageNo\": 10,\n" +
                "    \"pageSize\": 50\n" +
                "}";

        Header[] headers = new Header[] { new Header("Content-Type", "application/json; charset=utf-8"), new Header("Token", token) };
        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        List<Future<Void>> futures = new ArrayList<>();

        LogUtil.info("Begin to test ...");
        for (int i = 0; i < threadsCount; i++) {
            int finalI = i;
            Callable<Void> task = () -> {
                HttpResponse response = HttpUtil.post(url, body, headers);
                LogUtil.info("Thread ID = " + finalI + ", response code = " + response.getStatusCode() + ", body = " + response.getResponseBody());

                return null;
            };
            Future<Void> future = executorService.submit(task);
            futures.add(future);
        }

        TimeUnit.SECONDS.sleep(20);
        while (true) {
            boolean allThreadsComplete = true;
            for (Future<Void> future : futures) {
                if (! future.isDone()) {
                    allThreadsComplete = false;
                    break;
                }
            }

            if (allThreadsComplete) {
                LogUtil.info("All tread are end. Exit waiting.");
                break;
            }

            TimeUnit.SECONDS.sleep(10);
        }

        executorService.shutdown();
    }

    @Test
    public void uploadOneThousandPicturesOneBy() throws InterruptedException {
        String url = "http://10.139.12.239:18091/panoSdgk/disaster/new/getWpztAlarm";
        String body = "{\n" +
                "    \"startTime\": \"2024-03-12\",\n" +
                "    \"endTime\": \"2024-03-19\",\n" +
                "    \"dealStatus\": null,\n" +
                "    \"unit\": null,\n" +
                "    \"lineId\": null,\n" +
                "    \"wpType\": null,\n" +
                "    \"wpy\": null,\n" +
                "    \"dataSource\": null,\n" +
                "    \"pageNo\": 10,\n" +
                "    \"pageSize\": 50\n" +
                "}";

        Header[] headers = new Header[] { new Header("Content-Type", "application/json; charset=utf-8"), new Header("Token", token) };

        HttpResponse response = HttpUtil.post(url, body, headers);
        LogUtil.info("STATUS code = " + response.getStatusCode() + " body = " + response.getResponseBody());

//        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
//        List<Future<Void>> futures = new ArrayList<>();
//
//        LogUtil.info("Begin to test ...");
//        for (int i = 0; i < threadsCount; i++) {
//            int finalI = i;
//            Callable<Void> task = () -> {
//                HttpResponse response = HttpUtil.post(url, body, headers);
//                LogUtil.info("Thread ID = " + finalI + ", response code = " + response.getStatusCode() + ", body = " + response.getResponseBody());
//
//                return null;
//            };
//            Future<Void> future = executorService.submit(task);
//            futures.add(future);
//        }
//
//        TimeUnit.SECONDS.sleep(20);
//        while (true) {
//            boolean allThreadsComplete = true;
//            for (Future<Void> future : futures) {
//                if (! future.isDone()) {
//                    allThreadsComplete = false;
//                    break;
//                }
//            }
//
//            if (allThreadsComplete) {
//                LogUtil.info("All tread are end. Exit waiting.");
//                break;
//            }
//
//            TimeUnit.SECONDS.sleep(10);
//        }
//
//        executorService.shutdown();
    }
}
