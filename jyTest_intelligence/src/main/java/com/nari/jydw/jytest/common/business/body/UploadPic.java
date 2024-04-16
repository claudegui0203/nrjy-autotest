package com.nari.jydw.jytest.common.business.body;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class UploadPic {
    private String file_path = "upload_pic/220kV金家岭变电站/2023/12/11/20231211_143912_/profile_jydw_upload/capturepicpath/2023-12-11/00133f11fd324292bb576bd2c51c0963.jpg";
    private String station_code = "12M00000013840987";
    private String pic_height = "1080";
    private String patrolDevice_name = "二次屏柜";
    private String station_name = "220kV金家岭变电站";
    private String device_id = "c091abb1598c409386aeb9e0812e4586";
    private String model_id = "33";
    private String msg_type = "images";
    private String patrolDevice_code = "1604751594568597505";
    private String time = "";
    private String pic_width = "1920";

    public UploadPic() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        time = currentTime.format(formatter);
    }
}
