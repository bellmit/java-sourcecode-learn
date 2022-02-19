package com.niimbot.asset.service.http;

import com.dtflys.forest.annotation.Request;

import java.util.Map;

/**
 * 商品库条码查询
 */
public interface ProductMarketHttpClient {
    /**
     * 商品库条码查询
     * @param barcode
     * @return
     */
    @Request(
            url = "http://jisutxmcx.market.alicloudapi.com/barcode2/query?barcode=${0}",
            headers = "Authorization: APPCODE a397ac207a0b4a03a7a5848a8a633456",
            type = "get",
            dataType = "json"
    )
    Map<String, Object> productScan(String barcode);
}
