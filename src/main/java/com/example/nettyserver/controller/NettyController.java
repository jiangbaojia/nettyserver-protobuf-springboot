package com.example.nettyserver.controller;

/**
 * Created by Eric on 2017/12/1.
 */

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface NettyController {


}