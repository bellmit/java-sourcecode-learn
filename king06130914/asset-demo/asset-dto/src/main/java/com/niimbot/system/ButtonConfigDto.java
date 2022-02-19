package com.niimbot.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "按钮权限对象", description = "运营后台用户")
public class ButtonConfigDto implements Serializable {

    @ApiModelProperty(value = "按钮名称")
    private String cn;

    @ApiModelProperty(value = "按钮是否显示")
    private Boolean is_show;

}
