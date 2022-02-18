package com.niimbot.asset.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录对象
 */
@Data
@ApiModel(value = "LoginBody", description = "登录请求体")
public class LoginBody {
    /**
     * 用户名
     */
    @NotBlank(message = "请输入帐号、手机号或邮箱")
    @ApiModelProperty(value = "账号、手机号或邮箱", required = true)
    private String account;

    /**
     * 用户密码
     */
    @NotBlank(message = "请输入密码")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    private String code;

    /**
     * 唯一标识
     */
    @ApiModelProperty(value = "验证码uuid")
    private String uuid;

    /**
     * 推送服务id
     */
    @ApiModelProperty(value = "推送服务id")
    private String pushId;

}
