package com.niimbot.system;

import com.niimbot.validate.group.Insert;
import com.niimbot.validate.group.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * created by chen.y on 2020/11/17 17:56
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "CusRoleDto", description = "客户角色")
public class CusRoleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @NotNull(message = "角色ID不能为空", groups = {Update.class})
    private Long id;

    @ApiModelProperty(value = "角色名称", required = true)
    @Size(min = 2, max = 6, message = "角色名称2-6个字符")
    @NotBlank(message = "角色名称不能为空", groups = {Insert.class, Update.class})
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    @Size(max = 20, message = "角色编码最多20个字符")
    private String roleCode;

    @ApiModelProperty(value = "角色状态 1:正常  2:禁用 ")
    private Short status;

    @ApiModelProperty(value = "app菜单集合")
    private List<Long> appMenuIds = new ArrayList<>();

    @ApiModelProperty(value = "pc菜单集合")
    private List<Long> pcMenuIds = new ArrayList<>();

    @ApiModelProperty(value = "是否能删除")
    private Boolean canDelete;

}
