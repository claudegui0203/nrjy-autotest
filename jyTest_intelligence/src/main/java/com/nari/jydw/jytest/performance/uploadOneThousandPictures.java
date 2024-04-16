package com.nari.jydw.jytest.performance;

import com.nari.jydw.jytest.CommonTestCases;
import com.nari.jydw.jytest.common.business.body.AlertPic;
import com.nari.jydw.jytest.interfaceTest.utils.HttpUtil;
import com.nari.jydw.jytest.interfaceTest.utils.JsonUtil;
import com.nari.jydw.jytest.interfaceTest.utils.LogUtil;
import com.nari.jydw.jytest.interfaceTest.utils.MqttUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.testng.annotations.Test;

import java.io.*;
import java.util.*;

public class uploadOneThousandPictures extends CommonTestCases {
    private Integer count = 1000;
    private String url = "http://10.139.200.69:10013/api/uploadImage";
    private final File file = new File("performance/00133f11fd324292bb576bd2c51c0963.JPG");
//    private final File file = new File("D:\\performance\\00133f11fd324292bb576bd2c51c0963.JPG");

    @Test
    public void uploadOneThousandPicturesOneByOne() throws IOException {
        HashSet<File> pictures = new HashSet<>();
        generatePictures(count, file, pictures);

        long startTime = System.nanoTime();
        LogUtil.info("Begin to performance testing. Now time is " + new Date());

        Thread getCallBack = new Thread(new Runnable() {
            @Override
            public void run() {
                int receiveMqttSuccess = 0;
                while (true) {
                    IMqttDeliveryToken iMqttDeliveryToken = MqttUtil.getInstance().getMqttHelp().getIMqttDeliveryToken();
                    if (iMqttDeliveryToken == null) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        break;
                    }
                }

                if (MqttUtil.getInstance().getMqttHelp().getIMqttDeliveryToken().isComplete()) {
                    receiveMqttSuccess++;
                }
                LogUtil.info("Receive MQTT response = " + receiveMqttSuccess);
            }
        });
        getCallBack.start();

        for (File picture : pictures) {
            String picPath = "pic_alarm/220kV古圣变电站/2024/02/03/20240203_164835_待用线353_待用线3534隔离开关ABC相_基座/profile_jydw_upload/capturepicpath/2024-02-03/" + picture.getName();
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("picPath", picPath);

            CloseableHttpResponse response = HttpUtil.postUploadFile(url, picture, parameters, generateHeaders());
            LogUtil.info("status code = " + response.getStatusLine().getStatusCode() + ", body = " + EntityUtils.toString(response.getEntity(), "UTF-8"));

            if (response.getStatusLine().getStatusCode() == 200) {
//                uploadPic uploadPic = new uploadPic();
//                uploadPic.setFile_path(picPath);
                AlertPic alertPic = new AlertPic();
                alertPic.setFile_path(picPath);
                LogUtil.info("body = " + JsonUtil.getGson().toJson(alertPic));
                MqttUtil.getInstance().sendMessage(0, "jydw/topic/upload_pic", JsonUtil.getGson().toJson(alertPic));
            }
        }

        long endTime = System.nanoTime();
        LogUtil.info("End performance testing. Now time is " + new Date() + ". Total use time = " + (endTime - startTime)/1000000000 + " seconds.");
    }

    public void generatePictures(Integer count, File file, HashSet<File> pictures) {
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            String pictureName = "1" + String.valueOf(rand.nextInt(8)) + String.valueOf(rand.nextInt(1000000000)) + String.valueOf(rand.nextInt(100000000));

            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                File picture = new File("performance/" + pictureName + ".JPG");
                pictures.add(picture);
                FileOutputStream fileOutputStream = new FileOutputStream(picture);

                byte b[] = new byte[1024];
                int len = 0;
                while((len = fileInputStream.read(b)) != -1) {
                    fileOutputStream.write(b);
                }
            } catch (IOException ee) {
                LogUtil.error("generatePictures:: Generate picture fail. reason = " + ee.getMessage());
                throw new RuntimeException(ee);
            }
        }
    }
}
