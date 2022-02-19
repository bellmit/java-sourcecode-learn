package com.niimbot.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author XieKong
 * @date 2020/12/16 14:54
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@ApiModel(value="CompanyDetailDto", description="会员详情对象")
public class CompanyDetailDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业Id")
    private Long id;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "用户名")
    private String empName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "所属行业")
    private String industryName;

    @ApiModelProperty(value = "使用版本")
    private String versionName;

    @ApiModelProperty(value = "会员注册日期")
    private LocalDateTime userCreateTime;

    @ApiModelProperty(value = "到期日期")
    private LocalDateTime dueAt;

    @ApiModelProperty(value = "企业开关控制")
    private CompanySwitchDto expandSwitch;

    /**
     * 版本配置信息
     */
    @ApiModelProperty(value = "版本公司数量上限")
    private Integer companyNum;

    @ApiModelProperty(value = "版本账号数量上限")
    private Integer accountNum;

    @ApiModelProperty(value = "版本员工数量上限")
    private Integer employeeNum;

    @ApiModelProperty(value = "版本耗材档案数量上限")
    private Integer consumablesNum;

    @ApiModelProperty(value = "版本资产数量上限")
    private Integer assetNum;

    @ApiModelProperty(value = "版本资产模板数量上限")
    private Integer assetTplNum;

    @ApiModelProperty(value = "版本标签模板数量上限")
    private Integer tagTplNum;

    @ApiModelProperty(value = "版本照片数量上限")
    private Integer photoNum;

    @ApiModelProperty(value = "版本附件数量上限")
    private Integer appendixNum;

    /**
     * 企业配置
     */
    @ApiModelProperty(value = "会员公司数量上限")
    private Integer expandCompanyNum;

    @ApiModelProperty(value = "会员账号数量上限")
    private Integer expandAccountNum;

    @ApiModelProperty(value = "会员员工数量上限")
    private Integer expandEmployeeNum;

    @ApiModelProperty(value = "会员耗材档案数量上限")
    private Integer expandConsumablesNum;

    @ApiModelProperty(value = "会员资产数量上限")
    private Integer expandAssetNum;

    @ApiModelProperty(value = "会员资产模板数量上限")
    private Integer expandAssetTplNum;

    @ApiModelProperty(value = "会员标签模板数量上限")
    private Integer expandTagTplNum;

    @ApiModelProperty(value = "会员照片数量上限")
    private Integer expandPhotoNum;

    @ApiModelProperty(value = "会员附件数量上限")
    private Integer expandAppendixNum;

    /**
     * 实际使用
     */
    @ApiModelProperty(value = "实际公司数量")
    private Integer existsCompanyNum;

    @ApiModelProperty(value = "实际账号数量")
    private Integer existsAccountNum;

    @ApiModelProperty(value = "实际员工数量")
    private Integer existsEmployeeNum;

    @ApiModelProperty(value = "实际耗材档案数量")
    private Integer existsConsumablesNum = 0;

    @ApiModelProperty(value = "实际资产数量")
    private Integer existsAssetNum;

    @ApiModelProperty(value = "实际资产模板数量")
    private Integer existsAssetTplNum;

    @ApiModelProperty(value = "实际标签模板数量")
    private Integer existsTagTplNum;

    @ApiModelProperty(value = "实际照片数量")
    private Integer existsPhotoNum;

    @ApiModelProperty(value = "实际附件数量")
    private Integer existsAppendixNum;

    @ApiModelProperty(value = "备注")
    private String remark;

    public Integer getExistsPhotoNum() {
        if (this.photoNum == null) {
            return this.expandPhotoNum;
        }
        return this.photoNum + this.expandPhotoNum;
    }

    public Integer getExistsAppendixNum() {
        if (this.appendixNum == null) {
            return this.expandAppendixNum;
        }
        return this.appendixNum + this.expandAppendixNum;
    }

    public Integer getExistsTagTplNumForTag() {
        if (this.tagTplNum == null) {
            return this.expandTagTplNum;
        }
        return this.tagTplNum + this.expandTagTplNum;
    }

    public static final String REMARK = "remark";
}
