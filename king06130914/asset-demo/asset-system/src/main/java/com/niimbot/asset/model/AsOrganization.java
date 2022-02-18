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
 * 组织表
 * </p>
 *
 * @author dk
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AsOrganization对象", description="组织表")
@TableName(value = "as_organization")
public class AsOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "行业编号")
    private Long industryId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "组织编码")
    private String orgCode;

    @ApiModelProperty(value = "最顶级企业ID")
    private Long rootOrgId;

    @ApiModelProperty(value = "归属公司ID")
    private Long companyId;

    @ApiModelProperty(value = "父级组织")
    private Long pid;

    @ApiModelProperty(value = "路径")
    private String paths;

    @ApiModelProperty(value = "组织等级")
    private Integer level;

    @ApiModelProperty(value = "排序序号")
    private Integer sortNum;

    @ApiModelProperty(value = "组织类型:1-公司,2-部门")
    private Boolean type;

    @ApiModelProperty(value = "创建人")
    private Integer createdUserid;

    @ApiModelProperty(value = "0-未删除  1：已删除")
    private Boolean isDelete;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;


}
