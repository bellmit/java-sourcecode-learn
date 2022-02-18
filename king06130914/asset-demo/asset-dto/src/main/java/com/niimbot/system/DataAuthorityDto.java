package com.niimbot.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 数据权限
 * </p>
 *
 * @author chen.y
 * @since 2020-10-21
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "DataAuthority对象", description = "数据权限")
public class DataAuthorityDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限ID")
    private Long id;

    @ApiModelProperty(value = "数据范围（0、查看全部，1、仅查看当前用户，2、仅查看当前部门，3、所在部门及子部门，4、自定义部门列表）")
    private Integer dataScope;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "权限类型:dept(部门),area(地区),cate(分类),store(仓库)")
    private String authorityCode;

    @ApiModelProperty(value = "权限数据")
    private List<Long> authorityData;

    @ApiModelProperty(value = "权限分组")
    private Integer authorityGroup;
}
