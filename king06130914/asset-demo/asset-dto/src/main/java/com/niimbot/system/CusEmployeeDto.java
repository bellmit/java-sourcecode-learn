package com.niimbot.system;

import com.niimbot.validate.ValidationUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 员工表
 * </p>
 *
 * @author xie.wei
 * @since 2020-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AsCusEmployee对象", description = "PC员工管理")
public class CusEmployeeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "员工id")
    @NotNull(message = "请输入员工id")
    private Long id;

    @ApiModelProperty(value = "员工账号")
    private String account;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "请填写姓名")
    @Size(min = 2, max = 20, message = "请输入2-20个字")
    private String empName;

    @ApiModelProperty(value = "工号")
    @Pattern(regexp = "^$|^[a-zA-Z0-9-_]{2,8}$", message = "请输入工号 / 2-8位，只能由字母、数字、符号-_组成")
    private String empNo;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "请填写手机号")
    @Pattern(regexp = "^(((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[6])|(17[0135678])|(18[0-9])|(19[589]))\\d{8})$",
            message = "输入的手机号无效")
    private String mobile;

    @ApiModelProperty(value = "职位信息")
    @Pattern(regexp = "^$|^[a-zA-Z0-9\u4E00-\u9FA5·.\\-_]{2,20}$", message = "请输入职位 / 2-20位,可包含中文,数字,字母,符号·.-_")
    private String position;

    @ApiModelProperty(value = "组织部门名称")
    private List<CusUserOrgDto> orgList;

    @ApiModelProperty(value = "在职状态:1-在职, 2-离职")
    private Integer status;

    @ApiModelProperty(value = "在职状态:1-在职, 2-离职")
    private String statusText;

    @ApiModelProperty(value = "关键字")
    private String kw;

    @ApiModelProperty(value = "账号状态 1:启用 2:禁用")
    private Short accountStatus;

    @ApiModelProperty(value = "邮箱,选填")
    @Size(max = 255, message = "邮箱不得超过255个字符")
    @Pattern(regexp = ValidationUtils.EMAIL_REG, message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty("是否是超管")
    private boolean isAdmin;

    @ApiModelProperty("是否是主管")
    private boolean isDirector;

    public boolean getIsDirector() {
        return isDirector;
    }

    public void setIsDirector(boolean director) {
        isDirector = director;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }
}
