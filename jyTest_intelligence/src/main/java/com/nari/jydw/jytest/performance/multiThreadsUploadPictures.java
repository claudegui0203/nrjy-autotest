package com.nari.jydw.jytest.performance;

import com.nari.jydw.jytest.CommonTestCases;
import com.nari.jydw.jytest.common.business.body.AlertPic;
import com.nari.jydw.jytest.interfaceTest.helps.MqttHelp;
import com.nari.jydw.jytest.interfaceTest.utils.HttpUtil;
import com.nari.jydw.jytest.interfaceTest.utils.JsonUtil;
import com.nari.jydw.jytest.interfaceTest.utils.LogUtil;
import com.nari.jydw.jytest.common.TestParametersUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class multiThreadsUploadPictures extends CommonTestCases {
    private Integer count = 3000;
    private Integer threadsCount = 3;
    private final File file = new File("D:\\performance\\00133f11fd324292bb576bd2c51c0963.JPG");

    @Test
    public void uploadOneThousandPicturesOneByOne() throws IOException, InterruptedException, ExecutionException {
        List<HashSet<File>> pictures = generatePictures();
        checkPictures(pictures);

        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        List<Future<Void>> futures = new ArrayList<>();

        long startTime = System.nanoTime();
        LogUtil.info("Begin to performance testing. Now time is " + new Date());

        for (int i = 0; i < threadsCount; i++) {
            int finalI = i;
            Future<Void> future = executorService.submit(() -> {
                String url = "http://10.139.200.69:10013/api/uploadImage";
                MqttClient mqttClient = generateMqttClient(finalI);
                subscribe(mqttClient, 0, "jydw/topic/upload_pic");
                for (File picture : pictures.get(finalI)) {
                    String picPath = "upload_pic/220kV/2024/02/04/20240204_143912_/profile_jydw_upload/capturepicpath/2024-02-04/" + picture.getName();
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("picPath", picPath);

                    CloseableHttpResponse response = null;
                    LogUtil.info("Thread ID = " + finalI + ", begin to upload picture. Picture name = " + picture.getName());
                    try {
                        response = HttpUtil.postUploadFile(url, picture, parameters, null);
                        LogUtil.info("status code = " + response.getStatusLine().getStatusCode() + ", body = " + EntityUtils.toString(response.getEntity(), "UTF-8"));
                        if (response.getStatusLine().getStatusCode() != 200) {
                            LogUtil.error("Upload picture fail. And response = " + response.getStatusLine().getStatusCode());
                        } else {
                            LogUtil.info("Begin to sent MQTT message. picture name = " + picture.getName());

                            AlertPic alertPic = new AlertPic();
                            alertPic.setFile_path(picPath);
                            sendMessage(mqttClient, 0 , "jydw/topic/upload_pic", JsonUtil.getGson().toJson(alertPic));
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                LogUtil.info("Thread " + finalI + " end testing.");

                try {
                    TimeUnit.SECONDS.sleep(15);
                    mqttClient.disconnect();
                    mqttClient.close();
                } catch (InterruptedException | MqttException e) {
                    throw new RuntimeException(e);
                }

                return null;
            });
            futures.add(future);
        }

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

            TimeUnit.SECONDS.sleep(10);
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
                    throw new RuntimeException(ee);
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
            File picture = new File("D:\\performance\\" + pictureName + ".JPG");

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

    private void subscribe(MqttClient mqttClient, Integer qos, String topic) {
        try {
            mqttClient.subscribe(topic, qos);
        }catch (MqttException e){
            LogUtil.error("subscribe::Subscribe message fail. Reason = " + e.getMessage());
            reconnectMqtt(mqttClient);
        }
    }

    private void sendMessage(MqttClient mqttClient, Integer qos, String topic, String message) {
        MqttMessage mqttMsg = new MqttMessage(message.getBytes());
        mqttMsg.setQos(qos);
        mqttMsg.setRetained(false);

        try {
            mqttClient.publish(topic, mqttMsg);
        } catch (MqttException e) {
            LogUtil.error("sendMessage::Send MQTT message fail. Reason = " + e.getMessage());
            reconnectMqtt(mqttClient);
        }
    }
}
