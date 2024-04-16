package com.nari.jydw.jytest.common.business.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse extends ResponseBody {
    private int code = 0;
    private String msg = "";
    private LoginData data;

    public String getToken() {
        return data.getToken();
    }
}

@Setter
@Getter
class LoginData {
    UserInfo userInfo;
    String token = "";
    String type = "";
    String roleName = "";
    String roleId = "";
    String realName = "";
}

@Setter
@Getter
class UserInfo {
    int id = 0;
    String username = "";
}