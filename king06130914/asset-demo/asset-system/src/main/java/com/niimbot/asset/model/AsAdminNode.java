package com.niimbot.asset.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 后台 (菜单)权限节点表
 * </p>
 *
 * @author dk
 * @since 2021-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AsAdminNode对象", description="后台 (菜单)权限节点表")
@TableName(value = "as_admin_node")
public class AsAdminNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "节点名称")
    private String nodeName;

    @ApiModelProperty(value = "父节点编号")
    private Long pid;

    @ApiModelProperty(value = "路径paths")
    private String paths;

    @ApiModelProperty(value = "菜单class样式")
    private String menuClass;

    @ApiModelProperty(value = "样式ico 图标")
    private String menuIcon;

    @ApiModelProperty(value = "排序序号")
    private Integer sortNum;

    @ApiModelProperty(value = "前端路由名")
    private String feRoute;

    @ApiModelProperty(value = "后端路由URI 集合 | 分隔 ")
    private String apiRoutes;

    @ApiModelProperty(value = "后端路由URI 名称集合|分割")
    private String apiRoutesName;

    @ApiModelProperty(value = "是否菜单")
    private Boolean isMenu;

    @ApiModelProperty(value = "显示按钮   asset_add#资产添加|asset_del#资产删除")
    private String buttonConfig;

    @ApiModelProperty(value = "是否默认节点 0：否  1：是")
    private Boolean isDefault;

    @ApiModelProperty(value = "是否超管权限 0：否  1：是")
    private Boolean isAdmin;

    @ApiModelProperty(value = "是否配置项")
    private Boolean isConfig;

    @ApiModelProperty(value = "1：共有权限  2：PC   3：APP")
    private Boolean flag;

    @ApiModelProperty(value = "创建者")
    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
