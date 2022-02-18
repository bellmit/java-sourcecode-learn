package com.niimbot.validate;

/**
 * DTO对象校验工具类
 *
 * @author Wangzhiwen
 */
public class ValidationUtils {

    /**
     * 手机号正则
     */
    public static final String MOBILE_REG = "^(((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[6])|(17[0135678])|(18[0-9])|(19[589]))\\d{8})$";

    public static final String EMAIL_REG = "^$|^[a-zA-Z0-9._-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
}
