package com.niimbot.asset.framework.annotation;

import java.lang.annotation.*;

/**
 * post请求接受json
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestJson {
    String value();
}