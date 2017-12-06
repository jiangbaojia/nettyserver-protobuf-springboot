package com.example.nettyserver.controller;

/**
 * Created by Eric on 2017/12/6.
 */
import java.lang.reflect.Method;

public class Action {

    private Method method;

    private Object object;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }


}