package com.nari.jydw.jytest.common.business.body;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class AlertPic {
    private String area_name = "交流35kV";
    private String file_path = "pic_alarm/220kV古圣变电站/2024/02/03/20240203_164835_待用线353_待用线3534隔离开关ABC相_基座/profile_jydw_upload/capturepicpath/2024-02-03/1f5ab95ed9954726ae43c4fc284da021.jpg";
    private String appearance_type = "";
    private String bay_name = "待用线353";
    private String station_code = "1638005485132668928";
    private String device_type = "";
    private String area_id = "438a16d6fb13462c8d9a8f3af3046e1a";
    private List<Defact> defect = new ArrayList<>();
    private String pic_height = "1080";
    private String file_type = "2";
    private String patrolDevice_name = "220kV古圣变-开关室35kV待用线3532-南侧云台#1";
    private String station_name = "220kV古圣变电站";
    private String phase = "4";
    private String component_id = "bc44e52cbd4d46e7a2498fb7baf6c534";
    private String device_id = "c9ae06c12c8fc1c38796673df50870e0";
    private String component_name = "基座、机械闭锁及限位部分";
    private String main_device_id = "f7722e8688fc4e2cbc975b5b19d6ed2e";
    private String bay_id = "ebba16b46b754f608b6e42f3d6c55210";
    private String model_id = "33";
    private String meter_type = "";
    private String main_device_name = "待用线3534隔离开关ABC相";
    private String data_type = "0x01";
    private String msg_type = "alarm";
    private String patrolDevice_code = "1705131804642041856";
    private String time;
    private String pic_width = "1920";
    private String device_pos = "13";

    public AlertPic() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        time = currentTime.format(formatter);

        Defact defact = new Defact();
        defect.add(defact);
    }
}

@Setter
@Getter
class Defact {
    private float confidence = 51.7F;
    private float y1 = 903.0F;
    private float x1 = 1164.0F;
    private float y2 = 1075.0F;
    private float x2 = 1334.0F;
    private String type = "bmwh";
    private String desc = "表面污秽(坐标位置1164.0,1334.0,903.0,1075.0;置信度51.70%)";
}
