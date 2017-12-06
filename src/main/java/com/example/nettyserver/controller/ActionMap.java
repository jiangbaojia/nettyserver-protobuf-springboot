package com.example.nettyserver.controller;

import java.lang.annotation.*;

/**
 * Created by Eric on 2017/12/6.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ActionMap {

    int key();

}
