package com.niimbot.asset.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 版本信息
 *
 * @author XieKong
 * @date 2021/7/15 17:08
 */
@FeignClient(url = "${feign.remote}", name = "asset-demo")
public interface VersionFeignClient {
}
