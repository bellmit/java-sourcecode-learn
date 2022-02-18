package com.niimbot.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * created by chen.y on 2020/10/27 18:14
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value="CusMenuDto", description="PC端菜单")
public class CusMenuDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单ID")
    private Long id;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单编码")
    private String menuCode;

    @ApiModelProperty(value = "父菜单ID")
    private Long pid;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "路径paths")
    private String paths;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "菜单地址")
    private String feRoute;

    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    @ApiModelProperty("菜单类型")
    private String menuTypeText;

    @ApiModelProperty(value = "菜单class样式")
    private String menuClass;

    @ApiModelProperty(value = "样式icon图标")
    private String menuIcon;

    @ApiModelProperty(value = "菜单状态（1正常 2禁用）")
    private Short status;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = "是否配置项目")
    private Boolean isConfig;

    @ApiModelProperty(value = "是否展示子节点菜单")
    private Boolean isShowChildren;

    @ApiModelProperty(value = "是否可编辑")
    private Boolean canEdit;
}