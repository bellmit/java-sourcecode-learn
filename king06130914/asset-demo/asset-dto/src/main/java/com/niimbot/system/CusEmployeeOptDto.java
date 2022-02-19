package com.niimbot.system;

import com.niimbot.validate.ValidationUtils;
import com.niimbot.validate.group.Insert;
import com.niimbot.validate.group.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.*;
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
@ApiModel(value = "CusEmployeeOptDto对象", description = "员工编辑")
public class CusEmployeeOptDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "员工id")
    @NotNull(message = "请输入员工id", groups = {Update.class})
    private Long id;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "请填写姓名", groups = {Insert.class})
    @Size(min = 2, max = 20, message = "请输入2-20个字", groups = {Insert.class, Update.class})
    private String empName;

    @ApiModelProperty(value = "工号")
    @Pattern(regexp = "^$|^[a-zA-Z0-9-_]{2,8}$", message = "请输入工号 / 2-8位，只能由字母、数字、符号-_组成", groups = {Insert.class, Update.class})
    private String empNo;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "请填写手机号", groups = {Insert.class})
    @Pattern(regexp = "^(((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[6])|(17[0135678])|(18[0-9])|(19[589]))\\d{8})$",
            message = "输入的手机号无效", groups = {Insert.class})
    private String mobile;

    @ApiModelProperty(value = "职位信息")
    @Pattern(regexp = "^$|^[a-zA-Z0-9\u4E00-\u9FA5·.\\-_]{2,20}$", message = "请输入职位 / 2-20位,可包含中文,数字,字母,符号·.-_", groups = {Insert.class, Update.class})
    private String position;

    @ApiModelProperty(value = "组织部门ID")
    @NotEmpty(message = "请选择所属组织", groups = {Insert.class, Update.class})
    private List<Long> orgId;

    @ApiModelProperty(value = "邮箱,选填")
    @Size(max = 255, message = "邮箱不得超过255个字符")
    @Pattern(regexp = ValidationUtils.EMAIL_REG, message = "邮箱格式不正确")
    private String email;

}
