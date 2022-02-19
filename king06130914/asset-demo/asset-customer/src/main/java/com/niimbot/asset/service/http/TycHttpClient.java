package com.niimbot.asset.service.http;

import com.dtflys.forest.annotation.Request;

import java.util.Map;

/**
 * 天眼查
 */
public interface TycHttpClient {
    /**
     * 天眼查查询
     * @param companyName
     * @return
     */
    @Request(
            url = "http://open.api.tianyancha.com/services/open/ic/baseinfo/normal?keyword=${0}",
            headers = "Authorization: dfd8a33f-1ca0-48dc-bee4-feafcd0bff1b",
            type = "get",
            dataType = "json"
    )
    Map<String, Object> companyInfo(String companyName);
}
