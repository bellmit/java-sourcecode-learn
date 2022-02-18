package com.niimbot.asset.framework.annotation;

import java.lang.annotation.*;

/**
 * 转换字典
 * created by chen.y on 2020/11/9 18:07
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DictConvert {

    /**
     * 字典类型 dict_type 如果是 List 则无需指定 该值
     */
    String value() default "";

    /**
     * 填写该字段后 该字段会拿到 refField 配置的字段的值并且根据字典转换
     */
    String refField() default "";

}