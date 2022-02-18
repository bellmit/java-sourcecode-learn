package com.niimbot.asset.system.model;

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
 * 客户角色扩展
 * </p>
 *
 * @author chen.y
 * @since 2020-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AsCusUserExt对象", description = "客户角色扩展")
@TableName(value = "as_cus_user_ext")
public class AsCusUserExt implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "所属代理商ID")
    private Integer agentId;

    @ApiModelProperty(value = "代理商平台注册的客户id")
    private Integer customerId;

    @ApiModelProperty(value = "默认打印标签ID")
    private Long defaultTagId;

    @ApiModelProperty(value = "耗材默认打印标签模板id")
    private Integer defaultCftagId;

    @ApiModelProperty(value = "耗材默认打印标签模板 二维码内容 1-耗材唯一ID 2-耗材条码号")
    private Integer defaultCftagCode;

    @ApiModelProperty(value = "默认资产模版编号")
    private String defaultTplId;

    @ApiModelProperty(value = "默认材质id")
    private Long defaultMaterialId;

    @ApiModelProperty(value = "默认浓度")
    private Integer defaultConcentration;

    @ApiModelProperty(value = "pc 刷新时间(用户状态发生变化,同步操作APP)")
    private LocalDateTime pcRefreshAt;

    @ApiModelProperty(value = "APP 刷新时间(用户状态发生变化,同步操作PC)")
    private LocalDateTime appRefreshAt;

    @ApiModelProperty(value = "APP 极光推送ID")
    private String jpushId;

    @ApiModelProperty(value = "上一次登陆时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "当前登陆时间")
    private LocalDateTime currentLoginTime;

    @ApiModelProperty(value = "最后一次修改密码的时间")
    private LocalDateTime lastUpdatepwdAt;


}
