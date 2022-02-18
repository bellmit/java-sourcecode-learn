package com.niimbot.asset.framework.annotation;

import java.lang.annotation.*;

/**
 * 防表单重复提交注解
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {
}
