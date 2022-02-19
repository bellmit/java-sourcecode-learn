package com.niimbot.asset.system.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 客户账号
 * </p>
 *
 * @author chen.y
 * @since 2020-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AsCusUser对象", description = "客户账号")
@TableName(value = "as_cus_user")
public class AsCusUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号id（员工Id）")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonAlias(value = {"userId", "id"})
    private Long id;

    @ApiModelProperty(value = "认证中心Id")
    private String unionId;

    @ApiModelProperty(value = "登录账号 JC30000001 开始")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "公司ID（租户ID）")
    @TableField(fill = FieldFill.INSERT)
    private Long companyId;

    @ApiModelProperty(value = "数据范围（0、查看全部，1、仅查看当前用户，2、仅查看当前部门，3、所在部门及子部门，4、自定义部门列表）")
    private Short dataScope;

    @ApiModelProperty(value = "状态 1:启用 2:禁用")
    private Short status;

    @ApiModelProperty(value = "来源  1-本系统（pc端） 2-本系统（Android端） 3-本系统（iOS端） 10-代理商平台同步 ")
    private Integer source;

    @ApiModelProperty(value = "业务状态(1.未同步（没有）；2.已同步；3.试用-未到期；4.试用-已到期；5.成交-免费版（没有）；6.成交-付费版；7.成交-即将到期；8.成交-已到期；9.成交-已流失；10.已注销)")
    private Integer businessStatus;

    @ApiModelProperty(value = "弹窗协议状态 1-关闭  2-已同意")
    private Short agreementStatus;

    @ApiModelProperty(value = "引导“常用功能”、“系统初始化配置” 状态 1-需要  2-不需要")
    private Short guideStatus;

    @ApiModelProperty(value = "是否删除: 0未删除 1 已删除")
    @TableLogic
    private Boolean isDelete;

    @ApiModelProperty(value = "创建者")
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(update = "now()", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;


}
