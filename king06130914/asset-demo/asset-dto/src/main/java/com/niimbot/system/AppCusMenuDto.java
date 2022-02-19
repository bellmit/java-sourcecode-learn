package com.niimbot.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * App菜单权限对象
 *
 * @author XieKong
 * @date 2021/8/13 11:05
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value="AppCusMenuDto", description="App菜单权限对象")
public class AppCusMenuDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所有菜单code")
    List<String> allMenus;

    @ApiModelProperty(value = "当前菜单列表")
    List<CusMenuDto> menus;
}
