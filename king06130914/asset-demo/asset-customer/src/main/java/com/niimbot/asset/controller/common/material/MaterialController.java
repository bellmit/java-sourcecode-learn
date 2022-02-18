package com.niimbot.asset.controller.common.material;

import com.alibaba.fastjson.JSON;
import com.dtflys.forest.exceptions.ForestNetworkException;
import com.niimbot.asset.framework.constant.RedisConstant;
import com.niimbot.asset.framework.service.RedisService;
import com.niimbot.asset.service.http.ProductMarketHttpClient;
import com.niimbot.jf.core.component.annotation.ResultController;
import com.niimbot.material.ProductDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Api(tags = "【耗材】耗材档案")
@ResultController
@RequestMapping("api/common/material")
@Validated
public class MaterialController {

    private final RedisService redisService;

    private final ProductMarketHttpClient productMarketHttpClient;

    @Autowired
    public MaterialController(RedisService redisService,
                              ProductMarketHttpClient productMarketHttpClient) {
        this.redisService = redisService;
        this.productMarketHttpClient = productMarketHttpClient;
    }

    @ApiOperation(value = "商品库扫码查询")
    @GetMapping(value = "/product/scan")
    public ProductDto productScan(@ApiParam(name = "barcode", value = "编码")
                                  @RequestParam("barcode") String barcode) {
        try {
            // 查缓存
            ProductDto cache = (ProductDto) redisService.hGet(RedisConstant.PRODUCT_LIBRARY, barcode);
            // 缓存没有查询接口
            if (cache == null) {
                Map<String, Object> scan = productMarketHttpClient.productScan(barcode);
                Object result = scan.get("result");
                cache = JSON.parseObject(JSON.toJSONString(result), ProductDto.class);
                // 写缓存
                redisService.hSet(RedisConstant.PRODUCT_LIBRARY, barcode, cache, 7, TimeUnit.DAYS);
            }
            return cache;
        } catch (ForestNetworkException e) {
            log.error("商品库查询失败", e);
        }
        return null;
    }

}
