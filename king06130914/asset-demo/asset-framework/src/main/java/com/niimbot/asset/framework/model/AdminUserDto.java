package com.niimbot.asset.framework.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * created by chen.y on 2020/10/23 17:26
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value="AdminUserDto", description="运营后台账户")
public class AdminUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号Id")
    private Long id;

    @ApiModelProperty(value = "认证中心Id")
    private Long unionId;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "姓名")
    private String empName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "状态 1:正常  2:禁用 ")
    private Short status;

    @ApiModelProperty(value = "角色Id")
    private Long roleId;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    private Boolean isAdmin;

}
