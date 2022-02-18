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

/**
 * <p>
 * 用户扩展表
 * </p>
 *
 * @author dk
 * @since 2021-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AsUserExt对象", description="用户扩展表")
@TableName(value = "as_user_ext")
public class AsUserExt implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    @ApiModelProperty(value = "来源  1-本系统（pc端） 2-本系统（Android端） 3-本系统（iOS端）4-官网来源 10-代理商平台同步 ")
    private Integer source;

    @ApiModelProperty(value = "代理商平台注册的客户id")
    private Integer customerId;

    @ApiModelProperty(value = "所属代理商ID")
    private Integer agentId;

    @ApiModelProperty(value = "业务状态(1.未同步（没有）；2.已同步；3.试用-未到期；4.试用-已到期；5.成交-免费版（没有）；6.成交-付费版；7.成交-即将到期；8.成交-已到期；9.成交-已流失；10.已注销)")
    private Integer businessStatus;

    @ApiModelProperty(value = "默认材质id")
    private Long defaultMaterialId;

    @ApiModelProperty(value = "默认浓度")
    private Integer defaultConcentration;

    @ApiModelProperty(value = "打印服务升级弹窗状态 1-需要  2-不需要")
    private Boolean printUpgradeStatus;

    @ApiModelProperty(value = "弹窗协议状态 1-关闭  2-已同意")
    private Boolean agreementStatus;

    @ApiModelProperty(value = "引导“常用功能”、“系统初始化配置” 状态 1-需要  2-不需要")
    private Boolean guideStatus;

    @ApiModelProperty(value = "类型范围")
    private String typePower;

    @ApiModelProperty(value = "区域范围")
    private String areaPower;

    @ApiModelProperty(value = "组织范围")
    private String orgPower;

    @ApiModelProperty(value = "耗材分类范围")
    private String cfTypePower;

    @ApiModelProperty(value = "耗材仓库范围")
    private String cfDepotPower;

    @ApiModelProperty(value = "财务授权公司权限")
    private String acctCompanyPower;


}
