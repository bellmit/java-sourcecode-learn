package com.niimbot.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * created by chen.y on 2021/1/4 11:38
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "CusUserDetailDto", description = "PC端用户详情信息")
public class CusUserDetailDto {

    @ApiModelProperty(value = "员工Id")
    private String empId;

    @ApiModelProperty(value = "员工名称")
    private String empName;

    @ApiModelProperty(value = "公司Id")
    private Long companyId;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司Logo")
    private String companyLogo;

    @ApiModelProperty(value = "用户头像")
    private String image;

    @ApiModelProperty(value = "弹窗协议状态 1-未同意 2-已同意")
    private Short agreementStatus;

    @ApiModelProperty(value = "引导“常用功能”、“系统初始化配置” 状态 1-需要  2-不需要")
    private Short guideStatus;

    @ApiModelProperty(value = "照片合计数量")
    private Integer photoNumAmount;

    @ApiModelProperty(value = "附件合计数量")
    private Integer appendixNumAmount;

    @ApiModelProperty(value = "状态 1:启用 2:禁用")
    private Short status;

    @ApiModelProperty(value = "是否超管")
    private Boolean isAdmin;

    @ApiModelProperty(value = "是否极简模式")
    private Boolean assetSimplify = false;

    @ApiModelProperty(value = "组织")
    private List<CusUserOrgDto> orgList;

    @ApiModelProperty(value = "版本类型: 1-体验版, 2-普通版, 3-最高版")
    private Integer versionType;

    private String versionTypeText;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "是否绑定三方组织架构")
    private String bindThirdParty;

    @ApiModelProperty(value = "超级管理员是否被同步删除")
    private Boolean adminHasRemove;
}
