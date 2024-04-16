package com.nari.jydw.jytest.common.business.body;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Register {
    private String realName = "";
    private String password = "";
    private String username = "";
    private List<String> roleNames = new ArrayList<>();
    private List<Integer> roleIds = new ArrayList<>();
    private String company = "";
    private String phone = "";
    private String isDisplay = "";
    private String status = "";
}
