package com.example.nettyserver.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * Created by Eric on 2017/12/6.
 */
public class ActionBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods=bean.getClass().getMethods();
        for (Method method : methods) {
            ActionMap actionMap=method.getAnnotation(ActionMap.class);
            if(actionMap!=null){
                Action action=new Action();
                action.setMethod(method);
                action.setObject(bean);
                ActionMapUtil.put(actionMap.key(), action);
            }
        }
        return bean;
    }
}
