package com.nari.jydw.jytest.interfaceTest.actions;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTestAction {
    protected abstract void prepareParameters();
    protected abstract void sendRequest();
    protected abstract void verify();
    protected Map<String, Object> actionResult = new HashMap<String,Object>();

    public void perform() {
        prepareParameters();
        sendRequest();
        verify();
    }

    public Object getResult(String key) {
        return actionResult.get(key);
    }
}
