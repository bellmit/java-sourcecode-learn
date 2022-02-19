package com.niimbot.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author XieKong
 * @date 2020/12/15 下午4:11
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CompanySwitch对象", description = "企业开关控制")
public class CompanySwitchDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "新增员工时默认启用账号-默认开启")
    private Boolean enableAccount = true;

    @ApiModelProperty(value = "发送账号和密码给员工的方式-邮件-默认关闭")
    private Boolean enableEmail = false;
}
