package com.nari.jydw.jytest.interfaceTest.actions;

import com.nari.jydw.jytest.interfaceTest.utils.LogUtil;

import java.lang.reflect.*;
import java.util.*;

public class ActionBuilderProxy implements InvocationHandler {
    private final String BUILDMETHOD = "buildAction";
    Class<?> target;

    Class<?> object;
    Map<String, List<Object>> properties = new HashMap<String,List<Object>>();
    Map<String,Map<Object,Object>> mapProperties = new HashMap<String,Map<Object,Object>>();

    public ActionBuilderProxy(Class<?> target) {
        this.target = target;
        this.object = getParameterType(target);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(isPropertySetter(method)) {
            handParamWithBasic(method,args);
            handParamWithMap(method,args);
            return proxy;
        }
        if(isBuildMethod(method)) {
            Object obj = object.newInstance();
            setPropperties4Basic(obj);
            setPropperties4Map(obj);
            return obj;
        }
        throw new Exception(target.getClass() + " has no method:" + method.getName());
    }

    /**
     * When target object is a Map
     *
     */
    private void handParamWithMap(Method method,Object[] args) {
        if(method.getParameterTypes().length != 2 || args.length != 2)
            return;
        String methodName = method.getName();
        if(!mapProperties.containsKey(methodName)) {
            Map<Object,Object> tmp = new HashMap<Object,Object>();
            mapProperties.put(methodName, tmp);
        }
        mapProperties.get(methodName).put(args[0], args[1]);
    }

    private void handParamWithBasic(Method method,Object[] args) {
        if(method.getParameterTypes().length != 1 || args.length != 1)
            return;
        if(!properties.containsKey(method.getName())) {
            List<Object> tmp = new ArrayList<Object>(); //Can set same parameter with 1+ times, and put them in a list
            properties.put(method.getName(), tmp);
        }
        if(!properties.get(method.getName()).contains(args[0]))
            properties.get(method.getName()).add(args[0]);
    }

    //length=1:basic value,length=2:hashmap
    boolean isPropertySetter(Method method) {
        return (method.getParameterTypes().length == 1 || method.getParameterTypes().length == 2 ) && target.isAssignableFrom(method.getReturnType());
    }

    boolean isBuildMethod(Method method) {
        try {
            Method buildMethod = ActionParameterBuilder.class.getDeclaredMethod(BUILDMETHOD);
            return method.equals(buildMethod);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("");
        }
    }

    private Class<?> getParameterType(Class<?> clzz) {
        Type[] types = clzz.getGenericInterfaces();
        for(Type type : types) {
            if(type instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType)type;
                if(paramType.getRawType() == ActionParameterBuilder.class) {
                    return (Class<?>)paramType.getActualTypeArguments()[0];
                }
            }
        }
        return null;
    }

    private void setPropperties4Basic(Object obj) throws Exception {
        for(Map.Entry<String, List<Object>> entry : properties.entrySet()) {
            String key = entry.getKey();
            List<Object> value = properties.get(key);

            try {
                Field field = getFieldByName(obj,key);

                boolean isAccess = field.isAccessible();
                field.setAccessible(true);

                if(field.getType() == List.class && value.get(0).getClass() != List.class && value.get(0).getClass() != ArrayList.class ) {
                    field.set(obj, value); //For List parameter
                } else if(field.getType() == Set.class && value.get(0).getClass() != Set.class && value.get(0).getClass() != HashSet.class && value.get(0).getClass() != TreeSet.class) {
                    field.set(obj, new HashSet<Object>(value)); //For Set parameter
                } else {
                    field.set(obj, value.get(value.size() - 1)); //For normal parameter
                }

                field.setAccessible(isAccess);
            } catch (Exception e) {
                throw e;
            }
        }
    }

    private void setPropperties4Map(Object obj) throws Exception {
        for(Map.Entry<String, Map<Object,Object>> entry : mapProperties.entrySet()) {
            String key = entry.getKey();
            Map<Object,Object> value = mapProperties.get(key);

            try {
                Field field = getFieldByName(obj,key);

                boolean isAccess = field.isAccessible();
                field.setAccessible(true);

                if(field.getType() == Map.class ) {
                    field.set(obj, value); //For List parameter
                }
                field.setAccessible(isAccess);
            } catch (Exception e) {
                throw e;
            }
        }
    }

    private Field getFieldByName(Object obj,String name) throws Exception {
        try {
            return obj.getClass().getDeclaredField(name);
        } catch(Exception e1) {
            try {
                return obj.getClass().getSuperclass().getDeclaredField(name);
            } catch (Exception e2) {
                LogUtil.error("Please ensure " + obj.getClass() + " has member:" + name);
                throw new Exception("Member " + name + " not found!!!");
            }
        }
    }
}
