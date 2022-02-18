package com.niimbot.asset.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 合同表
 * </p>
 *
 * @author dk
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AsContract对象", description="合同表")
@TableName(value = "as_contract")
public class AsContract implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "顶级组织")
    private Long rootOrgId;

    @ApiModelProperty(value = "版本编号")
    private Long versionId;

    @ApiModelProperty(value = "合同编号")
    private String contractNo;

    @ApiModelProperty(value = "合同金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "合同类型 1-新签订  2-续约")
    private Boolean contractType;

    @ApiModelProperty(value = "支付方式 1-对公转账 2-银联付款 3-支付宝  4-微信")
    private Integer payType;

    @ApiModelProperty(value = "签订日期")
    private LocalDateTime signAt;

    @ApiModelProperty(value = "到期日期")
    private LocalDateTime dueAt;

    @ApiModelProperty(value = "销售单位")
    private String salesUnit;

    @ApiModelProperty(value = "销售人")
    private String salesperson;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "1- 生效  2- 过期")
    private Integer status;

    @ApiModelProperty(value = "创建者")
    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
