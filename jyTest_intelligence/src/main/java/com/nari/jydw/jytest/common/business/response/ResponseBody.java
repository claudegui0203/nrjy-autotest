package com.nari.jydw.jytest.common.business.response;

import com.nari.jydw.jytest.interfaceTest.utils.JsonUtil;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseBody {
    private int code = 0;
    private String msg = "";
    private Object data;

    @Override
    public String toString() {
        return JsonUtil.getGson().toJson(this);
    }
}
