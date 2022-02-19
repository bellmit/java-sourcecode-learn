package com.niimbot.asset.framework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * created by chen.y on 2020/10/27 11:59
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "CusUserDto", description = "PC端用户")
public class CusUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * AsCusUser 用户
     */
    @ApiModelProperty(value = "用户Id")
    private Long id;
    @ApiModelProperty(value = "用户唯一ID JC30000001 开始")
    private String account;
    @ApiModelProperty(value = "注册中心id")
    private String unionId;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "手机")
    private String mobile;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "公司ID（租户ID）")
    private Long companyId;
    @ApiModelProperty(value = "组织部门ID")
    private Long orgId;
    @ApiModelProperty(value = "数据范围（0、查看全部，1、仅查看当前用户，2、仅查看当前部门，3、所在部门及子部门，4、自定义部门列表）")
    private Short dataScope;
    @ApiModelProperty(value = "状态 1:启用 2:禁用")
    private Short status;
    @ApiModelProperty(value = "弹窗协议状态 1-关闭  2-已同意")
    private Short agreementStatus;
    @ApiModelProperty(value = "引导“常用功能”、“系统初始化配置” 状态 1-需要  2-不需要")
    private Short guideStatus;

    private Boolean isAdmin;

    /**
     * AsCusUserExt
     */
    @ApiModelProperty(value = "默认打印标签ID")
    private Integer defaultTagId;
    @ApiModelProperty(value = "耗材默认打印标签模板id")
    private Integer defaultCftagId;
    @ApiModelProperty(value = "耗材默认打印标签模板 二维码内容 1-耗材唯一ID 2-耗材条码号")
    private Integer defaultCftagCode;
    @ApiModelProperty(value = "默认资产模版编号")
    private String defaultTplId;

    /**
     * 员工或账号状态变化控制字段、更新redis登录状态
     */
    @ApiModelProperty(value = "角色权限是否变更")
    @JsonIgnore
    private boolean roleChanged;
}
