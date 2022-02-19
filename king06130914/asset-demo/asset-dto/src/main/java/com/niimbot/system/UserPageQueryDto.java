package com.niimbot.system;

import com.niimbot.page.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 账号dto
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UserPageQueryDto查询对象", description = "UserPageQueryDto查询对象")
public class UserPageQueryDto extends Pageable {

    @ApiModelProperty(value = "搜索用户名、手机号、企业名称")
    private String wd;

    @ApiModelProperty(value = "使用版本")
    private List<Integer> version_id;

    @ApiModelProperty(value = "到期日期")
    private List<Integer> intime;

    @ApiModelProperty(value = "注册日期")
    private List<Integer> created_at;

    @ApiModelProperty(value = "是否只显示测试账号")
    private Boolean is_only_test;
}
