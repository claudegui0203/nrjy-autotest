package com.nari.jydw.jytest.interfaceTest.actions;

public interface ActionParameterBuilder<T> {

    /**
     * After completing parameter builder, need call this method to build Action, and then call perform(conf,user)
     * @return
     */
    T buildAction();
}
