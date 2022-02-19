package com.niimbot.system;

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
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "用户唯一ID JC30000001 开始")
    private String uid;

    @ApiModelProperty(value = "用户昵称")
    private String user_name;

    @ApiModelProperty(value = "版本名称")
    private String version_name;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "顶级组织编号ID")
    private Long root_org_id;

    @ApiModelProperty(value = "是否是测试账号")
    private Integer type;

    @ApiModelProperty(value = "是否是测试账号 中文")
    private String type_cn;

    @ApiModelProperty(value = "行业名称")
    private String industry_name;

    @ApiModelProperty(value = "企业名称")
    private String org_name;

    @ApiModelProperty(value = "到期时间")
    private LocalDateTime due_at;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime created_at;

    @ApiModelProperty(value = "创建时间-返回参数")
    private LocalDateTime created_time;
}
