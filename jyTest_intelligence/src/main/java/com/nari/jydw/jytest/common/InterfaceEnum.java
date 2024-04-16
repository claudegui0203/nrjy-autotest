package com.nari.jydw.jytest.common;

import lombok.Getter;

@Getter
public enum InterfaceEnum {
    LOGIN("/api/auth/login"),
    SAMPLELIST("/api/sample/sampleView/sampleList"),
    SAMPLELIBRARYBD("/statistics/sampleLibraryBd"),
    DELETESAMPLEMAIN("/api/sample/sampleView/deleteSampleMain"),
    DELETEUPLOAD("/api/sample/sampleView/deleteUpload"),
    REGISTER("/api/user/register"),
    UPDATEUSER("/api/user/update"),
    CHECKUSERNAME("/api/user/checkName"),
    UPDATECHECKUSERNAME("/api/user/upCheckName"),
    QUERYUSER("/api/user/list"),
    UPDATEUSERINFO("/api/user/update"),
    CHECKPASSWD("/api/user/checkPwd"),
    UPDATEPASSWORD("/api/user/upPwd"),
    RESETPASSWORD("/api/user/resetPwd"),
    DELETEUSER("/api/user/del");

    private InterfaceEnum(String api) {
        this.api = api;
    }

    private String api;
}
