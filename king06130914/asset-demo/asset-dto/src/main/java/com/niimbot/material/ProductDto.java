package com.niimbot.material;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ProductDto对象", description = "商品信息")
public class ProductDto implements Serializable {
    private static final long serialVersionUID = -2848893340857998393L;
    /**
     * 条码
     */
    @ApiModelProperty(value = "条码")
    private String barcode;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 品牌
     */
    @ApiModelProperty(value = "品牌")
    private String brand;
    /**
     * 规格型号
     */
    @ApiModelProperty(value = "规格型号")
    @JSONField(name = "type")
    private String model;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    @JSONField(name = "netcontent")
    private String unit;
}
