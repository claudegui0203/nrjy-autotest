package com.nari.jydw.jytest.performance;

import com.nari.jydw.jytest.common.TestParametersUtil;
import com.nari.jydw.jytest.interfaceTest.helps.MqttHelp;
import com.nari.jydw.jytest.interfaceTest.utils.HttpUtil;
import com.nari.jydw.jytest.interfaceTest.utils.LogUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class MultiThreadMinio {
    private Integer count = 20032;
    private Integer threadsCount = 64;
    private final File file = new File("performance/00133f11fd324292bb576bd2c51c0963.JPG");

    @Test
    public void uploadOneThousandPicturesOneByOne() throws IOException, InterruptedException, ExecutionException {
        String url = "http://10.139.102.60:18021/file/v2/upload_file?token=WebToken:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpbnRpbWUiOiIyMDI0LTAzLTI5IDA5OjE2OjIyIiwiZXhwIjoxNzExNzYxMzgyLCJ1c2VySWQiOiIxIn0._BtIWG-rc-9Cl5Azr88dgKzSEyhpyHrx8OWOZIQttGQ";
        List<HashSet<File>> pictures = generatePictures();
        checkPictures(pictures);

        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        List<Future<Void>> futures = new ArrayList<>();

        long startTime = System.nanoTime();
        LogUtil.info("Begin to performance testing. Now time is " + new Date());

        for (int i = 0; i < threadsCount; i++) {
            int finalI = i;
            Future<Void> future = executorService.submit(() -> {
                int pictureNo = 0;
                for (File picture : pictures.get(finalI)) {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("id", String.valueOf((finalI * (count/threadsCount) + pictureNo)));
                    parameters.put("module", "performance-0329");
                    parameters.put("fileName", picture.getName());
                    parameters.put("leftBottom", String.valueOf(System.nanoTime()));
                    parameters.put("scaled", String.valueOf(false));

                    CloseableHttpResponse response;
                    LogUtil.info("Thread ID = " + finalI + ", begin to upload picture. Picture name = " + picture.getName());
                    try {
                        response = HttpUtil.postUploadFile(url, picture, parameters, null);
                        LogUtil.info("status code = " + response.getStatusLine().getStatusCode() + ", body = " + EntityUtils.toString(response.getEntity(), "UTF-8"));
                        if (response.getStatusLine().getStatusCode() != 200) {
                            LogUtil.error("Upload picture fail. And response = " + response.getStatusLine().getStatusCode());
                        }
                    } catch (IOException e) {
                        LogUtil.error("Upload picture fail. And response = " + e.getMessage());
                    }

                    pictureNo++;
                }

                LogUtil.info("Thread " + finalI + " end testing.");

                return null;
            });
            futures.add(future);
        }

        TimeUnit.SECONDS.sleep(1200);
        int i = 0;
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

            TimeUnit.SECONDS.sleep(60);
        }

        long endTime = System.nanoTime();
        LogUtil.info("End performance testing. Now time is " + new Date() + ". Total use time = " + (endTime - startTime)/1000000000 + " seconds.");

        LogUtil.info("Task is end, shutdown all threads.");
        executorService.shutdown();
    }

    private List<HashSet<File>> generatePictures() throws InterruptedException {
        List<HashSet<File>> picturesCollection = new ArrayList<>();
        int picturesCount = count / threadsCount;

        for (int thread = 0; thread < threadsCount; thread++) {
            HashSet<File> picturesSubCollection = new HashSet<>();
            for (int i = 0; i < picturesCount; i++) {
                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(file);
                    File picture = generatePictureName();
                    OutputStream outputStream = new FileOutputStream(picture);

                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while((len = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }

                    picturesSubCollection.add(picture);
                } catch (IOException ee) {
                    LogUtil.error("generatePictures:: Generate picture fail. reason = " + ee.getMessage());
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException ignored) {
                        }
                    }
                }
            }
            TimeUnit.SECONDS.sleep(10);
            picturesCollection.add(picturesSubCollection);
        }

        return picturesCollection;
    }

    private File generatePictureName() {
        Random rand = new Random();

        while (true) {
            String pictureName = "1" + String.valueOf(rand.nextInt(8)) + String.valueOf(rand.nextInt(1000000000)) + String.valueOf(rand.nextInt(100000000));
            File picture = new File("performance/" + pictureName + ".JPG");

            if (! picture.exists()) {
                return picture;
            }
        }
    }

    private void checkPictures(List<HashSet<File>> pictures) {
        int i = 0;
        for (HashSet<File> picturesSubCollection : pictures) {
            i = i + picturesSubCollection.size();
        }

        Assert.assertEquals(i, count, "checkPictures:: i = " + i + " is not equals " + count);
    }

    private MqttClient generateMqttClient(int threadId) {
        MqttClient mqttClient = null;
        String clientId = "sender-" + System.currentTimeMillis() + "-Thread_" + threadId;
        MqttHelp mqttHelp = new MqttHelp();

        try {
            mqttClient = new MqttClient(TestParametersUtil.getInstance().getTestParameters().getMqttHostUrl(), clientId);
            mqttClient.setCallback(mqttHelp);
            mqttClient.connect(getMqttConnectOptions());
        } catch (MqttException e) {
            LogUtil.error("MqttUtil::Create connection fail. Reason = " + e.getMessage());
            reconnectMqtt(mqttClient);
        }

        return mqttClient;
    }

    private void reconnectMqtt(MqttClient mqttClient) {
        try {
            if (mqttClient == null) {
                String clientId = "sender-" + System.currentTimeMillis();
                mqttClient = new MqttClient(TestParametersUtil.getInstance().getTestParameters().getMqttHostUrl(), clientId);
                MqttHelp mqttHelp = new MqttHelp();
                mqttClient.setCallback(mqttHelp);
            }

            mqttClient.connect();
        } catch (MqttException e) {
            LogUtil.error("MqttUtil::Create connection fail. Reason = " + e.getMessage());
            reconnectMqtt(mqttClient);
        }
    }

    private MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setServerURIs(new String[] { TestParametersUtil.getInstance().getTestParameters().getMqttHostUrl() });
        mqttConnectOptions.setUserName(TestParametersUtil.getInstance().getTestParameters().getMqttUsername());
        mqttConnectOptions.setPassword(TestParametersUtil.getInstance().getTestParameters().getMqttPassword().toCharArray());
        mqttConnectOptions.setConnectionTimeout(60);
        mqttConnectOptions.setKeepAliveInterval(60);
        mqttConnectOptions.setCleanSession(true);
        return mqttConnectOptions;
    }
}
