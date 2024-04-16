package com.nari.jydw.jytest.common.business.body;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class DeleteSampleMain {
    private Integer sysType;
    private List<String> sampleIdList = new ArrayList<>();
}
