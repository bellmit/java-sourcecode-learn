package com.niimbot.system;

import com.niimbot.page.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 员工查询条件实体
 *
 * @author xie.wei
 * @since 2020-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "员工查询对象", description = "员工查询对象")
public class CusEmployeeQueryDto extends Pageable {

    @ApiModelProperty(value = "员工id")
    private Long id;

    @ApiModelProperty(value = "工号")
    private String empNo;

    @ApiModelProperty(value = "姓名")
    private String empName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "职位信息")
    private String position;

    @ApiModelProperty(value = "在职状态:1-在职, 2-离职")
    private Integer status;

    @ApiModelProperty(value = "关键字")
    private String kw;

    @ApiModelProperty(value = "组织部门ID")
    private Long orgId;

    @ApiModelProperty(value = "是否软删除 0-否  1-是")
    private Boolean isDelete;

    @ApiModelProperty(value = "是否有手机号：1、没有手机号，2、有手机号")
    private String mobileType;

    @ApiModelProperty(value = "是否开通账号：1、未开通账号，2、已开通账号")
    private String accountType;
}
