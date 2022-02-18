package com.niimbot.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author Wangzhiwen
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "RemoveEmployDto", description = "删除员工DTO")
public class RemoveEmployDto {

    @ApiModelProperty("员工ID")
    @NotNull(message = "员工ID不能为空")
    private Long employeeId;

    @ApiModelProperty(value = "员工名称", hidden = true)
    private String employeeName;

}
