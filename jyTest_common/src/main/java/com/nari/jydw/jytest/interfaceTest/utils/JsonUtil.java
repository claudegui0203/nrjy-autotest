package com.nari.jydw.jytest.interfaceTest.utils;

import com.google.gson.Gson;

public class JsonUtil {
    public static Gson gson = null;

    public static Gson getGson() {
        gson = new Gson();
        return gson;
    }
}
