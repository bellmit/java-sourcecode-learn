package com.niimbot.asset.service.feign;

import com.niimbot.system.DataAuthorityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * created by chen.y on 2020/10/21 16:27
 *
 * @author chen.y
 */
@FeignClient(url = "${feign.remote}", name = "asset-demo")
public interface DataAuthorityFeignClient {

    /**
     * 根据用户Id查询数据权限集
     *
     * @param userId 用户Id
     * @return 公司集合
     */
    @GetMapping(value = "server/system/dataAuthority/{userId}")
    List<DataAuthorityDto> selectAuthorityById(@PathVariable("userId") Long userId);
}
