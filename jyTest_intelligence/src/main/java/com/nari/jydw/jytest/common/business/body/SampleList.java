package com.nari.jydw.jytest.common.business.body;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SampleList {
    private Integer sysType = 1;
    private Integer pageNum = 1;
    private Integer pageSize = 3;
    private String startTime = "";
    private String endTime = "";
    private String stationId = "";
    private List<Integer> labelIds = new ArrayList<>();
    private String state = "";
    private String dataSetId = "";
    private String scene = "";
    private String target = "";
//    private Integer level = 0;
//    private String flag = "";
    private String equipmentType = "";
    private String equipmentPartName = "";
    private String deviceVoltageLevel = "";
    private String pointLevel = "";
    private String algFactoryId = "";
    private String areaFlag = "";
    private String voltageLevel = "";
    private Integer isMain = 0;
    private Integer checkCycle = 0;
    private String factoryName = "";



}
