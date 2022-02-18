package com.niimbot.asset.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户账号表
 * </p>
 *
 * @author dk
 * @since 2021-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AsUser对象", description="用户账号表")
@TableName(value = "as_user")
public class AsUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "用户唯一ID JC30000001 开始")
    private String uid;

    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "顶级组织编号ID")
    private Long rootOrgId;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "用户类型 1-超级管理员  2-公司管理员 3-普通")
    private Integer userType;

    @ApiModelProperty(value = "状态 1:正常  2:禁用 ")
    private Boolean status;

    @ApiModelProperty(value = "是否软删除 0-否  1-是")
    private Boolean isDelete;

    @ApiModelProperty(value = "默认打印标签ID")
    private Long defaultTagId;

    @ApiModelProperty(value = "耗材默认打印标签模板id")
    private Long defaultCftagId;

    @ApiModelProperty(value = "耗材默认打印标签模板 二维码内容 1-耗材唯一ID 2-耗材条码号")
    private Integer defaultCftagCode;

    @ApiModelProperty(value = "默认资产模版编号")
    private String defaultTplId;

    @ApiModelProperty(value = "APP 极光推送ID")
    private String jpushId;

    @ApiModelProperty(value = "上一次登陆时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "当前登陆时间")
    private LocalDateTime currentLoginTime;

    @ApiModelProperty(value = "pc 刷新时间(用户状态发生变化,同步操作APP)")
    private LocalDateTime pcRefreshAt;

    @ApiModelProperty(value = "APP 刷新时间(用户状态发生变化,同步操作PC)")
    private LocalDateTime appRefreshAt;

    @ApiModelProperty(value = "最后一次修改密码的时间")
    private LocalDateTime lastUpdatepwdAt;

    @ApiModelProperty(value = "创建者")
    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
