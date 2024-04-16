package com.nari.jydw.jytest.common.business.body;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeleteUpload {
    private Integer sysType = 0;
    private String uploadId = "";
}
