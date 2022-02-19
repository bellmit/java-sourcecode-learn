package com.niimbot.asset.model;

import com.niimbot.validate.ValidationUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 手机号登录对象
 * created by chen.y on 2020/11/3 13:59
 */
@Data
public class MobileBody {

    /**
     * 手机号
     */
    @NotBlank(message = "请输入手机号 ")
    @Pattern(regexp = ValidationUtils.MOBILE_REG, message = "手机号格式不正确")
    private String mobile;

    /**
     *  验证码
     */
    @NotBlank(message = "请输入验证码")
    private String smsCode;

    /**
     * 推送服务id
     */
    @ApiModelProperty(value = "推送服务id")
    private String pushId;
}
