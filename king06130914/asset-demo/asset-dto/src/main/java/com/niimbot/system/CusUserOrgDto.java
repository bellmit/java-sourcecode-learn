package com.niimbot.system;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * created by chen.y on 2021/1/4 11:38
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "CusUserOrgDto", description = "用户组织信息")
public class CusUserOrgDto {

    @ApiModelProperty(value = "组织Id")
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;
}
