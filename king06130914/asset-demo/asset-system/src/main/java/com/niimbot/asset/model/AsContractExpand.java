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
 * 合同表- 合同内容补充升级
 * </p>
 *
 * @author dk
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AsContractExpand对象", description="合同表- 合同内容补充升级")
@TableName(value = "as_contract_expand")
public class AsContractExpand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "顶级组织编号")
    private Long rootOrgId;

    @ApiModelProperty(value = "当前绑定版本编号")
    private Long versionId;

    @ApiModelProperty(value = "用户类型 1-正常  2-测试")
    private Boolean type;

    @ApiModelProperty(value = "开启资产履历模块 1-关闭  2-开启")
    private Boolean enabledAssetLog;

    @ApiModelProperty(value = "开启耗材管理模块 1-关闭  2-开启")
    private Boolean enabledComsumables;

    @ApiModelProperty(value = "开启微信员工端 1-关闭  2-开启")
    private Boolean enabledWechat;

    @ApiModelProperty(value = "开启财务模块 1-关闭  2-开启")
    private Boolean enabledAcct;

    @ApiModelProperty(value = "开启自定义编码 1-关闭  2-开启")
    private Boolean enabledCustomCode;

    @ApiModelProperty(value = "扩展的标签数量(视情况-可为负数)")
    private Integer expandTagNum;

    @ApiModelProperty(value = "扩展的资产数量(视情况-可为负数)")
    private Integer expandAssetNum;

    @ApiModelProperty(value = "扩展耗材数量")
    private Integer expandConsumablesNum;

    @ApiModelProperty(value = "扩展模板数量")
    private Integer expandTemplateNum;

    @ApiModelProperty(value = "扩展帐号数量")
    private Integer expandAccountNum;

    @ApiModelProperty(value = "有效合同开始时间")
    private LocalDateTime signAt;

    @ApiModelProperty(value = "有效合同结束时间")
    private LocalDateTime dueAt;

    @ApiModelProperty(value = "资产审批模块 1-关闭  2-开启")
    private Integer enabledAssetAudit;

    @ApiModelProperty(value = "员工扩展数量")
    private Integer expandEmployeeNum;

    @ApiModelProperty(value = "企业扩展数量")
    private Integer expandCompanyNum;

    @ApiModelProperty(value = "照片扩展数量")
    private Integer expandPhotoNum;

    @ApiModelProperty(value = "附件扩展数量")
    private Integer expandAppendixNum;

    @ApiModelProperty(value = "创建者")
    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
