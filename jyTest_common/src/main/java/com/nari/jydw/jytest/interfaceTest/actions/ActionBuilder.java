package com.nari.jydw.jytest.interfaceTest.actions;

import java.lang.reflect.Proxy;

public class ActionBuilder<T> {
    Class<T> actionParameterBuilder;

    private ActionBuilder(Class<T> actionParameterBuilder) {
        if(!actionParameterBuilder.isInterface()) {
            throw new IllegalArgumentException(actionParameterBuilder.getClass() + " must be interface");
        }
        this.actionParameterBuilder = actionParameterBuilder;
    }

    /**
     * Need build action parameter for building action, this parameter is to select a correct action parameter builder, can get parameter builder from:
     * <code>ActionParameterBuilderMap4Http</code>
     * @param actionParameterBuilder ActionParameterBuilderMap4Http.xxxx
     * @return
     */
    public static <T> T createActionParameterBuilder(Class<T> actionParameterBuilder) {
        return new ActionBuilder<T>(actionParameterBuilder).createActionParameterBuilder();
    }

    @SuppressWarnings("unchecked")
    private T createActionParameterBuilder() {
        ActionBuilderProxy jdkProxy = new ActionBuilderProxy(actionParameterBuilder);
        return (T)Proxy.newProxyInstance(actionParameterBuilder.getClassLoader(),
                new Class[]{actionParameterBuilder},
                jdkProxy);
    }
}
