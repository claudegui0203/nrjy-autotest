package com.nari.jydw.jytest.common.business.response;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SearchUserResponseBody {
    private int code = 0;
    private String msg = "";
    private UserInfoData data = null;

    public int getTotal() {
        return data.getTotal();
    }

    public int getTotalPage() {
        return data.getTotalPage();
    }

    public List<Item> getItems() {
        return data.getItems();
    }
}

@Setter
@Getter
class UserInfoData {
    private Integer total = 0;
    private Integer totalPage = 0;
    private List<Item> items = new ArrayList<>();
}

@Setter
@Getter
class Item {
    private Long id = 0L;
    private String username = "";
    private String realName = "";
    private String password = "";
    private String status = "";
    private Long createUserId = 0L;
    private String createTime = "";
    private Integer sysType = 0;
    private String roleNames = "";
    private String time = "";
    private Integer roleId = 0;
    private String company = "";
    private String phone = "";
}
